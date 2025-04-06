package com.mq.manager.mqManager.entity;

import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "alias is required")
    @Column(nullable = false)
    private String alias;

    @NotBlank(message = "Type is required")
    @Column(nullable = false)
    private String type;

    @NotNull(message = "Direction is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    private String application;

    @NotNull(message = "Processed flow type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "processed_flow_type", nullable = false)
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String description;
}
