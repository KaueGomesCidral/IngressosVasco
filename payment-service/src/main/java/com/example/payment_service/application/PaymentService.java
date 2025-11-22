package com.example.payment_service.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PaymentService {

    private final RestTemplate rest;
    private final String ticketServiceBase;

    public PaymentService(RestTemplate rest,
                          @Value("${ticket.service.url:http://localhost:8086}") String ticketServiceBase) {
        this.rest = rest;
        this.ticketServiceBase = ticketServiceBase;
    }
    public boolean processPayment(UUID reservationId) {
        boolean paymentSucceeded = simulatePaymentProcessing();

        String commitUrl = ticketServiceBase + "/api/reservations/{id}/commit";
        String cancelUrl = ticketServiceBase + "/api/reservations/{id}";

        try {
            if (paymentSucceeded) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Void> entity = new HttpEntity<>(headers);

                rest.exchange(commitUrl, HttpMethod.POST, entity, String.class, reservationId.toString());
            } else {
                rest.exchange(cancelUrl, HttpMethod.DELETE, null, Void.class, reservationId.toString());
            }
        } catch (Exception e) {
            return false;
        }

        return paymentSucceeded;
    }

    private boolean simulatePaymentProcessing() {
        return true;
    }
}