package com.example.ticket_service.infrastructure.persistence;

import com.example.ticket_service.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByOwnerId(UUID ownerId);
}
