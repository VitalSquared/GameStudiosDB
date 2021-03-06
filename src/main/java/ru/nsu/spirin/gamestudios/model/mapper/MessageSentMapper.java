package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.message.Attachment;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.utils.AttachmentUtils;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
        Array array = rs.getArray("receivers");
        List<String> receivers = array == null ? new ArrayList<>() : List.of((String[])array.getArray());

        return new Message(messageID, date, topic, content, attachments, sender, receivers, true);
    }
}
