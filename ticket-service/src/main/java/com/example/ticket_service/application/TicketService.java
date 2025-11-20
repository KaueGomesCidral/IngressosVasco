package com.example.ticket_service.application;

import com.example.ticket_service.domain.Reservation;
import com.example.ticket_service.domain.Seat;
import com.example.ticket_service.domain.Ticket;
import com.example.ticket_service.infrastructure.persistence.SpringDataReservationRepository;
import com.example.ticket_service.infrastructure.persistence.SpringDataSeatRepository;
import com.example.ticket_service.infrastructure.persistence.SpringDataTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final SpringDataSeatRepository seatRepo;
    private final SpringDataReservationRepository reservationRepo;
    private final SpringDataTicketRepository ticketRepo;
    private final QRService qrService;

    public TicketService(SpringDataSeatRepository seatRepo,
                         SpringDataReservationRepository reservationRepo,
                         SpringDataTicketRepository ticketRepo,
                         QRService qrService) {
        this.seatRepo = seatRepo;
        this.reservationRepo = reservationRepo;
        this.ticketRepo = ticketRepo;
        this.qrService = qrService;
    }

    @Transactional
    public Reservation createReservation(UUID userId, UUID eventId, List<String> seatNumbers, long ttlSeconds) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(ttlSeconds);

        List<Seat> reservedSeats = seatNumbers.stream().map(sn -> {
            Seat seat = seatRepo.findByEventIdAndSeatNumber(eventId, sn)
                    .orElseThrow(() -> new IllegalArgumentException("seat not found: " + sn));
            if (seat.getStatus() != Seat.SeatStatus.AVAILABLE) {
                throw new IllegalStateException("seat not available: " + sn);
            }
            seat.setStatus(Seat.SeatStatus.RESERVED);
            seat.setReservedBy(userId);
            return seatRepo.save(seat);
        }).toList();

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setCreatedAt(now);
        reservation.setExpiresAt(expiresAt);
        reservation.setSeats(reservedSeats);

        Reservation saved = reservationRepo.save(reservation);

        reservedSeats.forEach(s -> { s.setReservationId(saved.getId()); seatRepo.save(s); });

        return saved;
    }

    @Transactional
    public void cancelReservation(UUID reservationId) {
        Optional<Reservation> opt = reservationRepo.findById(reservationId);
        if (opt.isEmpty()) return;

        Reservation r = opt.get();
        r.getSeats().forEach(s -> {
            s.setStatus(Seat.SeatStatus.AVAILABLE);
            s.setReservedBy(null);
            s.setReservationId(null);
            seatRepo.save(s);
        });

        reservationRepo.delete(r);
    }

    @Transactional
    public Ticket commitReservation(UUID reservationId) {
        Reservation r = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("reservation not found"));

        Instant now = Instant.now();
        for (Seat s : r.getSeats()) {
            s.setStatus(Seat.SeatStatus.SOLD);
            seatRepo.save(s);

            Ticket t = new Ticket();
            t.setSeatId(s.getId());
            t.setOwnerId(r.getUserId());
            t.setIssuedAt(now);
            t.setQrPayload(qrService.generatePayloadForTicket(s.getId(), r.getUserId()));
            ticketRepo.save(t);
        }

        reservationRepo.delete(r);
        return ticketRepo.findByOwnerId(r.getUserId()).stream().findFirst().orElse(null);
    }

    @Transactional
    public Ticket transferTicket(UUID ticketId, UUID fromUser, UUID toUser) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("ticket not found"));
        if (!ticket.getOwnerId().equals(fromUser)) throw new IllegalStateException("not ticket owner");
        ticket.setOwnerId(toUser);
        ticket.setQrPayload(qrService.generatePayloadForTicket(ticket.getSeatId(), toUser));
        return ticketRepo.save(ticket);
    }

    @Transactional
    public Ticket regenerateQr(UUID ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("ticket not found"));
        ticket.setQrPayload(qrService.generatePayloadForTicket(ticket.getSeatId(), ticket.getOwnerId()));
        return ticketRepo.save(ticket);
    }
}
