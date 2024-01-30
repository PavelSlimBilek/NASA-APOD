package com.example.fridayproject.controller;

import com.example.fridayproject.dto.AuthenticationResponse;
import com.example.fridayproject.dto.AuthenticationRequest;
import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationDto) {
        return  ResponseEntity.ok(service.register(registrationDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationDto) {
        return  ResponseEntity.ok(service.authenticate(authenticationDto));
    }
}