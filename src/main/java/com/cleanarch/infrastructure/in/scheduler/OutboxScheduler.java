package com.cleanarch.infrastructure.in.scheduler;

import com.cleanarch.application.outbox.port.PublishOutboxUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final PublishOutboxUseCasePort publishOutboxUseCase;

    @Scheduled(fixedDelay = 1000)
    public void poll()
    {
        publishOutboxUseCase.execute();
    }

}
