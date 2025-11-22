package com.example.history_service.interfaces.rest;

import com.example.history_service.domain.HistoryPurchase;
import com.example.history_service.infrastructure.persistence.SpringDataHistoryPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final SpringDataHistoryPurchaseRepository repo;

    public static record PurchaseRequest(UUID userId, UUID ticketId, UUID eventId, UUID seatId, Instant issuedAt) {}

    @PostMapping("/purchases")
    public ResponseEntity<Void> create(@RequestBody PurchaseRequest req) {
        HistoryPurchase hp = new HistoryPurchase();
        hp.setUserId(req.userId());
        hp.setTicketId(req.ticketId());
        hp.setEventId(req.eventId());
        hp.setSeatId(req.seatId());
        hp.setIssuedAt(req.issuedAt());
        repo.save(hp);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PurchaseRequest>> listByUser(@PathVariable UUID userId) {
        List<PurchaseRequest> dtos = repo.findByUserId(userId).stream()
                .map(h -> new PurchaseRequest(h.getUserId(), h.getTicketId(), h.getEventId(), h.getSeatId(), h.getIssuedAt()))
                .toList();
        return ResponseEntity.ok(dtos);
    }
}