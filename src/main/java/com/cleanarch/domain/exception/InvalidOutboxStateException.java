package com.cleanarch.domain.exception;

public class InvalidOutboxStateException extends DomainException {
    public InvalidOutboxStateException(String message) {
        super(message);
    }
}
