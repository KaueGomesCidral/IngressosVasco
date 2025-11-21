package com.example.ticket_service.interfaces.rest.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReservationResponse(
    UUID id,
    UUID userId,
    Instant createdAt,
    Instant expiresAt,
    List<SeatResponse> seats
) {}
