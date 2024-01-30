package com.example.fridayproject.service;

import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.model.Role;
import com.example.fridayproject.model.Uzer;
import com.example.fridayproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationRequest dto) {
        new Uzer();
        Uzer user = Uzer.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
    }
}