package com.mq.manager.mqManager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @JmsListener(destination = "DEV.QUEUE.1")
    public void processMessage(String msg) {
        Message message = new Message();
        message.setContent(msg);
        message.setReceivedAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> findMessageById(Long id) {
        return messageRepository.findById(id);

    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}