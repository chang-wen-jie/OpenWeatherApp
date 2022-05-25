package com.example.keuzedeelweek5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle extras = getIntent().getExtras();

        String countryName = extras.getString("COUNTRY_NAME");
        String countryTemperature = extras.getString("COUNTRY_TEMPERATURE") + "°";
        String countryMiscellaneous =
                extras.getString("COUNTRY_WEATHER_DESCRIPTION") + "\nVoelt als " +
                extras.getString("COUNTRY_TEMPERATURE_FEEL") + "°\nWind: " +
                extras.getString("COUNTRY_WIND") + " km/u\nLuchtvochtigheid: " +
                extras.getString("COUNTRY_HUMIDITY") + "%";
        String countryTemperatureRage =
                extras.getString("COUNTRY_TEMPERATURE_MINIMUM") + "° / " +
                extras.getString("COUNTRY_TEMPERATURE_MAXIMUM") + "°";

        TextView countryNameText = findViewById(R.id.country_name);
        TextView countryTemperatureText = findViewById(R.id.country_temperature);
        TextView countryMiscellaneousText = findViewById(R.id.country_miscellaneous);
        TextView countryTemperatureRangeText = findViewById(R.id.country_temperature_range);

        countryNameText.setText(countryName);
        countryTemperatureText.setText(countryTemperature);
        countryMiscellaneousText.setText(countryMiscellaneous);
        countryTemperatureRangeText.setText(countryTemperatureRage);

        WebView globalEarthquakeData;
        globalEarthquakeData = (WebView) findViewById(R.id.global_earthquake_data);
        globalEarthquakeData.getSettings().setJavaScriptEnabled(true);
        globalEarthquakeData.loadUrl(
                "https://earthquake.usgs.gov/earthquakes/map/?extent=55.20709,-138.97224&extent=55.54961,-137.9615"
        );

        TextView localEarthquakeData = findViewById(R.id.local_earthquake_data);
        localEarthquakeData.setMovementMethod(LinkMovementMethod.getInstance());
        localEarthquakeData.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(
                    "https://json-feed-viewer.herokuapp.com/feed?url=https%3A%2F%2Fcdn.knmi.nl%2Fknmi%2Fmap%2Fpage%2Fseismologie%2FGQuake_KNMI_RSS.xml"
            ));
            startActivity(browserIntent);
        });
    }
}
