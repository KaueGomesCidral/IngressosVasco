package com.example.events_service.messaging.events;

import java.util.UUID;

public record UserCreatedEvent (
    UUID UserID,
    String name,
    String email,
    String role
) {}