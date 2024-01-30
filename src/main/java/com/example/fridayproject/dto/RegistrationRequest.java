package com.example.fridayproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String nick;
    private String email;
    private String password;
}
