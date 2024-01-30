package com.example.fridayproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NasaPictureModelDto {

    private Long id;
    private String url;
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
}
