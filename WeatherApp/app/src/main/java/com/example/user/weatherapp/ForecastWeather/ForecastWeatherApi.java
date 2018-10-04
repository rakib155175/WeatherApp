package com.example.user.weatherapp.ForecastWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ForecastWeatherApi {
    @GET
    Call<ForecastWeatherResponse> getForecast(@Url String endUrl);
}
