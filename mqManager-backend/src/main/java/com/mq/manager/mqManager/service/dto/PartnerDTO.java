package com.mq.manager.mqManager.service.dto;

import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;

import lombok.Data;

@Data
public class PartnerDTO {
    private Long id;
    private String alias;
    private String type;
    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;
    private String description;
}
