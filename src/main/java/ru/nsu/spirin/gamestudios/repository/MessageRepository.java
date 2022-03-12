package ru.nsu.spirin.gamestudios.repository;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.MessageReceivedMapper;
import ru.nsu.spirin.gamestudios.model.mapper.MessageSentMapper;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.repository.query.MessageQueries;
import ru.nsu.spirin.gamestudios.utils.AttachmentUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MessageRepository extends JdbcDaoSupport {
    @Autowired
    public MessageRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    private String queryTopic(String topic) {
        return topic == null ? " TRUE " : " msg.topic ILIKE '%" + topic + "%' ";
    }

    private String queryReceiver(String receiver) {
        return receiver == null ? " TRUE " : " msg.receivers_string ILIKE '%" + receiver + "%' ";
    }

    private String querySender(String sender) {
        return sender == null ? " TRUE " : " msg.sender ILIKE '%" + sender + "%' ";
    }

    private String queryDate(String date) {
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
        return leftDate == null ? " TRUE " : " msg.date BETWEEN '" + leftDate + " 00:00:00' AND '" + rightDate + " 23:59:59' ";
    }

    private String queryRead(String read) {
        int readValue = 0;
        try {
            readValue = Integer.parseInt(read);
            if (readValue < 0 || readValue > 2) readValue = 0;
        }
        catch (Exception ignored) {}
        return readValue == 0 ? " TRUE " : readValue == 1 ? " msg.read = TRUE " : " msg.read = FALSE ";
    }

    public Page<Message> findAllSentMessagesByEmail(String email,
                                                       String topic,
                                                       String receiver,
                                                       String date,
                                                       Pageable pageable) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        String topicWhere = queryTopic(topic);
        String receiverWhere = queryReceiver(receiver);
        String dateWhere = queryDate(date);

        Long total = this.getJdbcTemplate().queryForObject(
                String.format(MessageQueries.QUERY_COUNT_TOTAL_SENT_BY_EMAIL, " AND " + topicWhere + " AND " + receiverWhere + " AND " + dateWhere),
                (rs, rowNum) -> rs.getLong(1),
                email
        );
        if (total == null) {
            return null;
        }

        List<Message> messages = this.getJdbcTemplate().query(
                String.format(MessageQueries.QUERY_FIND_ALL_SENT_BY_EMAIL, " AND " + topicWhere + " AND " + receiverWhere + " AND " + dateWhere + " "),
                new MessageSentMapper(),
                email, pageable.getPageSize(), pageable.getOffset()
        );
        return new PageImpl<>(messages, pageable, total);
    }

    public Page<Message> findAllReceivedMessagesByUsername(String email,
                                                           String topic,
                                                           String date,
                                                           String sender,
                                                           String read,
                                                           Pageable pageable) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        String topicWhere = queryTopic(topic);
        String senderWhere = querySender(sender);
        String dateWhere = queryDate(date);
        String readWhere = queryRead(read);

        Long total = this.getJdbcTemplate().queryForObject(
                String.format(MessageQueries.QUERY_COUNT_TOTAL_RECEIVED_BY_EMAIL, topicWhere + " AND " + senderWhere + " AND " + dateWhere + " AND " + readWhere),
                (rs, rowNum) -> rs.getLong(1),
                email, email
        );
        if (total == null) {
            return null;
        }

        List<Message> messages = this.getJdbcTemplate().query(
                String.format(MessageQueries.QUERY_FIND_ALL_RECEIVED_BY_EMAIL, topicWhere + " AND " + senderWhere + " AND " + dateWhere + " AND " + readWhere + " "),
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

    public void saveSentMessage(Message message) {
        if (null == this.getJdbcTemplate()) {
            return;
        }

        this.getJdbcTemplate().update(
            con -> {
                PreparedStatement ps = con.prepareStatement(MessageQueries.QUERY_SAVE_SENT_MESSAGE, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, message.getMessageID());
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

    public void deleteSentMessage(Long messageID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(MessageQueries.QUERY_DELETE_SENT_MESSAGE, messageID);
    }
}
