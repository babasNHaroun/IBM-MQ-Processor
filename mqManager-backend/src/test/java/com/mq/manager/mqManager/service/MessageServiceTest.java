package com.mq.manager.mqManager.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mq.manager.mqManager.service.dto.MessageDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void testGetAllMessages() {
        Page<MessageDTO> messages = messageService.getAllMessages(PageRequest.of(0, 10));
        assertNotNull(messages);
    }

}
