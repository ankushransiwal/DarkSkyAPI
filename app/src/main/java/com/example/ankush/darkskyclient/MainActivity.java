package com.example.ankush.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ankush.darkskyclient.models.Currently;
import com.example.ankush.darkskyclient.models.Weather;
import com.example.ankush.darkskyclient.services.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/d7f22eb84e853202ead966b3aae24820/19.0760,72.8777/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<Weather> weatherData = weatherService.getWeather();
        weatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Currently currently = response.body().getCurrently();
                Log.e(TAG,"Temparature : " + currently.getTemperature());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG,"onFailure : unable to get Weather Data");

            }
        });

    }
}
