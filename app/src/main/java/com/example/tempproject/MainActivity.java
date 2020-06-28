package com.example.tempproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mymap;
    private View v;
    Marker marker1 = new Marker();
    Marker marker2 = new Marker();
    Marker marker3 = new Marker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로화면 고정
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.mymap = naverMap;

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945278, 126.682167)).animate(CameraAnimation.Easing,3000);
        naverMap.moveCamera(cameraUpdate);


        marker1.setPosition(new LatLng(35.945278, 126.682167)); // 군산대학교 좌표 마켓
        marker1.setMap(mymap);
        marker2.setPosition(new LatLng(35.981482, 126.684542)); // 전북외국어고등학교 좌표 마켓
        marker2.setMap(mymap);
        marker3.setPosition(new LatLng(35.967604, 126.736843)); // 군산시청 좌표 마켓
        marker3.setMap(mymap);

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter( this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });

        marker1.setTag("군산대학교");
        marker2.setTag("전북외국어고등학교");
        marker3.setTag("군산시청");
// 지도를 클릭하면 정보 창을 닫음
        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

// 마커를 클릭하면:
        Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker)overlay;

            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };

        marker1.setOnClickListener(listener);
        marker2.setOnClickListener(listener);
        marker3.setOnClickListener(listener);
    }

    public void ClickButton1(View v){
        mymap.setMapType(NaverMap.MapType.Hybrid);
        Toast.makeText(this, "Hybrid Mode", Toast.LENGTH_SHORT).show();
    }

    public void ClickButton2(View v){
        mymap.setMapType(NaverMap.MapType.Satellite);
        Toast.makeText(this, "Satellite Mode", Toast.LENGTH_SHORT).show();
    }

    public void ClickButton3(View v){
        mymap.setMapType(NaverMap.MapType.Navi);
        Toast.makeText(this, "Navi Mode", Toast.LENGTH_SHORT).show();
    }

    public void ClickButton4(View v){
        mymap.setMapType(NaverMap.MapType.Basic);
        Toast.makeText(this, "Basic Mode", Toast.LENGTH_SHORT).show();
    }

    public void ClickButton5(View v){
        mymap.setMapType(NaverMap.MapType.Terrain);
        Toast.makeText(this, "Terrain Mode", Toast.LENGTH_SHORT).show();
    }

    public void ClickButton6(View v){
        mymap.setMapType(NaverMap.MapType.None);
        Toast.makeText(this, "None Mode", Toast.LENGTH_SHORT).show();
    }
    public void ClickButton7(View v){
        marker1.setMap(null);
        marker2.setMap(null);
        marker3.setMap(null);

    }
}