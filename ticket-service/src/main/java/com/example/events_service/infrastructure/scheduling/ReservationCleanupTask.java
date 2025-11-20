package com.example.ticket_service.infrastructure.scheduling;

import com.example.events_service.infrastructure.persistence.SpringDataReservationRepository;
import com.example.events_service.application.TicketService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReservationCleanupTask {

    private final SpringDataReservationRepository reservationRepo;
    private final TicketService ticketService;

    public ReservationCleanupTask(SpringDataReservationRepository reservationRepo, TicketService ticketService) {
        this.reservationRepo = reservationRepo;
        this.ticketService = ticketService;
    }

    @Scheduled(fixedRateString = "PT1M")
    public void cleanupExpired() {
        var now = Instant.now();
        var expired = reservationRepo.findByExpiresAtBefore(now);
        expired.forEach(r -> ticketService.cancelReservation(r.getId()));
    }
}
