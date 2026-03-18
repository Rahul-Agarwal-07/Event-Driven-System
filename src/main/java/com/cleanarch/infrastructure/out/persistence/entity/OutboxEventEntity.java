package com.cleanarch.infrastructure.out.persistence.entity;

import com.cleanarch.domain.model.OutboxStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
public class OutboxEventEntity {

    @Id
    private UUID id;

    private UUID aggregateId;
    private String aggregateType;
    private String eventType;
    private Integer eventVersion;

    @Column(columnDefinition = "jsonb")
    private String payload;

    private String status;
    private Integer retryCount;
    private String errorMessage;

    private Instant nextRetryAt;
    private Instant createdAt;
    private Instant processedAt;


    // Getters

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getId() {
        return id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getEventVersion() {
        return eventVersion;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Instant getNextRetryAt() {
        return nextRetryAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}
