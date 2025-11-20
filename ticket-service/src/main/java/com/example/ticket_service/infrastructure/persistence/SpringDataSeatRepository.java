package com.example.ticket_service.infrastructure.persistence;

import com.example.ticket_service.domain.Seat;
import com.example.ticket_service.domain.Seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataSeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByEventIdAndStatus(UUID eventId, SeatStatus status);
    Optional<Seat> findByEventIdAndSeatNumber(UUID eventId, String seatNumber);
}
