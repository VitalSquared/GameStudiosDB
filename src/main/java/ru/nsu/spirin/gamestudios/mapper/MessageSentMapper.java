package ru.nsu.spirin.gamestudios.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.Attachment;
import ru.nsu.spirin.gamestudios.model.Message;
import ru.nsu.spirin.gamestudios.utils.AttachmentUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MessageSentMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long messageID = rs.getLong("message_id");
        Timestamp date = rs.getTimestamp("date");
        String topic = rs.getString("topic");
        String content = rs.getString("content");
        List<Attachment> attachments = AttachmentUtils.parseAttachments(rs.getString("attachments"));
        String sender = rs.getString("sender");
        List<String> receivers = List.of((String[])rs.getArray("receivers").getArray());

        return new Message(messageID, date, topic, content, attachments, sender, receivers, true);
    }
}
