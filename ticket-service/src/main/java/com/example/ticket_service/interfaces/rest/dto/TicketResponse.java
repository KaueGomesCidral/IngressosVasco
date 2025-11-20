package com.example.ticket_service.interfaces.rest.dto;

import java.time.Instant;
import java.util.UUID;

public record TicketResponse(
    UUID id,
    UUID seatId,
    UUID ownerId,
    Instant issuedAt,
    String qrPayload
) {}
