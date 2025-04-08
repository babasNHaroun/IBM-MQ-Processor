package com.mq.manager.mqManager.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mq.manager.mqManager.Utils.exceptions.ResourceNotFoundException;
import com.mq.manager.mqManager.Utils.helpers.Mapper;
import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.repository.MessageRepository;
import com.mq.manager.mqManager.service.dto.MessageDTO;
import com.mq.manager.mqManager.service.interfaces.MessageInterface;

@Service
public class MessageService implements MessageInterface {

    private final MessageRepository messageRepository;
    private final Mapper mapper;

    public MessageService(MessageRepository messageRepository, Mapper mapper) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
    }

    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable)
                .map(mapper::convertMessageToDTO);
    }

    public Optional<MessageDTO> findMessageById(Long id) {
        return messageRepository.findById(id).map(mapper::convertMessageToDTO);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
    }
}