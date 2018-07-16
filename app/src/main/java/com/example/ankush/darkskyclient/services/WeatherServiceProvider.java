package com.example.ankush.darkskyclient.services;

import android.util.Log;
import android.widget.Toast;

import com.example.ankush.darkskyclient.events.EventError;
import com.example.ankush.darkskyclient.events.WeatherEvent;
import com.example.ankush.darkskyclient.models.Currently;
import com.example.ankush.darkskyclient.models.Weather;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {

    private static final String BASE_URL = "https://api.darksky.net/forecast/d7f22eb84e853202ead966b3aae24820/";
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private Retrofit retrofit;


    Retrofit getRetrofit(){
        if(this.retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;

    }

    public void getWeather(double lat, double lng){
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<Weather> weatherData = weatherService.getWeather(lat, lng);
        weatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Weather weather = response.body();
                if(weather != null){
                    Currently currently = weather.getCurrently();
                    Log.e(TAG,"Temparature : " + currently.getTemperature());
                    EventBus.getDefault().post(new WeatherEvent(weather));
                }else{
                    Log.e(TAG,"No response : Check secret key");
                    EventBus.getDefault().post(new EventError("No weather data available"));
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG,"onFailure : unable to get Weather Data");
                EventBus.getDefault().post(new EventError("Unable to connect to Internet"));

            }
        });
    }

}
