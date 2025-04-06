package com.mq.manager.mqManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mq.manager.mqManager.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
