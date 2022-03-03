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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MessageDAO extends JdbcDaoSupport {
    @Autowired
    public MessageDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Page<Message> getSentMessagesByUsername(String userName,
                                                   String topic,
                                                   String receiver,
                                                   String date,
                                                   Pageable pageable) {
        Object[] params = new Object[]{ userName };

        String topicWhere = topic == null ? " TRUE " : " msg.topic ILIKE '%" + topic + "%' ";
        String receiverWhere = receiver == null ? " TRUE " : " msg.receivers_string ILIKE '%" + receiver + "%' ";

        String leftDate = null, rightDate = null;
        if (date != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String[] dates = date.split("_-_");
                Date leftDate1 = format.parse(dates[0]);
                Date rightDate1 = format.parse(dates[1]);
                leftDate = format.format(leftDate1);
                rightDate = format.format(rightDate1);
            }
            catch (Exception ignored) {
                leftDate = null;
            }
        }
        String dateWhere = leftDate == null ? " TRUE " : " msg.date BETWEEN '" + leftDate + " 00:00:00' AND '" + rightDate + " 23:59:59' ";

        String sqlTotal = """
                            SELECT count(1) AS row_count
                            FROM (message LEFT JOIN
                                    (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers, string_agg(received.receiver, ' ') as  receivers_string
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                    ) as received1
                                 on message.message_id = received1.message_id) msg
                            WHERE msg.sender = ?
                         """ +
                         " AND " + topicWhere + " AND " + receiverWhere + " AND " + dateWhere;
        int total = this.getJdbcTemplate().queryForObject(sqlTotal, params, (rs, rowNum) -> rs.getInt(1));

        String querySql = """
                            SELECT *
                            FROM (message LEFT JOIN
                                    (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers, string_agg(received.receiver, ' ') as  receivers_string
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                    ) as received1
                                 on message.message_id = received1.message_id) msg
                            WHERE msg.sender = ?
                        """  +
                            " AND " + topicWhere + " AND " + receiverWhere + " AND " + dateWhere + " "
                             +
                            "ORDER BY msg.date DESC " +
                            "LIMIT " + pageable.getPageSize() + " " +
                            "OFFSET " + pageable.getOffset() + " ";


        List<Message> messages = this.getJdbcTemplate().query(querySql, new MessageSentMapper(), params);
        return new PageImpl<>(messages, pageable, total);
    }

    public Page<Message> getReceivedMessagesByUsername(String userName,
                                                       String topic,
                                                       String date,
                                                       String sender,
                                                       String read,
                                                       Pageable pageable) {
        Object[] params = new Object[]{ userName, userName };

        String topicWhere = topic == null ? " TRUE " : " msg.topic ILIKE '%" + topic + "%' ";
        String senderWhere = sender == null ? " TRUE " : " msg.sender ILIKE '%" + sender + "%' ";

        String leftDate = null, rightDate = null;
        if (date != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String[] dates = date.split("_-_");
                Date leftDate1 = format.parse(dates[0]);
                Date rightDate1 = format.parse(dates[1]);
                leftDate = format.format(leftDate1);
                rightDate = format.format(rightDate1);
            }
            catch (Exception ignored) {
                leftDate = null;
            }
        }
        String dateWhere = leftDate == null ? " TRUE " : " msg.date BETWEEN '" + leftDate + " 00:00:00' AND '" + rightDate + " 23:59:59' ";


        int readValue = 0;
        try {
            readValue = Integer.parseInt(read);
            if (readValue < 0 || readValue > 2) readValue = 0;
        }
        catch (Exception ignored) {}
        String readWhere = readValue == 0 ? " TRUE " :
                readValue == 1 ? " msg.read = TRUE " :
                        " msg.read = FALSE ";

        String sqlTotal = """
                          WITH received1 AS (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                        HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                                    ),
                                read AS (
                                            SELECT received.message_id, received.read
                                            FROM received_message received
                                            WHERE received.receiver = ?
                                 )
                            SELECT count(1) AS row_count
                            FROM (message NATURAL JOIN
                                    (received1 NATURAL JOIN read) as received2
                                ) msg
                                WHERE 
                         """ +
                                topicWhere + " AND " + senderWhere + " AND " + dateWhere + " AND " + readWhere;
        int total = this.getJdbcTemplate().queryForObject(sqlTotal, params, (rs, rowNum) -> rs.getInt(1));

        String querySql = """
                                WITH received1 AS (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                        HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                                    ),
                                    read AS (
                                        SELECT received.message_id, received.read
                                        FROM received_message received
                                        WHERE received.receiver = ?
                                 )
                                SELECT *
                                FROM (message NATURAL JOIN
                                        (received1 NATURAL JOIN read) as received2
                                    ) msg
                                    WHERE 
                            """ +
                                topicWhere + " AND " + senderWhere + " AND " + dateWhere + " AND " + readWhere + " "
                                +
                                "ORDER BY msg.date DESC " +
                                "LIMIT " + pageable.getPageSize() + " " +
                                "OFFSET " + pageable.getOffset();

        List<Message> messages = this.getJdbcTemplate().query(querySql, new MessageReceivedMapper(), params);
        return new PageImpl<>(messages, pageable, total);
    }

    public int getNumberOfUnreadMessages(String userName) {
        Object[] params = new Object[]{ userName, userName };

        String sqlTotal = """
                            WITH received1 AS (
                                        SELECT received.message_id, array_agg(received.receiver) as receivers
                                        FROM (message NATURAL JOIN received_message) received
                                        GROUP BY received.message_id
                                        HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                                    ),
                                 read AS (
                                        SELECT received.message_id, received.read
                                        FROM received_message received
                                        WHERE received.receiver = ?
                                 )
                            SELECT count(1) AS row_count
                            FROM (message NATURAL JOIN
                                    (received1 NATURAL JOIN read) as received2
                                ) msg
                             WHERE msg.read = FALSE;
                         """;
        return this.getJdbcTemplate().queryForObject(sqlTotal, params, (rs, rowNum) -> rs.getInt(1));
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

        String sql0 = "SELECT MAX(message_id) as max_id FROM message";
        Long id = this.getJdbcTemplate().queryForObject(sql0, Long.class) + 1;

        String sqlInsertSender = """
                                    INSERT INTO message (message_id, date, topic, content, sender, attachments) VALUES
                                         (?, now(), ?, ?, ?, ?)
                                         RETURNING message_id;
                                """;
        this.getJdbcTemplate().update(
                con -> {
                    PreparedStatement ps =con.prepareStatement(sqlInsertSender, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, id);
                    ps.setString(2, message.getTopic());
                    ps.setString(3, message.getContent());
                    ps.setString(4, message.getSender());

                    PGobject jsonObject = new PGobject();
                    jsonObject.setType("json");
                    jsonObject.setValue(AttachmentUtils.parseTo(message.getAttachments()));
                    ps.setObject(5, jsonObject);

                    return ps;
                }
        );

        String sqlInsertReceiver = """
                                        INSERT INTO received_message VALUES
                                            (?, ?, false);
                                    """;
        for (var receiver : message.getReceivers()) {
            this.getJdbcTemplate().update(sqlInsertReceiver, id, receiver);
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
