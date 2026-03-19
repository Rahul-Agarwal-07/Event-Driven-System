package com.cleanarch.infrastructure.out.persistence.repository;
import com.cleanarch.application.outbox.port.OutboxRepositoryPort;
import com.cleanarch.domain.model.OutboxEvent;
import com.cleanarch.infrastructure.out.persistence.entity.OutboxEventEntity;
import com.cleanarch.infrastructure.out.persistence.mapper.OutboxMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepositoryPort {

    private EntityManager entityManager;

    @Override
    @Transactional
    public List<OutboxEvent> fetchPendingEvents(int batchSize) {

        List<OutboxEventEntity> entities = entityManager
                .createNativeQuery("""
                      SELECT * FROM outbox_events
                      WHERE status = 'PENDING'
                      ORDER BY createdAt
                      LIMIT :batchSize
                      FOR UPDATE SKIP LOCKED
                    """, OutboxEventEntity.class)
                .setParameter("batchSize", batchSize)
                .getResultList();

        return entities.stream()
                .map(OutboxMapper::toDomain)
                .toList();

    }

    @Override
    @Transactional
    public void markAsProcessing(List<UUID> ids) {

        entityManager
                .createQuery("""
                        UPDATE OutboxEventEntity e
                        SET e.status = 'PROCESSING'
                        WHERE e.id IN :ids
                    """
                )
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void markAsPublished(UUID eventId) {
        entityManager
                .createQuery("""
                        UPDATE OutboxEventEntity e
                        SET e.status = 'PUBLISHED', e.processedAt = CURRENT_TIMESTAMP
                        WHERE e.id = :eventId
                    """
                )
                .setParameter("eventId", eventId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void markAsFailed(UUID eventId, String error) {
        entityManager
                .createQuery("""
                        UPDATE OutboxEventEntity e
                        SET e.status = 'FAILED',
                            e.nextRetryAt = CURRENT_TIMESTAMP + INTERVAL '5 seconds',
                            e.retryCount = COALESCE(e.nextRetryAt,0) + 1,
                            e.errorMessage = :error
                        WHERE e.id = :eventId
                    """
                )
                .setParameter("eventId", eventId)
                .setParameter("error", error)
                .executeUpdate();
    }
}
