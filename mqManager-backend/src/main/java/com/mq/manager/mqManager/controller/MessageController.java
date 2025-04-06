package com.mq.manager.mqManager.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.findAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Message>> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findMessageById(id));
    }

    @PostMapping("/simulate")
    public ResponseEntity<Void> simulateMessage(@RequestBody String content) {
        Message message = new Message();
        message.setContent(content);
        message.setReceivedAt(LocalDateTime.now());
        messageService.saveMessage(message);
        return ResponseEntity.ok().build();
    }

}
