package com.example.ticket_service.interfaces.rest;

import com.example.ticket_service.domain.Seat;
import com.example.ticket_service.infrastructure.persistence.SpringDataSeatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SpringDataSeatRepository seatRepo;

    public SeatController(SpringDataSeatRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam UUID eventId, @RequestBody List<String> seatNumbers) {
        seatNumbers.forEach(sn -> {
            boolean exists = seatRepo.findByEventIdAndSeatNumber(eventId, sn).isPresent();
            if (!exists) {
                Seat s = new Seat();
                s.setEventId(eventId);
                s.setSeatNumber(sn);
                s.setStatus(Seat.SeatStatus.AVAILABLE);
                seatRepo.save(s);
            }
        });

        return ResponseEntity.created(URI.create("/api/seats")).build();
    }
}