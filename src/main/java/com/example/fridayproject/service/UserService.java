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
        user.getFavorites().add(picture);
        picture.setFan(user);
        pictureRepo.save(picture);
        userRepo.save(user);
    }

    public void deleteFavorite(FavDto dto) {
        Uzer user = userRepo.findFirstByEmail(dto.username());
        Optional<NasaPicture> picture = pictureRepo.findById(dto.date());
        if (picture.isPresent()) {
            user.getFavorites().remove(picture.get());
            pictureRepo.delete(picture.get());
            userRepo.save(user);
        }
    }

    public Map<String, NasaPictureSimpleDto> getFavorites(String loggedUser) {
        return userRepo.findFirstByEmail(loggedUser)
                .getFavorites()
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