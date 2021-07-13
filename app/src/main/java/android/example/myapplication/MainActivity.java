package android.example.myapplication;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    Spinner maptype_spinner;
    NaverMap mMap;
    PolygonOverlay mPolygon;
    ArrayList<Marker> mMarkerArrayLIst;
    ArrayList<LatLng> mLatLngList;

    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        initViews();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mMap = naverMap;
        mPolygon = new PolygonOverlay();
        mMarkerArrayLIst = new ArrayList<>();
        mLatLngList = new ArrayList<>();

        naverMap.setOnMapClickListener((point, coord) -> {
//                    Marker marker = new Marker();
//                    marker.setPosition(new LatLng(coord.latitude, coord.longitude));
//                    marker.setMap(naverMap);
//                    mMarkerArrayLIst.add(marker);
//                    mLatLngList.add(coord);
//                    if (mLatLngList.size() > 2) {
//                        mPolygon.setCoords(mLatLngList);
//                        mPolygon.setMap(naverMap);
//                    }
                }


        );


        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(35.945255, 126.682155), 16
        );
        naverMap.setCameraPosition(cameraPosition);


        Marker marker = new Marker();
        marker.setPosition(new LatLng(35.945255, 126.682155));
        marker.setMap(naverMap);
        marker.setCaptionText("군산대학교");
        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(35.96763, 126.73685));
        marker2.setMap(naverMap);
        marker2.setCaptionText("군산시청");
        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(35.96984, 126.61721));
        marker3.setMap(naverMap);
        marker3.setCaptionText("군산항");

        PolygonOverlay polygon = new PolygonOverlay();
        polygon.setCoords(Arrays.asList(
                new LatLng(35.945255, 126.682155),
                new LatLng(35.96763, 126.73685),
                new LatLng(35.96984, 126.61721)
        ));
        polygon.setColor(Color.argb(100, 255, 0, 0));
        polygon.setMap(naverMap);


        maptype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mMap.setMapType(NaverMap.MapType.Basic);
                } else if (position == 1) {
                    mMap.setMapType(NaverMap.MapType.Satellite);
                } else if (position == 2) {
                    mMap.setMapType(NaverMap.MapType.Terrain);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initViews() {
        maptype_spinner = findViewById(R.id.maptype_spinner);
        btnTest = findViewById(R.id.btnTest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.map_type, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_item_click);
        maptype_spinner.setAdapter(adapter);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mMarkerArrayLIst.size(); i++) {
                    mMarkerArrayLIst.get(i).setMap(null);
                }
                mPolygon.setMap(null);
            }
        });
    }


}