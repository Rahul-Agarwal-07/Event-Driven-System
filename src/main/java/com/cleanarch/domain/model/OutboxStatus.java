package com.cleanarch.domain.model;

public enum OutboxStatus {
    PENDING,
    PROCESSING,
    PUBLISHED,
    RETRYABLE,
    DEAD
}
