package com.example.keuzedeelweek5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "e53301e27efa0b66d05045d91b2742d3";
    EditText countryInput;
    DecimalFormat countryWeather = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryInput = findViewById(R.id.country_input);
    }

    public void getWeatherData(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle extras = new Bundle();
        String url;
        String countryQuery = countryInput.getText().toString().trim();

        if (countryQuery.equals("")) {
            Snackbar emptyWeatherData =
                    Snackbar.make(findViewById(
                            R.id.get_weather_data
                            ), "Voer a.u.b een land of gemeente in", BaseTransientBottomBar.LENGTH_SHORT
                    );
            emptyWeatherData.show();
        } else {
            url = apiUrl + "?q=" + countryQuery + "&appid=" + apiKey + "&lang=nl";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                String countryName;
                String countryTemperature;
                String countryTemperatureFeel;
                String countryTemperatureMinimum;
                String countryTemperatureMaximum;
                String countryHumidity;
                String countryWeatherDescription;
                String countryWind;

                try {
                    JSONObject apiResponse = new JSONObject(response);
                    JSONObject apiMainObject = apiResponse.getJSONObject("main");
                    JSONObject apiWindObject = apiResponse.getJSONObject("wind");
                    JSONArray apiWeatherArray = apiResponse.getJSONArray("weather");
                    JSONObject apiWeatherObject = apiWeatherArray.getJSONObject(0);

                    String name = apiResponse.getString("name");
                    String description = apiWeatherObject.getString("description");
                    double temp = apiMainObject.getDouble("temp") - 273.15;
                    double feelsLike = apiMainObject.getDouble("feels_like") - 273.15;
                    double tempMin = apiMainObject.getDouble("temp_min") - 273.15;
                    double tempMax = apiMainObject.getDouble("temp_max") - 273.15;
                    double speed = apiWindObject.getInt("speed") * 3.6;
                    int humidity = apiMainObject.getInt("humidity");

                    countryName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    countryTemperature = countryWeather.format(temp);
                    countryTemperatureMinimum = countryWeather.format(feelsLike);
                    countryTemperatureMaximum = countryWeather.format(tempMin);
                    countryTemperatureFeel = countryWeather.format(tempMax);
                    countryHumidity = String.valueOf(humidity);
                    countryWeatherDescription = description.substring(0, 1).toUpperCase() + description.substring(1);
                    countryWind = String.valueOf(speed);

                    extras.putString("COUNTRY_NAME", countryName);
                    extras.putString("COUNTRY_WEATHER_DESCRIPTION", countryWeatherDescription);
                    extras.putString("COUNTRY_TEMPERATURE", countryTemperature);
                    extras.putString("COUNTRY_TEMPERATURE_FEEL", countryTemperatureFeel);
                    extras.putString("COUNTRY_TEMPERATURE_MINIMUM", countryTemperatureMinimum);
                    extras.putString("COUNTRY_TEMPERATURE_MAXIMUM", countryTemperatureMaximum);
                    extras.putString("COUNTRY_WIND", countryWind);
                    extras.putString("COUNTRY_HUMIDITY", countryHumidity);

                    intent.putExtras(extras);
                    startActivity(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
