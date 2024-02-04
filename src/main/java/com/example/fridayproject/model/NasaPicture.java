package com.example.fridayproject.model;

import com.example.fridayproject.dto.FavDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "nasaPictures")
    private Set<Uzer> uzers = new HashSet<>();

    public NasaPicture(FavDto dto) {
        this.url = dto.url();
        this.hdurl = dto.hdurl();
        this.title = dto.title();
        this.date = dto.date();
    }
}