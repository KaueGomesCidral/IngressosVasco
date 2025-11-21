package com.example.ticket_service.interfaces.rest;

import com.example.ticket_service.application.TicketService;
import com.example.ticket_service.domain.Ticket;
import com.example.ticket_service.interfaces.rest.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private TicketResponse toDto(Ticket t) {
        if (t == null) return null;
        return new TicketResponse(t.getId(), t.getSeatId(), t.getOwnerId(), t.getIssuedAt(), t.getQrPayload());
    }
}
