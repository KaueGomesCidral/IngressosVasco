package com.example.ticket_service.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "seat", uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seat_number"}))
public class Seat {

    @Id
    private UUID id;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "seat_number", nullable = false, length = 64)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column(name = "reserved_by")
    private UUID reservedBy;

    @Column(name = "reservation_id")
    private UUID reservationId;

    public Seat() {}

    @PrePersist
    public void ensureId() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

    public enum SeatStatus { AVAILABLE, RESERVED, SOLD }

    public UUID getId() { return id; }
    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
    public UUID getReservedBy() { return reservedBy; }
    public void setReservedBy(UUID reservedBy) { this.reservedBy = reservedBy; }
    public UUID getReservationId() { return reservationId; }
    public void setReservationId(UUID reservationId) { this.reservationId = reservationId; }
}
