package com.example.ankush.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ankush.darkskyclient.models.Currently;
import com.example.ankush.darkskyclient.models.Weather;
import com.example.ankush.darkskyclient.services.WeatherService;
import com.example.ankush.darkskyclient.services.WeatherServiceProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.temTextView)
    TextView temTextView;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //temTextView = findViewById(R.id.temTextView);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        requestCurrentWeather(32.0760,72.8777);
    }

    private void requestCurrentWeather(double lat, double lng) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();

        Callback callback = new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Currently currently = response.body().getCurrently();
                Log.e(TAG,"Temparature : " + currently.getTemperature());
                temTextView.setText(String.valueOf(currently.getTemperature()));
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG,"onFailure : unable to get Weather Data");

            }
        };

        weatherServiceProvider.getWeather(lat,lng, callback);
    }
}
