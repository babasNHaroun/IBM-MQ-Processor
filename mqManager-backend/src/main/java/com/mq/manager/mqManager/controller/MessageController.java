package com.mq.manager.mqManager.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.service.MQService;
import com.mq.manager.mqManager.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;
    private MQService mqService;

    public MessageController(MessageService messageService, MQService mqService) {
        this.messageService = messageService;
        this.mqService = mqService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> simulateMessage(@RequestBody String content) {
        String response = mqService.sendMessageToMQ(content);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
