package ru.nsu.spirin.gamestudios.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Message {
    private Long messageID;
    private Timestamp date;
    private String topic;
    private String content;
    private List<Attachment> attachments;
    private String sender;
    private List<String> receivers;
    private Boolean read;

    private String receiversString;

    public Message(Long messageID, Timestamp date, String topic,
                   String content, List<Attachment> attachments, String sender,
                   List<String> receivers, Boolean read) {
        this.messageID = messageID;
        this.date = date;
        this.topic = topic;
        this.content = content;
        this.attachments = attachments;
        this.sender = sender;
        this.receivers = receivers;
        this.read = read;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return false;
        if (!(o instanceof Message)) return false;
        return Objects.equals(this.messageID, ((Message) o).messageID);
    }

    public void convertReceivers() {
        receivers = new ArrayList<>();
        for (var receiver : receiversString.split(",")) {
            receivers.add(receiver.trim());
        }
    }
}
