package com.example.keuzedeelweek5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
        globalEarthquakeData.loadUrl("https://earthquake.usgs.gov/earthquakes/map/?extent=55.20709,-138.97224&extent=55.54961,-137.9615");

        WebView localEarthquakeData;
        localEarthquakeData = (WebView) findViewById(R.id.local_earthquake_data);
        localEarthquakeData.getSettings().setJavaScriptEnabled(true);
        localEarthquakeData.loadUrl("https://json-feed-viewer.herokuapp.com/feed?url=https%3A%2F%2Fcdn.knmi.nl%2Fknmi%2Fmap%2Fpage%2Fseismologie%2FGQuake_KNMI_RSS.xml");
    }
}
