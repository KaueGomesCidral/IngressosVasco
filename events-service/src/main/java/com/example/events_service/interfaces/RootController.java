package com.example.events_service.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class RootController {
    @GetMapping("s/api/event/_health")
    public Map<String, String> health() {
        return Map.of("service","events-service","status","OK");
    }
}
