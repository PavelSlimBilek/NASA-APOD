package com.example.fridayproject.dto;

import com.example.fridayproject.model.NasaPicture;

public record NasaPictureSimpleDto(
        String url,
        String hdurl,
        String title,
        String date
) {
}
