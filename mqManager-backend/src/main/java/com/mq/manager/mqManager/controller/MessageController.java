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
        // Create a list to store all message IDs
        List<Long> messageIds = new ArrayList<>();

        // Define the possible source applications
        String[] sourceApps = { "app_1", "app_2", "app_3", "app_4", "app_5" };

        // Generate 100 messages
        for (int i = 1; i <= 100; i++) {
            Message message = new Message();
            message.setMessageId("message_" + i);
            message.setContent(content + " (Message " + i + ")");
            message.setReceivedAt(LocalDateTime.now());

            // Randomly choose a source application from the defined list
            int appIndex = (int) (Math.random() * sourceApps.length);
            message.setSourceApplication(sourceApps[appIndex]);

            // Save the message and collect its ID
            messageService.save(message);
            messageIds.add(message.getId());
        }

        String response = "Generated 100 messages with IDs: " + messageIds;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
