package com.example.fridayproject.dto;

public record NasaPictureModelDto (
        String url,
        String copyright,
        String date,
        String explanation,
        String hdurl,
        String media_type,
        String service_version,
        String title
) {
}