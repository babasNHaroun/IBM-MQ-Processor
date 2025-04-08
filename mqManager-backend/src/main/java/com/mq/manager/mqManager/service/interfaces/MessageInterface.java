package com.mq.manager.mqManager.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.service.dto.MessageDTO;

public interface MessageInterface {

    Message saveMessage(Message message);

    Page<MessageDTO> getAllMessages(Pageable pageable);
}
