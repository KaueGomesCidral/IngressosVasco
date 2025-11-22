package com.example.history_service.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    // GET /api/history/users/{userId}
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object[]> getUserHistory(@PathVariable UUID userId) {
        // Retorna lista vazia por enquanto (implemente persistência / listener de eventos depois)
        return ResponseEntity.ok(new Object[] {});
    }
}