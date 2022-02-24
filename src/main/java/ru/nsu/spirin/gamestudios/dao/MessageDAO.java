package ru.nsu.spirin.gamestudios.dao;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.MessageReceivedMapper;
import ru.nsu.spirin.gamestudios.mapper.MessageSentMapper;
import ru.nsu.spirin.gamestudios.model.message.Message;
import ru.nsu.spirin.gamestudios.utils.AttachmentUtils;

import javax.sql.DataSource;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@Transactional
public class MessageDAO extends JdbcDaoSupport {
    @Autowired
    public MessageDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Page<Message> getSentMessagesByUsername(String userName, Pageable pageable) {
        Object[] params = new Object[]{ userName };

        String sqlTotal = """
                            SELECT count(1) AS row_count
                            FROM (message LEFT JOIN
                                    (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                    ) as received1
                                 on message.message_id = received1.message_id) msg
                            WHERE msg.sender = ?;
                         """;
        int total = this.getJdbcTemplate().queryForObject(sqlTotal, params, (rs, rowNum) -> rs.getInt(1));

        String querySql = """
                            SELECT *
                            FROM (message LEFT JOIN
                                    (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                    ) as received1
                                 on message.message_id = received1.message_id) msg
                            WHERE msg.sender = ?
                        """ +
                            "LIMIT " + pageable.getPageSize() + " " +
                            "OFFSET " + pageable.getOffset();

        List<Message> messages = this.getJdbcTemplate().query(querySql, new MessageSentMapper(), params);
        return new PageImpl<>(messages, pageable, total);
    }

    public Page<Message> getReceivedMessagesByUsername(String userName, Pageable pageable) {
        Object[] params = new Object[]{ userName, userName };

        String sqlTotal = """
                            SELECT count(1) AS row_count
                            FROM (message NATURAL JOIN
                                    (
                                        (
                                            SELECT received.message_id, array_agg(received.receiver) as receivers
                                            FROM (message NATURAL JOIN received_message) received
                                            GROUP BY received.message_id
                                            HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                                        ) as received1
                                        NATURAL JOIN
                                        (
                                            SELECT received.message_id, received.read
                                            FROM received_message received
                                            WHERE received.receiver = ?
                                        ) as read
                                    ) as received2
                                ) msg;
                         """;
        int total = this.getJdbcTemplate().queryForObject(sqlTotal, params, (rs, rowNum) -> rs.getInt(1));

        String querySql = """
                                SELECT *
                                FROM (message NATURAL JOIN
                                        (
                                            (
                                                SELECT received.message_id, array_agg(received.receiver) as receivers
                                                FROM (message NATURAL JOIN received_message) received
                                                GROUP BY received.message_id
                                                HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                                            ) as received1
                                            NATURAL JOIN
                                            (
                                                SELECT received.message_id, received.read
                                                FROM received_message received
                                                WHERE received.receiver = ?
                                            ) as read
                                        ) as received2
                                    ) msg
                            """ +
                                "LIMIT " + pageable.getPageSize() + " " +
                                "OFFSET " + pageable.getOffset();

        List<Message> messages = this.getJdbcTemplate().query(querySql, new MessageReceivedMapper(), params);
        return new PageImpl<>(messages, pageable, total);
    }

    public Message getMessageByID(Long id) {
        Object[] params = new Object[]{ id };

        String sqlSent = """
                            SELECT *
                            FROM message LEFT JOIN
                                    (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                    ) as received1
                                 on message.message_id = received1.message_id
                            WHERE message.message_id = ?;
                        """;
        MessageSentMapper sentMapper = new MessageSentMapper();
        return this.getJdbcTemplate().queryForObject(sqlSent, sentMapper, params);
    }

    public boolean canViewMessage(Long id, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Object[] params = new Object[]{ id, user.getUsername(), id, user.getUsername() };

        String sqlSent = """
                            SELECT msg.message_id
                            FROM message msg
                            WHERE msg.message_id = ? AND msg.sender = ?
                            UNION
                            SELECT received.message_id as receivers
                            FROM (message NATURAL JOIN received_message) received
                            WHERE received.message_id = ? AND received.receiver = ?;
                        """;
        List<Long> res = this.getJdbcTemplate().query(sqlSent, (rs, rowNum) -> rs.getLong("message_id"), params);
        return res.size() > 0;
    }

    public void newMessage(Message message) throws SQLException {
        if (message.getTopic() == null || message.getContent() == null || message.getAttachments() == null || message.getReceivers() == null) {
            return;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsertSender = """
                                    INSERT INTO message (message_id, date, topic, content, sender, attachments) VALUES
                                         (default, now(), ?, ?, ?, ?)
                                         RETURNING message_id;
                                """;
        this.getJdbcTemplate().update(
                con -> {
                    PreparedStatement ps =con.prepareStatement(sqlInsertSender, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, message.getTopic());
                    ps.setString(2, message.getContent());
                    ps.setString(3, message.getSender());

                    PGobject jsonObject = new PGobject();
                    jsonObject.setType("json");
                    jsonObject.setValue(AttachmentUtils.parseTo(message.getAttachments()));
                    ps.setObject(4, jsonObject);

                    return ps;
                },
                keyHolder
        );

        String sqlInsertReceiver = """
                                        INSERT INTO received_message VALUES
                                            (?, ?, false);
                                    """;
        for (var receiver : message.getReceivers()) {
            Object[] params = new Object[]{
                    keyHolder.getKey().longValue(),
                    receiver
            };
            this.getJdbcTemplate().update(sqlInsertReceiver, params);
        }
    }

    public void readMessage(Long messageID, Principal principal) {
        String sql = """
                        UPDATE received_message
                        SET read = true
                        WHERE message_id = ? AND receiver = ?;
                    """;
        User user = (User) ((Authentication) principal).getPrincipal();
        this.getJdbcTemplate().update(sql, messageID, user.getUsername());
    }

    public void deleteReceivedMessage(Long messageID, Principal principal) {
        String sql = """
                        DELETE FROM received_message
                        WHERE message_id = ? AND receiver = ?;
                    """;
        User user = (User) ((Authentication) principal).getPrincipal();
        this.getJdbcTemplate().update(sql, messageID, user.getUsername());
    }

    public void deleteSentMessage(Long messageID, Principal principal) {
        String sql = """
                        DELETE FROM message
                        WHERE message_id = ? AND sender = ?;
                    """;
        User user = (User) ((Authentication) principal).getPrincipal();
        this.getJdbcTemplate().update(sql, messageID, user.getUsername());
    }
}
