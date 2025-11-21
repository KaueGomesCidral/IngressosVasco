package com.example.ticket_service.interfaces;

import com.example.ticket_service.interfaces.rest.dto.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("s/api/ticket/_health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(new HealthResponse("ticket-service", "OK"));
    }
}
