package com.example.user.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.weatherapp.CurrentWeather.RetrofitClient;
import com.example.user.weatherapp.ForecastWeather.ForecastWeatherApi;
import com.example.user.weatherapp.ForecastWeather.ForecastWeatherResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForecastFragment extends Fragment {
    private String Lat;
    private String Lon;
    private String BASE_URL_FORECAST="http://api.openweathermap.org/data/2.5/";
    private String BASE_URL_ICON="https://openweathermap.org/img/w/";
    private String endUrl;
    RecyclerView recyclerView;
    private String TAG=ForecastFragment.class.getSimpleName();
    ForecastWeatherApi fwa;
    RvAdapter adapter;
    java.util.List<com.example.user.weatherapp.ForecastWeather.List> list=new ArrayList<>();
    ForecastWeatherResponse forecastWeatherResponse;
    private Context context;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Lat=MainActivity.Latitude;
        Lon=MainActivity.Longitude;

        View v= inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView=v.findViewById(R.id.rv);

        endUrl=String.format("forecast?lat=%s&lon=%s&units=metric&appid=d74a96f97b96b7e46b0e80884912cbba",Lat,Lon);
        fwa=RetrofitClient.getRetrofitClient(BASE_URL_FORECAST).create(ForecastWeatherApi.class);
        fwa.getForecast(endUrl).enqueue(new Callback<ForecastWeatherResponse>() {
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                if (response.isSuccessful()){
                   list=response.body().getList();
                   adapter=new RvAdapter(list);
                   LinearLayoutManager llm=new LinearLayoutManager(getActivity());
                  recyclerView.setLayoutManager(llm);
                   recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
            }
        });


        return v;
    }

}
