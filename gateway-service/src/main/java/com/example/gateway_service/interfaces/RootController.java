package com.example.gateway_service.interfaces;

import com.example.gateway_service.interfaces.rest.dto.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public ResponseEntity<HealthResponse> rootHealth() {
        return ResponseEntity.ok(new HealthResponse("gateway-service", "OK"));
    }

    @GetMapping("/s/api/gateway/_health")
    public ResponseEntity<HealthResponse> scopedHealth() {
        return ResponseEntity.ok(new HealthResponse("gateway-service", "OK"));
    }
}
