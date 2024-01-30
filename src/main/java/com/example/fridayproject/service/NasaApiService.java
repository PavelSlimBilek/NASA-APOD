package com.example.fridayproject.service;

import com.example.fridayproject.dto.NasaPictureModelDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import java.util.List;

public interface NasaApiService {

    @GET("/planetary/apod")
    Call<NasaPictureModelDto> getNasaPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Call<List<NasaPictureModelDto>> getNasaPictures(@Query("api_key") String apiKey, @Query("count") int count);

    @GET("/planetary/apod")
    Call<NasaPictureModelDto> getNasaPictureByDate(@Query("api_key") String apiKey, @Query("date") String date);


}

