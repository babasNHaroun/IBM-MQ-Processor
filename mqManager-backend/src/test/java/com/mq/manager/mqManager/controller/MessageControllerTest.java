package com.mq.manager.mqManager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.mq.manager.mqManager.service.MessageService;
import com.mq.manager.mqManager.service.dto.MessageDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private MessageDTO messageDTO;

    @BeforeEach
    void setUp() {
        messageDTO = new MessageDTO();
        messageDTO.setContent("Test message");
        messageDTO.setMessageId("ID: ID123123123");
        messageDTO.setQueueName("DEV.QUEUE.1");
    }

    @Test
    void getAllMessages_ShouldReturnPageOfMessages() throws Exception {
        List<MessageDTO> messages = Arrays.asList(
                new MessageDTO("ID:123", "Message 1", LocalDateTime.now(), "APP_1", "DEV.QUEUE.1"),
                new MessageDTO("ID:456", "Message 2", LocalDateTime.now(), "APP_2", "DEV.QUEUE.1"));
        Page<MessageDTO> messagePage = new PageImpl<>(messages, PageRequest.of(0, 10), messages.size());

        when(messageService.getAllMessages(any(PageRequest.class))).thenReturn(messagePage);

        mockMvc.perform(get("/api/messages")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getMessageById_ShouldReturnMessage() throws Exception {
        when(messageService.findMessageById(1L)).thenReturn(Optional.of(messageDTO));

        mockMvc.perform(get("/api/messages/1"))
                .andExpect(jsonPath("$.messageId").value("ID: ID123123123"))
                .andExpect(status().isOk());
    }

    @Test
    void getMessageById_WhenMessageNotFound_ShouldReturnEmpty() throws Exception {
        when(messageService.findMessageById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/messages/999"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMessage_ShouldReturnNoContent() throws Exception {
        doNothing().when(messageService).deleteMessage(1L);

        mockMvc.perform(delete("/api/messages/1"))
                .andExpect(status().isNoContent());
    }
}