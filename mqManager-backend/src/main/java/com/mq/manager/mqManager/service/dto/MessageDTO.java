package com.mq.manager.mqManager.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String messageId;
    private String content;
    private LocalDateTime receivedAt;
    private String sourceApplication;
    private String queueName;
}
