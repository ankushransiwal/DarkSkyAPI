package com.example.ankush.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankush.darkskyclient.events.EventError;
import com.example.ankush.darkskyclient.events.WeatherEvent;
import com.example.ankush.darkskyclient.models.Currently;
import com.example.ankush.darkskyclient.models.Weather;
import com.example.ankush.darkskyclient.services.WeatherService;
import com.example.ankush.darkskyclient.services.WeatherServiceProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

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

    @BindView(R.id.summaryTextView)
    TextView summaryTextView;

    @BindView(R.id.iconImageView)
    ImageView iconImageView;

    double temp;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //temTextView = findViewById(R.id.temTextView);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        requestCurrentWeather(12.0760,52.8777);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent weatherEvent) {
        Currently currently = weatherEvent.getWeather().getCurrently();
        temp = currently.getTemperature();
        temp = (temp - 32) * 5/9;

        temTextView.setText(String.valueOf(Math.round(temp)));

        summaryTextView.setText(currently.getSummary());

        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("clear-day", R.drawable.clear_day);
        iconMap.put("clear-night", R.drawable.clear_night);
        iconMap.put("cloudy", R.drawable.cloudy);
        iconMap.put("partly-cloudy-day", R.drawable.partly_cloudy_day);
        iconMap.put("partly-cloudy-night", R.drawable.partly_cloudy_night);
        iconMap.put("rain", R.drawable.rain);
        iconMap.put("snow", R.drawable.snow);
        iconMap.put("thunderstorm", R.drawable.thunderstorm);
        iconMap.put("sleet", R.drawable.sleet);
        iconMap.put("wind", R.drawable.wind);
        iconMap.put("fog", R.drawable.fog);



        iconImageView.setImageResource(iconMap.get(currently.getIcon()));
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(EventError eventError){
        Toast.makeText(this, eventError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void requestCurrentWeather(double lat, double lng) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather(lat,lng);
    }
}
