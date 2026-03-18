package com.cleanarch.application.outbox.port;

import com.cleanarch.domain.model.OutboxEvent;

import java.util.List;
import java.util.UUID;

public interface OutboxRepositoryPort {

    List<OutboxEvent> fetchPendingEvents(int batchSize);

    void markAsProcessing(List<UUID> ids);

    void markAsPublished(UUID eventId);

    void markAsFailed(UUID eventId, String error);

}
