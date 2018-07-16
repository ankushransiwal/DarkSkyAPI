package com.example.ankush.darkskyclient.services;

import com.example.ankush.darkskyclient.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherService {
    @GET(".")
        Call<Weather> getWeather();

}
