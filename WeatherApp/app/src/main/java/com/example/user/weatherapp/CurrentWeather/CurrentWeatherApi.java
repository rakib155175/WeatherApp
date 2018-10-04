package com.example.user.weatherapp.CurrentWeather;

import com.example.user.weatherapp.ForecastWeather.ForecastWeatherResponse;

import java.net.URL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CurrentWeatherApi {
    @GET
    Call<CurrentWeatherResponse>GetCurrentWeather(@Url String EndUrl);




}
