package com.example.fridayproject.repository;

import com.example.fridayproject.model.NasaPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TODO - favorites list
@Repository
public interface NasaPictureRepo extends JpaRepository<NasaPicture, Long> {
}