package com.mq.manager.mqManager.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableJms
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

    @Value("${ibm.mq.user}")
    private String user;

    @Value("${ibm.mq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        try {
            factory.setHostName(host);
            factory.setPort(port);
            factory.setQueueManager(queueManager);
            factory.setChannel(channel);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setStringProperty(WMQConstants.USERID, user);
            factory.setStringProperty(WMQConstants.PASSWORD, password);

        } catch (javax.jms.JMSException e) {
            logger.error("Failed to configure MQQueueConnectionFactory", e);
            throw new RuntimeException("MQ configuration failed", e);
        }
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }
}