package com.example.fridayproject.controller;

import com.example.fridayproject.dto.AuthenticationRequest;
import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.service.AuthenticationService;
import com.example.fridayproject.service.NasaApiServiceImpl;
import com.example.fridayproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NasaApiServiceImpl nasaApiServiceImpl;
    private final UserService userService;

    @GetMapping("/view")
    public String showMainPage(Model model,
                               @RequestParam Optional<String> date) {

        model.addAttribute("picture", date.isPresent()
                ? nasaApiServiceImpl.getNasaImageByDate(date.get())
                : nasaApiServiceImpl.getNasaMainPicture());
        model.addAttribute("images", nasaApiServiceImpl.getNasaRandomPictures());
        return "mainPage";
    }

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationRequest requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/oauth2/authorization/github")
    public String githubLogin() {
        return "redirect:/oauth2/authorization/github";
    }

}
