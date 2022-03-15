package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.spirin.gamestudios.model.entity.message.Attachment;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.repository.filtration.Filtration;
import ru.nsu.spirin.gamestudios.service.MessageService;

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
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String redirectMessages() {
        return "redirect:/messages/sent";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN')")
    @RequestMapping(path = "/sent", method = RequestMethod.GET)
    public String indexSentMessages(Model model, Principal principal,
                                    @PageableDefault(sort = { "date" }, direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(name = "topic", required = false, defaultValue = "") String topic,
                                    @RequestParam(name = "receiver", required = false, defaultValue = "") String receiver,
                                    @RequestParam(name = "date", required = false, defaultValue = "") String date) {
        User user = (User) ((Authentication) principal).getPrincipal();
        var messages = messageService.getSentMessagesByUsername(user.getUsername(), topic, receiver, date, pageable);
        model.addAttribute("page", messages);
        model.addAttribute("topic", topic);
        model.addAttribute("receiver", receiver);
        model.addAttribute("date", date);
        model.addAttribute("url", "/messages/sent");
        model.addAttribute("numberOfUnread", messageService.getNumberOfUnreadMessages(user.getUsername()));

        Filtration filtration = new Filtration();
        filtration.addFilter("topic", null, topic);
        filtration.addFilter("receiver", null, receiver);
        filtration.addFilter("date", null, date);
        model.addAttribute("filters", filtration.buildPath());

        return "messages/sent";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN')")
    @RequestMapping(path = "/received", method = RequestMethod.GET)
    public String indexReceivedMessages(Model model, Principal principal,
                                        @PageableDefault(sort = { "date" }, direction = Sort.Direction.DESC) Pageable pageable,
                                        @RequestParam(name = "topic", required = false, defaultValue = "") String topic,
                                        @RequestParam(name = "date", required = false, defaultValue = "") String date,
                                        @RequestParam(name = "sender", required = false, defaultValue = "") String sender,
                                        @RequestParam(name = "read", required = false, defaultValue = "") String read) {
        User user = (User) ((Authentication) principal).getPrincipal();
        var messages = messageService.getReceivedMessagesByUsername(user.getUsername(),
                topic, date, sender, read, pageable);
        model.addAttribute("page", messages);
        model.addAttribute("topic", topic);
        model.addAttribute("date", date);
        model.addAttribute("sender", sender);
        model.addAttribute("read", read);
        model.addAttribute("url", "/messages/received");
        model.addAttribute("numberOfUnread", messageService.getNumberOfUnreadMessages(user.getUsername()));

        Filtration filtration = new Filtration();
        filtration.addFilter("topic", null, topic);
        filtration.addFilter("sender", null, sender);
        filtration.addFilter("date", null, date);
        filtration.addFilter("read", null, read.equals("1") || read.equals("2") ? read : "");
        model.addAttribute("filters", filtration.buildPath());

        return "messages/received";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN') && @messageService.canViewMessage(#messageID, #principal)")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long messageID, Model model, Principal principal) {
        model.addAttribute("message", messageService.getMessageByID(messageID));
        User user = (User) ((Authentication) principal).getPrincipal();
        messageService.readMessage(messageID, user.getUsername());
        return "messages/message";
    }

    @ResponseBody
    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN') && @messageService.canViewMessage(#messageID, #principal)")
    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public InputStreamResource downloadFile(@PathVariable(value = "id") Long messageID,
                                            @RequestParam(value = "name") String fileName,
                                            @RequestParam(value = "id1") Long attID,
                                            Principal principal,
                                            HttpServletResponse response) throws IOException {
        Message message = messageService.getMessageByID(messageID);
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

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN')")
    @RequestMapping(path = "/new_message", method = RequestMethod.GET)
    public String newMessage(@ModelAttribute("message") Message message, Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("numberOfUnread", messageService.getNumberOfUnreadMessages(user.getUsername()));
        return "messages/new_message";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute("message") Message message,
                         BindingResult bindingResult,
                         Principal principal,
                         @RequestParam("file") MultipartFile[] files) throws SQLException, IOException {
        if (bindingResult.hasErrors()) {
            return "messages/new_message";
        }

        User user = (User) ((Authentication) principal).getPrincipal();
        message.setSender(user.getUsername());
        message.convertReceivers();
        message.setAttachments(new ArrayList<>());

        int idx = 0;
        for (var file : files) {
            if (file != null) {
                if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty() || file.getBytes().length == 0) {
                    continue;
                }

                Attachment attachment = new Attachment();
                attachment.setID(idx);
                attachment.setName(file.getOriginalFilename());
                attachment.setContent(file.getBytes());
                message.getAttachments().add(attachment);

                idx++;
            }
        }

        messageService.newMessage(message);
        return "redirect:/messages/sent";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN') && @messageService.canViewMessage(#messageID, #principal)")
    @RequestMapping(path = "/delete_recv/{id}", method = RequestMethod.GET)
    public String removeReceived(@PathVariable("id") Long messageID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        messageService.deleteReceivedMessage(messageID, user.getUsername());
        return "redirect:/messages/received";
    }

    @PreAuthorize("hasAnyRole('DEVELOPER', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'ADMIN') && @messageService.canViewMessage(#messageID, #principal)")
    @RequestMapping(path = "/delete_sent/{id}", method = RequestMethod.GET)
    public String removeSent(@PathVariable("id") Long messageID) {
        messageService.deleteSentMessage(messageID);
        return "redirect:/messages/sent";
    }
}
