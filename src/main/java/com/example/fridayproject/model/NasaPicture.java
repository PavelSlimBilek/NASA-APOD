package com.example.fridayproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NasaPicture {

    @Id
    @GeneratedValue
    private Long id;

    private String url;
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;

    // TODO add favorite-list
    @ManyToMany
    @JsonBackReference
    private List<Uzer> fans;
}