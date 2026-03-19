package com.cleanarch.application.outbox.port;

import com.cleanarch.domain.model.OutboxEvent;

public interface EventPublisherPort {

    void publish(OutboxEvent event);

}
