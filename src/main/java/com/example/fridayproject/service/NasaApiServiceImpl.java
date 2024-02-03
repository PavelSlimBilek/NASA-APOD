package com.example.fridayproject.service;

import com.example.fridayproject.dto.NasaPictureModelDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NasaApiServiceImpl {

    @Value("${nasa.api.key}")
    private String apiKey;
    private final Retrofit retrofit;

    public NasaPictureModelDto getNasaMainPicture() {
        NasaApiService nasaApiService = retrofit.create(NasaApiService.class);
        Call<NasaPictureModelDto> call = nasaApiService.getNasaPictureOfTheDay(apiKey);
        return getNasaPictureModelDto(call);
    }

    public List<NasaPictureModelDto> getNasaRandomPictures() {
        NasaApiService nasaApiService = retrofit.create(NasaApiService.class);
        Call<List<NasaPictureModelDto>> call = nasaApiService.getNasaPictures(apiKey, 10);
        try {
            Response<List<NasaPictureModelDto>> responses = call.execute();
            if (responses.isSuccessful()) {
                return responses.body();
            } else {
                System.err.println("Error fetching NASA picture. Response code: " + responses.code());
            }
        } catch (IOException e) {
            System.err.println("Error fetching NASA picture: " + e.getMessage());
        }
        return null;
    }

    public NasaPictureModelDto getNasaImageByDate(String date) {
        NasaApiService nasaApiService = retrofit.create(NasaApiService.class);
        Call<NasaPictureModelDto> call = nasaApiService.getNasaPictureByDate(apiKey, date);
        return getNasaPictureModelDto(call);
    }

    @Nullable
    private NasaPictureModelDto getNasaPictureModelDto(Call<NasaPictureModelDto> call) {
        try {
            Response<NasaPictureModelDto> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Error fetching NASA picture. Response code: " + response.code());
            }
        } catch (IOException e) {
            System.err.println("Error fetching NASA picture: " + e.getMessage());
        }
        return null;
    }
}