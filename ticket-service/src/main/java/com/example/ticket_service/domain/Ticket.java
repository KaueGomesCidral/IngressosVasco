package com.example.ticket_service.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    private UUID id;

    @Column(name = "seat_id", nullable = false)
    private UUID seatId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "qr_payload", columnDefinition = "text")
    private String qrPayload;

    public Ticket() {}

    @PrePersist
    public void ensureId() { if (this.id == null) this.id = UUID.randomUUID(); }

    public UUID getId() { return id; }
    public UUID getSeatId() { return seatId; }
    public void setSeatId(UUID seatId) { this.seatId = seatId; }
    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID ownerId) { this.ownerId = ownerId; }
    public Instant getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }
    public String getQrPayload() { return qrPayload; }
    public void setQrPayload(String qrPayload) { this.qrPayload = qrPayload; }
}
