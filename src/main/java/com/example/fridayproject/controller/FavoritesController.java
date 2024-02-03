package com.example.fridayproject.controller;

import com.example.fridayproject.dto.FavDto;
import com.example.fridayproject.service.AuthenticationService;
import com.example.fridayproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FavoritesController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/favorites")
    public String favorites(Model model) {
        model.addAttribute("favorites", userService.getFavorites(authenticationService.getLoggedUser()));
        return "/favorites";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute FavDto dto,
                                 HttpServletRequest request) {
        userService.addFavorite(dto);
        String referer = request.getHeader("referer");
        return "redirect:" + (referer != null && !referer.isEmpty() ? referer : "/view");
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute FavDto dto,
                         HttpServletRequest request) {
        userService.deleteFavorite(dto);
        String referer = request.getHeader("referer");
        return "redirect:" + (referer != null && !referer.isEmpty() ? referer : "/view");
    }
}
