package com.example.ticket_service.interfaces.rest;

import com.example.ticket_service.application.TicketService;
import com.example.ticket_service.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final TicketService service;

    public ReservationController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestParam UUID userId, @RequestParam UUID eventId, @RequestBody List<String> seatNumbers, @RequestParam long ttlSeconds) {
        Reservation r = service.createReservation(userId, eventId, seatNumbers, ttlSeconds);
        return ResponseEntity.accepted().body(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        service.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/commit")
    public ResponseEntity<Void> commit(@PathVariable UUID id) {
        service.commitReservation(id);
        return ResponseEntity.ok().build();
    }
}
