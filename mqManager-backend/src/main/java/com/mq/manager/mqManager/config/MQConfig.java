package com.mq.manager.mqManager.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MQConfig {
    private static final Logger logger = LoggerFactory.getLogger(MQConfig.class);

    @Bean
    public MQQueueConnectionFactory connectionFactory() {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        try {
            factory.setHostName("localhost");
            factory.setPort(1414);
            factory.setQueueManager("QM1");
            factory.setChannel("DEV.APP.SVRCONN");

        } catch (javax.jms.JMSException e) {
            logger.error("Failed to configure MQQueueConnectionFactory", e);
            throw new RuntimeException("MQ configuration failed", e);
        }
        return factory;
    }
}