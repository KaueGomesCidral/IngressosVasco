package com.example.ticket_service.interfaces.rest;

import com.example.ticket_service.application.TicketService;
import com.example.ticket_service.domain.Reservation;
import com.example.ticket_service.domain.Seat;
import com.example.ticket_service.domain.Ticket;
import com.example.ticket_service.interfaces.rest.dto.ReservationResponse;
import com.example.ticket_service.interfaces.rest.dto.SeatResponse;
import com.example.ticket_service.interfaces.rest.dto.TicketResponse;
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
    public ResponseEntity<ReservationResponse> create(@RequestParam UUID userId, @RequestParam UUID eventId, @RequestBody List<String> seatNumbers, @RequestParam long ttlSeconds) {
        Reservation r = service.createReservation(userId, eventId, seatNumbers, ttlSeconds);
        return ResponseEntity.accepted().body(toDto(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        service.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/commit")
    public ResponseEntity<TicketResponse> commit(@PathVariable UUID id) {
        Ticket t = service.commitReservation(id);
        return ResponseEntity.ok(toDto(t));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id) {
        Reservation r = service.getReservation(id);
        return ResponseEntity.ok(toDto(r));
    }

    private ReservationResponse toDto(Reservation r) {
        List<SeatResponse> seats = r.getSeats().stream()
                .map(this::toSeatDto)
                .toList();

        return new ReservationResponse(
                r.getId(),
                r.getUserId(),
                r.getCreatedAt(),
                r.getExpiresAt(),
                seats
        );
    }

    private SeatResponse toSeatDto(Seat s) {
        return new SeatResponse(s.getId(), s.getSeatNumber(), s.getStatus().name());
    }

    private TicketResponse toDto(Ticket t) {
        if (t == null) return null;
        return new TicketResponse(t.getId(), t.getSeatId(), t.getOwnerId(), t.getIssuedAt(), t.getQrPayload());
    }
}
