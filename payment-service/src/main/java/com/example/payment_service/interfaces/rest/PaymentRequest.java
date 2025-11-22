package com.example.payment_service.interfaces.rest;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
    UUID reservationId,
    UUID userId,
    BigDecimal amount,
    String method
) {}