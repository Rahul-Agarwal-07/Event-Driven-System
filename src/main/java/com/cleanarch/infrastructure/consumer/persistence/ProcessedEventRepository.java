package com.cleanarch.infrastructure.consumer.persistence;

import com.cleanarch.application.consumer.port.ProcessedEventRepositoryPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProcessedEventRepository implements ProcessedEventRepositoryPort {

    private final EntityManager entityManager;

    public ProcessedEventRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(UUID eventId) {

        entityManager.
                createNativeQuery("""
                        INSERT INTO processed_events(eventId)
                            VALUES(:eventId)
                        """)
                .setParameter("eventId", eventId)
                .executeUpdate();
    }
}
