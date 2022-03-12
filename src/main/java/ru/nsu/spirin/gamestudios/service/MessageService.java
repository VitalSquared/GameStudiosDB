package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.repository.MessageRepository;

import java.security.Principal;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<Message> getSentMessagesByUsername(String email, String topic, String receiver, String date, Pageable pageable) {
        return this.messageRepository.findAllSentMessagesByEmail(email, topic, receiver, date, pageable);
    }

    public Page<Message> getReceivedMessagesByUsername(String email, String topic, String date, String sender, String read, Pageable pageable) {
        return this.messageRepository.findAllReceivedMessagesByUsername(email, topic, date, sender, read, pageable);
    }

    public Long getNumberOfUnreadMessages(String email) {
        return this.messageRepository.countReceived(email, false);
    }

    public Message getMessageByID(Long id) {
        return this.messageRepository.findByID(id);
    }

    public boolean canViewMessage(Long id, Principal principal) {
        String email = ((User) ((Authentication) principal).getPrincipal()).getUsername();
        List<Long> res = this.messageRepository.findAllIDsByEmail(id, email);
        return res.size() > 0;
    }

    public void newMessage(Message message) {
        if (message.getTopic() == null || message.getContent() == null || message.getAttachments() == null || message.getReceivers() == null) {
            return;
        }

        Long id = this.messageRepository.countMaxID() + 1;
        message.setMessageID(id);
        this.messageRepository.saveSentMessage(message);
        for (var receiver : message.getReceivers()) {
            this.messageRepository.saveReceivedMessage(message, receiver);
        }
    }

    public void readMessage(Long messageID, String email) {
        this.messageRepository.updateMessage(messageID, email, true);
    }

    public void deleteReceivedMessage(Long messageID, String email) {
        this.messageRepository.deleteReceivedMessage(messageID, email);
    }

    public void deleteSentMessage(Long messageID) {
        this.messageRepository.deleteSentMessage(messageID);
    }
}
