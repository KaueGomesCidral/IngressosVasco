package com.example.ticket_service.infrastructure.persistence;

import com.example.ticket_service.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SpringDataReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByExpiresAtBefore(Instant now);
}
