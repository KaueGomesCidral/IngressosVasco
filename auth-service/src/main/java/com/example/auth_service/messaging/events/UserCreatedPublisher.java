package com.example.auth_service.messaging.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreatedPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(UserCreatedEvent event) {
        log.info("Publishing UserCreatedEvent: {}", event);
        eventPublisher.publishEvent(event);
    }

    public void send(UserCreatedEvent event) {
        publish(event);
    }

    public void publishUserCreated(UserCreatedEvent event) {
        publish(event);
    }
}
