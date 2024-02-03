package com.example.fridayproject.model;

import com.example.fridayproject.dto.FavDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NasaPicture {

    @Id
    private String date;

    private String url;
    private String hdurl;
    private String title;

    @ManyToOne
    @JsonBackReference
    private Uzer fan;

    public NasaPicture(FavDto dto) {
        this.url = dto.url();
        this.hdurl = dto.hdurl();
        this.title = dto.title();
        this.date = dto.date();
    }
}