package com.example.gateway_service.interfaces.rest.dto;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
