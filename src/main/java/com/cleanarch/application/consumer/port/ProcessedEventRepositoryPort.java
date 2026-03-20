package com.cleanarch.application.consumer.port;

import java.util.UUID;

public interface ProcessedEventRepositoryPort {

    void insert(UUID eventId);

}
