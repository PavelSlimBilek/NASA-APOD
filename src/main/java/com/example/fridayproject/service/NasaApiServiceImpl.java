package com.example.fridayproject.service;
import com.example.fridayproject.dto.NasaPictureModelDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class NasaApiServiceImpl {

    @Value("${nasa.api.key}")
    private String apiKey;

    private final Retrofit retrofit;

    public NasaApiServiceImpl(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public NasaPictureModelDto getNasaMainPicture() {
        NasaApiService nasaApiService = retrofit.create(NasaApiService.class);
        Call<NasaPictureModelDto> call = nasaApiService.getNasaPictureOfTheDay(apiKey);

        try {
            Response<NasaPictureModelDto> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Error fetching NASA picture. Response code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error fetching NASA picture: " + e.getMessage());
            return null;
        }
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
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error fetching NASA picture: " + e.getMessage());
            return null;
        }
    }

    public NasaPictureModelDto getNasaImageByDate(String date) {
        NasaApiService nasaApiService = retrofit.create(NasaApiService.class);
        Call<NasaPictureModelDto> call = nasaApiService.getNasaPictureByDate(apiKey, date);

        try {
            Response<NasaPictureModelDto> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Error fetching NASA picture. Response code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error fetching NASA picture: " + e.getMessage());
            return null;
        }
    }

}