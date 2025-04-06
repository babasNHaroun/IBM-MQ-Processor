package com.mq.manager.mqManager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mq.manager.mqManager.entity.Message;

import java.time.LocalDateTime;

import javax.jms.TextMessage;

@Service
public class MQService {

    private static final Logger logger = LoggerFactory.getLogger(MQService.class);

    private final JmsTemplate jmsTemplate;
    private final MessageService messageService;

    @Value("${ibm.mq.queue}")
    private String queueName;

    public MQService(JmsTemplate jmsTemplate, MessageService messageService) {
        this.jmsTemplate = jmsTemplate;
        this.messageService = messageService;
    }

    /**
     * Listens for messages on the predefined queue
     * 
     * @param receivedMessage The JMS message received from the queue
     * @throws Exception
     */
    @JmsListener(destination = "${ibm.mq.queue}")
    @Transactional
    public void receiveMessage(javax.jms.Message receivedMessage) throws Exception {
        try {
            String messageContent;
            String messageId = receivedMessage.getJMSMessageID();

            if (receivedMessage instanceof TextMessage) {
                messageContent = ((TextMessage) receivedMessage).getText();
            } else {
                messageContent = "Non-text message received: " + receivedMessage.toString();
            }

            logger.info("Received message with ID: {} from queue: {}", messageId, queueName);

            String sourceApp = "Unknown";
            if (receivedMessage.propertyExists("sourceApplication")) {
                sourceApp = receivedMessage.getStringProperty("sourceApplication");
            } else if (receivedMessage.propertyExists("JMS_IBM_PutApplName")) {
                sourceApp = receivedMessage.getStringProperty("JMS_IBM_PutApplName");
            }

            Message message = new Message();
            message.setMessageId(messageId);
            message.setContent(messageContent);
            message.setReceivedAt(LocalDateTime.now());
            message.setSourceApplication(sourceApp);

            messageService.save(message);

            logger.info("Message saved in the Databse with ID: {}", message.getId());
        } catch (Exception e) {
            logger.error("Error processing MQ message", e);
            throw e;
        }
    }

    public String sendMessageToMQ(String content) {
        jmsTemplate.send(queueName, session -> {
            TextMessage message = session.createTextMessage(content);
            message.setStringProperty("sourceApplication", "TEST_APP");
            logger.info("Sending test message to queue: {}", queueName);
            return message;
        });

        logger.info("Test message sent to queue: {}", queueName);
        return "Message sent to queue: " + queueName;
    }
}
