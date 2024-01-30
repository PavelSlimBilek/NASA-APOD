package com.example.fridayproject.repository;

import com.example.fridayproject.model.Uzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Uzer, Long> {
    Uzer findFirstByEmail(String email);
}