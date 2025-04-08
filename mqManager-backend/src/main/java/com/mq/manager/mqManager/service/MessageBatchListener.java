package com.mq.manager.mqManager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.repository.MessageRepository;
import com.mq.manager.mqManager.service.interfaces.MQInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class MessageBatchListener implements MQInterface {

    private final MessageRepository messageRepository;
    private final BlockingQueue<javax.jms.Message> messageBuffer;
    private final ExecutorService executorService;
    private static final int BATCH_SIZE = 100;
    private static final int BUFFER_SIZE = 1000;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 1000;

    private static final Logger logger = LoggerFactory.getLogger(MessageBatchListener.class);

    @Value("${ibm.mq.queue}")
    private String queueName;

    @Autowired
    public MessageBatchListener(MessageRepository messageRepository) {
        // this.messageService = messageService;
        this.messageRepository = messageRepository;
        this.messageBuffer = new LinkedBlockingQueue<>(BUFFER_SIZE);
        this.executorService = Executors.newFixedThreadPool(5);
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void consumeMQMessage(javax.jms.Message message) {
        try {
            messageBuffer.offer(message);
            if (messageBuffer.size() >= BATCH_SIZE) {
                processBatch();
            }
        } catch (Exception e) {
            logger.error("Error consuming message: ", e);
        }
    }

    private void processBatch() {
        List<javax.jms.Message> batch = new ArrayList<>();
        messageBuffer.drainTo(batch, BATCH_SIZE);

        if (!batch.isEmpty()) {
            executorService.submit(() -> {
                try {
                    processBatchMessages(batch);
                    logger.info("Processed batch of {} messages", batch.size());
                } catch (Exception e) {
                    logger.error("Error processing batch: ", e);
                }
            });
        }
    }

    private void processBatchMessages(List<javax.jms.Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return;
        }

        List<Message> messagesToSave = new ArrayList<>();

        for (javax.jms.Message jmsMessage : messages) {
            try {
                String messageContent = extractMessageContent(jmsMessage);
                String messageId = jmsMessage.getJMSMessageID();

                String sourceApp = "UNKOWN";
                if (jmsMessage.propertyExists("sourceApplication")) {
                    sourceApp = jmsMessage.getStringProperty("sourceApplication");
                } else if (jmsMessage.propertyExists("JMSXAppID")) {
                    sourceApp = jmsMessage.getStringProperty("JMSXAppID");
                }

                Message message = new Message();
                message.setContent(messageContent);
                message.setMessageId(messageId);
                message.setQueueName(queueName);
                message.setSourceApplication(sourceApp);
                message.setReceivedAt(LocalDateTime.now());

                messagesToSave.add(message);
            } catch (Exception e) {
                logger.error("Error processing message: {}", e.getMessage());
            }
        }

        if (!messagesToSave.isEmpty()) {
            int retryCount = 0;
            boolean saved = false;

            // adding a retry mechanism for failed attempts at saving batches
            while (retryCount < MAX_RETRY_ATTEMPTS && !saved) {
                try {
                    messageRepository.saveAll(messagesToSave);
                    logger.info("Successfully saved batch of {} messages", messagesToSave.size());
                    saved = true;
                } catch (Exception e) {
                    retryCount++;
                    logger.error("Retry attempt {} of {} - Error trying to save batch of messages: {}",
                            retryCount, MAX_RETRY_ATTEMPTS, e.getMessage());

                    if (retryCount < MAX_RETRY_ATTEMPTS) {
                        try {
                            Thread.sleep(RETRY_DELAY_MS * retryCount); // Simple backoff
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    } else {
                        logger.error("Failed to save batch after {} attempts. Messages will be lost.",
                                MAX_RETRY_ATTEMPTS);
                    }
                }
            }
        }
    }

    // extract the message content from the JMS message
    private String extractMessageContent(javax.jms.Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            return new String(bytes);
        }
        return "Unsupported message type";
    }

    // process any remaining messages in the buffer every 10 seconds
    @Scheduled(fixedDelay = 10000)
    public void processRemainingMessages() {
        logger.info("Processing remaining messages in the buffer");
        if (!messageBuffer.isEmpty()) {
            processBatch();
        }
    }
}
