package ru.nsu.spirin.gamestudios.repository;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.MessageReceivedMapper;
import ru.nsu.spirin.gamestudios.model.mapper.MessageSentMapper;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.repository.filtration.Filtration;
import ru.nsu.spirin.gamestudios.repository.query.MessageQueries;
import ru.nsu.spirin.gamestudios.utils.AttachmentUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Transactional
public class MessageRepository extends JdbcDaoSupport {
    @Autowired
    public MessageRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Page<Message> findAllSentMessagesByEmail(String email, Filtration filtration, Pageable pageable) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        Long total = this.getJdbcTemplate().queryForObject(
                String.format(MessageQueries.QUERY_COUNT_TOTAL_SENT_BY_EMAIL, filtration.buildQuery()),
                (rs, rowNum) -> rs.getLong(1),
                email
        );
        if (total == null) {
            return null;
        }

        List<Message> messages = this.getJdbcTemplate().query(
                String.format(MessageQueries.QUERY_FIND_ALL_SENT_BY_EMAIL, filtration.buildQuery()),
                new MessageSentMapper(),
                email, pageable.getPageSize(), pageable.getOffset()
        );
        return new PageImpl<>(messages, pageable, total);
    }

    public List<Long> findAllSentMessagesByEmailSimple(String email) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(
                MessageQueries.QUERY_FIND_ALL_SENT_BY_EMAIL_SIMPLE,
                (rs, rowNum) -> rs.getLong(1),
                email
        );
    }

    public Page<Message> findAllReceivedMessagesByUsername(String email, Filtration filtration, Pageable pageable) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        Long total = this.getJdbcTemplate().queryForObject(
                String.format(MessageQueries.QUERY_COUNT_TOTAL_RECEIVED_BY_EMAIL, filtration.buildQuery()),
                (rs, rowNum) -> rs.getLong(1),
                email, email
        );
        if (total == null) {
            return null;
        }

        List<Message> messages = this.getJdbcTemplate().query(
                String.format(MessageQueries.QUERY_FIND_ALL_RECEIVED_BY_EMAIL, filtration.buildQuery()),
                new MessageReceivedMapper(),
                email, email, pageable.getPageSize(), pageable.getOffset()
        );
        return new PageImpl<>(messages, pageable, total);
    }

    public Long countReceived(String userName, boolean read) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(MessageQueries.QUERY_COUNT_RECEIVED,
                (rs, rowNum) -> rs.getLong(1),
                userName, userName, read);
    }

    public Message findByID(Long id) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(MessageQueries.QUERY_FIND_BY_ID, new MessageSentMapper(), id);
    }

    public List<Long> findAllIDsByEmail(Long id, String email) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(MessageQueries.QUERY_FIND_ALL_IDS_BY_EMAIL,
                (rs, rowNum) -> rs.getLong("message_id"),
                id, email, id, email);
    }

    public Long countMaxID() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(MessageQueries.QUERY_COUNT_MAX_ID, Long.class);
    }

    public Long saveSentMessage(Message message) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        this.getJdbcTemplate().update(
            con -> {
                PreparedStatement ps = con.prepareStatement(MessageQueries.QUERY_SAVE_SENT_MESSAGE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, message.getTopic());
                ps.setString(2, message.getContent());
                ps.setString(3, message.getSender());

                PGobject jsonObject = new PGobject();
                jsonObject.setType("json");
                jsonObject.setValue(AttachmentUtils.parseTo(message.getAttachments()));
                ps.setObject(4, jsonObject);

                return ps;
            },
            generatedKeyHolder
        );

        if (generatedKeyHolder.getKey() == null) {
            return this.countMaxID();
        }

        return generatedKeyHolder.getKey().longValue();
    }

    public void saveReceivedMessage(Message message, String receiver) {
        if (null == this.getJdbcTemplate()) {
            return;
        }

        this.getJdbcTemplate().update(MessageQueries.QUERY_SAVE_RECEIVED_MESSAGE, message.getMessageID(), receiver);
    }

    public void updateMessage(Long messageID, String receiver, boolean read) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_UPDATE_RECEIVED_MESSAGE, read, messageID, receiver);
    }

    public void deleteReceivedMessage(Long messageID, String receiver) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_RECEIVED_MESSAGE, messageID, receiver);
    }

    public void deleteAllReceivedMessagesByMessageID(Long messageID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_ALL_RECEIVED_BY_ID, messageID);
    }

    public void deleteSentMessage(Long messageID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_SENT_MESSAGE, messageID);
    }

    public void deleteAllSentMessagesByAccount(String accountID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_ALL_SENT_BY_ACCOUNT, accountID);
    }

    public void deleteAllReceivedMessagesByAccount(String accountID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_ALL_RECEIVED_BY_ACCOUNT, accountID);
    }
}
