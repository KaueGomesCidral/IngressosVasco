package com.example.ticket_service.infrastructure.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Component
public class HistoryClient {

    private final RestTemplate rest = new RestTemplate();
    private final String url = "http://history-service:8080/api/history/purchases";

    public record PurchaseDto(UUID userId, UUID ticketId, UUID eventId, UUID seatId, Instant issuedAt) {}

    public void recordPurchase(UUID userId, UUID ticketId, UUID eventId, UUID seatId, Instant issuedAt) {
        PurchaseDto dto = new PurchaseDto(userId, ticketId, eventId, seatId, issuedAt);
        try {
            rest.postForEntity(url, dto, Void.class);
        } catch (Exception ignored) {
        }
    }
}