package com.mq.manager.mqManager.service.interfaces;

import javax.jms.Message;

public interface MQInterface {

    public void consumeMQMessage(Message message);

}
