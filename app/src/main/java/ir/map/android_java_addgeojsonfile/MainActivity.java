package ir.map.android_java_addgeojsonfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;

public class MainActivity extends AppCompatActivity {

    MapboxMap map;
    Style mapStyle;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                map = mapboxMap;
                map.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;

                        addSymbolSourceAndLayerToMap();
                    }
                });
            }
        });
    }

    private void addSymbolSourceAndLayerToMap() {
        // Add source to map
        FeatureCollection featureCollection = FeatureCollection.fromJson(loadGeoJsonFile("sample_file.geojson"));
        GeoJsonSource geoJsonSource = new GeoJsonSource("sample_source_id", featureCollection);

        mapStyle.addSource(geoJsonSource);

        // Add image to map
        mapStyle.addImage("sample_image_id", ContextCompat.getDrawable(this, R.drawable.ic_resturant));

        // Add layer to map
        SymbolLayer symbolLayer = new SymbolLayer("sample_layer_id", "sample_source_id");
        symbolLayer.setProperties(
                PropertyFactory.iconImage("sample_image_id"),
                PropertyFactory.iconSize(1.5f),
                PropertyFactory.iconOpacity(.8f),
                PropertyFactory.textColor("#ff5252")
        );
        mapStyle.addLayer(symbolLayer);
    }

    private String loadGeoJsonFile(String fileName) {
        String contents = "";

        try {
            InputStream stream = getAssets().open(fileName);

            int size = stream.available();
            byte[] buffer = new byte[size];

            stream.read(buffer);
            stream.close();

            contents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return contents;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}