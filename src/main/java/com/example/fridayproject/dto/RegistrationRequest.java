package com.example.fridayproject.dto;

public record RegistrationRequest (
        String firstName,
        String lastName,
        String email,
        String password
) {
}