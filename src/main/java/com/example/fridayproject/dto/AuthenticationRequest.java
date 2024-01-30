package com.example.fridayproject.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}