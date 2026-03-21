package com.cleanarch.infrastructure.out.persistence.mapper;

import com.cleanarch.domain.model.EventType;
import com.cleanarch.domain.model.OutboxEvent;
import com.cleanarch.domain.model.OutboxStatus;
import com.cleanarch.infrastructure.out.persistence.entity.OutboxEventEntity;

public class OutboxMapper {

    public static OutboxEvent toDomain(OutboxEventEntity entity)
    {
        return new OutboxEvent(
            entity.getId(),
            entity.getAggregateId(),
            entity.getAggregateType(),
            EventType.valueOf(entity.getEventType()),
            entity.getEventVersion(),
            entity.getPayload(),
            OutboxStatus.valueOf(entity.getStatus()),
            entity.getRetryCount(),
            entity.getErrorMessage(),
            entity.getNextRetryAt(),
            entity.getCreatedAt(),
            entity.getProcessedAt()
        );
    }

}
