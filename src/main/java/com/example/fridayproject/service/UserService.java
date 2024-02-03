package com.example.fridayproject.service;

import com.example.fridayproject.dto.FavDto;
import com.example.fridayproject.dto.NasaPictureSimpleDto;
import com.example.fridayproject.dto.RegistrationRequest;
import com.example.fridayproject.model.NasaPicture;
import com.example.fridayproject.model.Role;
import com.example.fridayproject.model.Uzer;
import com.example.fridayproject.repository.NasaPictureRepo;
import com.example.fridayproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final NasaPictureRepo pictureRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationRequest dto) {
        new Uzer();
        Uzer user = Uzer.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
    }

    public void addFavorite(FavDto dto) {
        Uzer user = userRepo.findFirstByEmail(dto.username());
        if (user == null) {
            user = new Uzer(dto.username());
            userRepo.save(user);
        }
        NasaPicture picture = new NasaPicture(dto);
        user.getNasaPictures().add(picture);
        picture.getUzers().add(user);
        pictureRepo.save(picture);
        userRepo.save(user);
    }

    public void deleteFavorite(FavDto dto) {
        Uzer user = userRepo.findFirstByEmail(dto.username());
        Optional<NasaPicture> picture = pictureRepo.findById(dto.date());
        if (picture.isPresent()) {
            user.getNasaPictures().remove(picture.get());
            userRepo.save(user);
        }
    }

    public Map<String, NasaPictureSimpleDto> getFavorites(String loggedUser) {
        Uzer user = userRepo.findFirstByEmail(loggedUser);
        if (user == null) {
            return new HashMap<>();
        }
        return userRepo.findFirstByEmail(loggedUser)
                .getNasaPictures()
                .stream()
                .collect(Collectors.toMap(
                        NasaPicture::getDate,
                        p -> new NasaPictureSimpleDto(
                                p.getUrl(),
                                p.getHdurl(),
                                p.getTitle(),
                                p.getDate()),
                        (existing, replacement) -> existing));

    }
}