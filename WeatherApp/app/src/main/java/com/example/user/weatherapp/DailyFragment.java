package com.example.user.weatherapp;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.weatherapp.CurrentWeather.CurrentWeatherApi;
import com.example.user.weatherapp.CurrentWeather.CurrentWeatherResponse;
import com.example.user.weatherapp.CurrentWeather.RetrofitClient;
import com.example.user.weatherapp.CurrentWeather.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyFragment extends Fragment{
    private String Latitude;
    private String Longitude;
    private String endUrl;
    private Context context;
    private List<Weather>weathers=new ArrayList<>();
    private String temp,max,min,state,city,riseString,setString,dateString,iconID;
    private long time,sunrise,sunset;
    TextView temptv,citytv,datetv,maxtv,mintv,sunrisetv,sunsettv,statetv;
    ImageView imageView;
    private String BASE_URL_WEATHER="http://api.openweathermap.org/data/2.5/";
    private String BASE_URL_ICON="https://openweathermap.org/img/w/";
    CurrentWeatherResponse cwr;
    CurrentWeatherApi currentWeatherApi;
    //ProgressBar progressBar;

    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Latitude=MainActivity.Latitude;
        Longitude=MainActivity.Longitude;


        View view=inflater.inflate(R.layout.fragment_daily, container, false);
        temptv=view.findViewById(R.id.tempTV);
        citytv=view.findViewById(R.id.cityTV);
        datetv=view.findViewById(R.id.dateTV);
        maxtv=view.findViewById(R.id.maxTemp);
        mintv=view.findViewById(R.id.minTemp);
        sunrisetv=view.findViewById(R.id.sunriseTV);
        sunsettv=view.findViewById(R.id.sunsetTV);
        statetv=view.findViewById(R.id.stateTV);
        imageView=view.findViewById(R.id.imageview);
        //progressBar=view.findViewById(R.id.Bar);


        getdata();
        return view;

    }
    public void getdata(){
        //Toast.makeText(context, ""+Longitude, Toast.LENGTH_SHORT).show();
        endUrl=String.format("weather?lat=%s&lon=%s&units=metric&appid=d74a96f97b96b7e46b0e80884912cbba",Latitude,Longitude);
        currentWeatherApi=RetrofitClient.getRetrofitClient(BASE_URL_WEATHER).create(CurrentWeatherApi.class);
        currentWeatherApi.GetCurrentWeather(endUrl).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if(response.isSuccessful()){
                    cwr=response.body();
                    if(MainActivity.format==1){
                        temp=String.format("%.0f",cwr.getMain().getTemp())+"\u2103";
                        max=String.format("%.0f",cwr.getMain().getTempMax())+"\u2103";
                        min=String.format("%.0f",cwr.getMain().getTempMin())+"\u2103";
                        }

                    if(MainActivity.format==2){
                        temp=String.format("%.0f",((cwr.getMain().getTemp())*9/5)+32)+"\u2109";
                        max=String.format("%.0f",((cwr.getMain().getTempMax())*9/5)+32)+"\u2109";
                        min=String.format("%.0f",((cwr.getMain().getTempMin())*9/5)+32)+"\u2109";
                    }
//                    else
//                        temp=String.format("%.0f",cwr.getMain().getTemp())+"\u2103";
//                        max=String.format("%.0f",cwr.getMain().getTempMax())+"\u2103";
//                        min=String.format("%.0f",cwr.getMain().getTempMin())+"\u2103";

                    weathers=cwr.getWeather();
                    state=weathers.get(0).getDescription();
                    city=cwr.getName();

                    iconID=weathers.get(0).getIcon();
                    String icon=String.format(BASE_URL_ICON)+iconID+".png";
                    Picasso.get().load(icon).into(imageView);

                    time=cwr.getDt();
                    Date date=new Date(time*1000);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        SimpleDateFormat sdf=new SimpleDateFormat("MMM-dd,yyyy\nhh:mm a");
                        dateString=sdf.format(date);
                    }

                    sunrise=cwr.getSys().getSunrise();
                    Date rise=new Date(sunrise*1000);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                        riseString=sdf.format(rise);
                    }

                    sunset=cwr.getSys().getSunset();
                    Date set=new Date(sunset*1000);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                        setString=sdf.format(set);
                    }

                    setdata();

                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setdata() {
        temptv.setText(temp);
        datetv.setText(dateString);
        statetv.setText(state);
        citytv.setText(city);
        maxtv.setText(max);
        mintv.setText(min);
        sunrisetv.setText(riseString);
        sunsettv.setText(setString);
    }
}
