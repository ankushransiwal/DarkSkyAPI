package com.example.ankush.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ankush.darkskyclient.models.Currently;
import com.example.ankush.darkskyclient.models.Weather;
import com.example.ankush.darkskyclient.services.WeatherService;
import com.example.ankush.darkskyclient.services.WeatherServiceProvider;

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

        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather();
    }
}
