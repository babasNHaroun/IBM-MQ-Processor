package com.mq.manager.mqManager.entity;

import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String alias;

    @NonNull
    private String type;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    private String application;

    @Enumerated(EnumType.STRING)
    private ProcessedFlowType flowType;

    @NonNull
    private String description;
}
