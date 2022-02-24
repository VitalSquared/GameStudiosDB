package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.spirin.gamestudios.dao.MessageDAO;
import ru.nsu.spirin.gamestudios.model.message.Attachment;
import ru.nsu.spirin.gamestudios.model.message.Message;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final MessageDAO messageDAO;

    @Autowired
    public MessageController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @GetMapping("/sent")
    public String indexSentMessages(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("messages", messageDAO.getSentMessagesByUsername(user.getUsername()));
        return "messages/sent";
    }

    @GetMapping("/received")
    public String indexReceivedMessages(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("messages", messageDAO.getReceivedMessagesByUsername(user.getUsername()));
        return "messages/received";
    }

    @GetMapping("/{id}")
    @PreAuthorize("@messageDAO.canViewMessage(#messageID, #principal)")
    public String show(@PathVariable("id") Long messageID, Model model, Principal principal) {
        model.addAttribute("message", messageDAO.getMessageByID(messageID));
        messageDAO.readMessage(messageID, principal);
        return "messages/message";
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PreAuthorize("@messageDAO.canViewMessage(#messageID, #principal)")
    @ResponseBody
    public InputStreamResource downloadFile(@PathVariable(value = "id") Long messageID,
                                            @RequestParam(value = "name") String fileName,
                                            @RequestParam(value = "id1") Long attID,
                                            Principal principal,
                                            HttpServletResponse response) throws IOException {
        Message message = messageDAO.getMessageByID(messageID);
        Optional<Attachment> attachment = message.getAttachments()
                .stream()
                .filter(att -> att.getName().equals(fileName) && att.getID() == attID)
                .findFirst();
        if (attachment.isPresent()) {
            Attachment att = attachment.get();
            response.setHeader("Content-Disposition", "attachment; filename=" + att.getName());
            return new InputStreamResource(new ByteArrayInputStream(att.getContent()));
        }
        throw new IOException("There was no file with given name in given message");
    }

    @GetMapping("/new_message")
    public String newMessage(@ModelAttribute("message") Message message) {
        return "messages/new_message";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute("message") Message message,
                         BindingResult bindingResult,
                         Principal principal,
                         @RequestParam("file") MultipartFile file) throws SQLException, IOException {
        if (bindingResult.hasErrors()) {
            return "messages/new_message";
        }

        User user = (User) ((Authentication) principal).getPrincipal();
        message.setSender(user.getUsername());
        message.convertReceivers();
        message.setAttachments(new ArrayList<>());
        //for (var file : files) {
        if (file != null) {
            Attachment attachment = new Attachment();
            attachment.setID(0);
            attachment.setName(file.getOriginalFilename());
            attachment.setContent(file.getBytes());
            message.getAttachments().add(attachment);
        }
        //}

        messageDAO.newMessage(message);
        return "redirect:/messages/sent";
    }

    @GetMapping(value = "/delete_recv/{id}")
    public String removeReceived(@PathVariable("id") Long messageID, Principal principal) {
        messageDAO.deleteReceivedMessage(messageID, principal);
        return "redirect:/messages/received";
    }

    @GetMapping(value = "/delete_sent/{id}")
    public String removeSent(@PathVariable("id") Long messageID, Principal principal) {
        messageDAO.deleteSentMessage(messageID, principal);
        return "redirect:/messages/sent";
    }
}
