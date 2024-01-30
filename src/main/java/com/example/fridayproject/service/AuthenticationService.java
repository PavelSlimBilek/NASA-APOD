package com.example.fridayproject.service;

import com.example.fridayproject.config.JwtService;
import com.example.fridayproject.dto.AuthenticationRequest;
import com.example.fridayproject.dto.AuthenticationResponse;
import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.model.Role;
import com.example.fridayproject.model.Uzer;
import com.example.fridayproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest registrationDto) {
        Uzer user = Uzer.builder()
                .email(registrationDto.email())
                .firstName(registrationDto.firstName())
                .lastName(registrationDto.lastName())
                .password(passwordEncoder.encode(registrationDto.password()))
                .role(Role.USER)
                .build();

        repo.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.email(),
                        authenticationDto.password()
                )
        );
        Uzer user = repo.findFirstByEmail(authenticationDto.email());
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}