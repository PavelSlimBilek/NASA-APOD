package com.example.fridayproject.dto;

public record RegistrationRequest (
        String email,
        String password
) {
}