package com.mq.manager.mqManager.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MQConfig {
    private static final Logger logger = LoggerFactory.getLogger(MQConfig.class);

    @Value("${ibm.mq.host}")
    private String host;

    @Value("${ibm.mq.port}")
    private Integer port;

    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Bean
    public MQQueueConnectionFactory connectionFactory() {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        try {
            factory.setHostName(host);
            factory.setPort(port);
            factory.setQueueManager(queueManager);
            factory.setChannel(channel);

        } catch (javax.jms.JMSException e) {
            logger.error("Failed to configure MQQueueConnectionFactory", e);
            throw new RuntimeException("MQ configuration failed", e);
        }
        return factory;
    }
}