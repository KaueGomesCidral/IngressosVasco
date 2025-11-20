package com.example.ticket_service.interfaces.rest;

import com.example.events_service.application.TicketService;
import com.example.events_service.domain.Ticket;
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
    public ResponseEntity<String> regenerateQr(@PathVariable UUID ticketId) {
        Ticket t = service.regenerateQr(ticketId);
        return ResponseEntity.ok(t.getQrPayload());
    }

    @PostMapping("/{ticketId}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable UUID ticketId, @RequestParam UUID fromUser, @RequestParam UUID toUser) {
        service.transferTicket(ticketId, fromUser, toUser);
        return ResponseEntity.ok().build();
    }
}
