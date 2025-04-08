package com.mq.manager.mqManager.Utils.helpers;

import org.springframework.stereotype.Component;

import com.mq.manager.mqManager.entity.Message;
import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.service.dto.MessageDTO;
import com.mq.manager.mqManager.service.dto.PartnerDTO;

@Component
public class Mapper {

    public MessageDTO convertMessageToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setContent(message.getContent());
        messageDTO.setReceivedAt(message.getReceivedAt());
        messageDTO.setSourceApplication(message.getSourceApplication());
        messageDTO.setQueueName(message.getQueueName());
        return messageDTO;
    }

    public PartnerDTO convertPartnerToDTO(Partner partner) {
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setId(partner.getId());
        partnerDTO.setAlias(partner.getAlias());
        partnerDTO.setType(partner.getType());
        partnerDTO.setDirection(partner.getDirection());
        partnerDTO.setApplication(partner.getApplication());
        partnerDTO.setProcessedFlowType(partner.getProcessedFlowType());
        partnerDTO.setDescription(partner.getDescription());
        return partnerDTO;
    }

}
