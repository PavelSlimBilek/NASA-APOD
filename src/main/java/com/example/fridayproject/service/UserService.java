package com.example.fridayproject.service;

import com.example.fridayproject.config.JwtService;
import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.model.Role;
import com.example.fridayproject.model.Uzer;
import com.example.fridayproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequest dto) {
        Uzer user = new Uzer(
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                passwordEncoder.encode(dto.getPassword()),
                Role.USER
        );
        userRepo.save(user);
    }

    public long countUsers() {
        return this.userRepo.count();
    }

    public Uzer getUser(Long id) {
        return this.userRepo.findById(id).orElse(null);
    }

    public void saveUser(Uzer user) {
        this.userRepo.save(user);
    }
}