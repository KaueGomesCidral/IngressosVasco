package com.example.ticket_service.application;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class QRService {

    public String generatePayloadForTicket(UUID seatId, UUID ownerId) {
        String raw = seatId.toString() + "|" + ownerId.toString() + "|" + System.currentTimeMillis();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }
}
