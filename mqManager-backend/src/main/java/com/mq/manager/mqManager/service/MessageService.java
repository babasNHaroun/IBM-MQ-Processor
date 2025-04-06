package com.mq.manager.mqManager.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mq.manager.mqManager.Utils.exceptions.ResourceNotFoundException;
import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public void delete(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
    }
}