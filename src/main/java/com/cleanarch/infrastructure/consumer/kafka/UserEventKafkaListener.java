package com.cleanarch.infrastructure.consumer.kafka;

import com.cleanarch.application.consumer.dto.EventMessage;
import com.cleanarch.application.consumer.port.ConsumeEventUseCasePort;
import com.cleanarch.application.consumer.usecase.ConsumeEventUseCase;
import com.cleanarch.domain.model.OutboxEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class UserEventKafkaListener {

    private final ConsumeEventUseCasePort useCase;
    private final ObjectMapper objectMapper;

    public UserEventKafkaListener(ConsumeEventUseCase useCase, ObjectMapper objectMapper) {
        this.useCase = useCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    @Transactional
    public void listen(String message, Acknowledgment ack) throws JsonProcessingException {

        EventMessage event = objectMapper.readValue(message, EventMessage.class);
        useCase.execute(event);
        ack.acknowledge();
    }

}
