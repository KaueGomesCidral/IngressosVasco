package com.example.ticket_service.interfaces.rest.dto;

import java.util.UUID;

public record SeatResponse(
    UUID id,
    String seatNumber,
    String status
) {}
