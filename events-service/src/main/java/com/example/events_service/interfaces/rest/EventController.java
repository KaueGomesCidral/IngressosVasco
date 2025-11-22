package com.example.events_service.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final Map<UUID, EventResponse> events = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<EventResponse>> list() {
        return ResponseEntity.ok(new ArrayList<>(events.values()));
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventRequest req) {
        UUID id = UUID.randomUUID();
        EventResponse event = new EventResponse(id, req.name(), req.date(), req.venue());
        events.put(id, event);
        return ResponseEntity.ok(event);
    }
    public record EventRequest(String name, LocalDateTime date, String venue) {}
    public record EventResponse(UUID id, String name, LocalDateTime date, String venue) {}
}
