package com.cleanarch.application.consumer.port;

import com.cleanarch.application.consumer.dto.EventMessage;

public interface ConsumeEventUseCasePort {

    void execute(EventMessage message);

}
