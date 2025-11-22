package com.example.ticket_service.interfaces.rest;

import com.example.ticket_service.application.TicketService;
import com.example.ticket_service.domain.Ticket;
import com.example.ticket_service.interfaces.rest.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/{ticketId}/qr/regenerate")
    public ResponseEntity<TicketResponse> regenerateQr(@PathVariable UUID ticketId) {
        Ticket t = service.regenerateQr(ticketId);
        return ResponseEntity.ok(toDto(t));
    }

    @PostMapping("/{ticketId}/transfer")
    public ResponseEntity<TicketResponse> transfer(@PathVariable UUID ticketId, @RequestParam UUID fromUser, @RequestParam UUID toUser) {
        Ticket t = service.transferTicket(ticketId, fromUser, toUser);
        return ResponseEntity.ok(toDto(t));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID ticketId) {
        Ticket t = service.getTicket(ticketId);
        return ResponseEntity.ok(toDto(t));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> listByOwner(@RequestParam UUID ownerId) {
        java.util.List<Ticket> tickets = service.getTicketsByOwner(ownerId);
        java.util.List<TicketResponse> dto = tickets.stream().map(this::toDto).toList();
        return ResponseEntity.ok(dto);
    }

    private TicketResponse toDto(Ticket t) {
        if (t == null) return null;
        return new TicketResponse(t.getId(), t.getSeatId(), t.getOwnerId(), t.getIssuedAt(), t.getQrPayload());
    }
}
