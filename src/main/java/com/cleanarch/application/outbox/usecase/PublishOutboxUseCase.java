package com.cleanarch.application.outbox.usecase;

import com.cleanarch.application.outbox.port.EventPublisherPort;
import com.cleanarch.application.outbox.port.OutboxRepositoryPort;
import com.cleanarch.application.outbox.port.PublishOutboxUseCasePort;
import com.cleanarch.domain.model.OutboxEvent;

import java.util.List;

public class PublishOutboxUseCase implements PublishOutboxUseCasePort {

    private static final int BATCH_SIZE = 50;
    private final OutboxRepositoryPort outboxRepository;
    private final EventPublisherPort eventPublisher;

    public PublishOutboxUseCase(OutboxRepositoryPort outboxRepository, EventPublisherPort eventPublisher) {
        this.outboxRepository = outboxRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute() {

        List<OutboxEvent> pendingEvents = outboxRepository.fetchPendingEvents(BATCH_SIZE);

        outboxRepository.markAsProcessing(
                pendingEvents.stream()
                        .map(OutboxEvent::getId)
                        .toList()
        );

        for(OutboxEvent event : pendingEvents)
        {
            try
            {
                eventPublisher.publish(event);
                outboxRepository.markAsPublished(event.getId());
            }
            catch(Exception e)
            {
                outboxRepository.markAsFailed(event.getId(), e.getMessage());
            }
        }
    }
}
