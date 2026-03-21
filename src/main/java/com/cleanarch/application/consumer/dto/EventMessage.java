package com.cleanarch.application.consumer.dto;

import com.cleanarch.domain.model.EventType;

import java.util.UUID;

public record EventMessage(
         UUID id,
         EventType eventType,
         String payload
) { }
