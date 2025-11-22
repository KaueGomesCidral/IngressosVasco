package com.example.payment_service.interfaces.rest;

import com.example.payment_service.application.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequest req) {
        boolean ok = service.processPayment(req.reservationId());

        if (ok) {
            return ResponseEntity.created(URI.create("/api/payments")).build();
        } else {
            return ResponseEntity.status(502).build();
        }
    }
}