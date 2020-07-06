package com.example.tempproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mymap;
    private View v;

    private String addr = "";
    private FusedLocationSource locationSource;

    Marker marker1 = new Marker();
    Marker marker2 = new Marker();
    Marker marker3 = new Marker();
    Marker marker4 = new Marker();

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;
    Button button13;
    Button button14;
    boolean check1 = true;
    boolean check2 = true;
    boolean check3 = true;
    boolean check4 = true;
    boolean check5 = true;
    boolean check6 = true;
    boolean check7 = true;
    boolean check8 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로화면 고정
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button13 = (Button) findViewById(R.id.button13);
        button14 = (Button) findViewById(R.id.button14);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        invisiblesetup1();
        invisiblesetup2();
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.mymap = naverMap;
        LatLng coord1 = new LatLng(35.945378,126.682110);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945278, 126.682167)).animate(CameraAnimation.Easing,3000);
        naverMap.moveCamera(cameraUpdate);

        mymap.setLocationSource(locationSource);
        mymap.setLocationTrackingMode(LocationTrackingMode.Follow);

        marker1.setPosition(new LatLng(35.945278, 126.682167)); // 군산대학교 좌표 마켓
        marker1.setMap(mymap);
        marker2.setPosition(new LatLng(35.981482, 126.684542)); // 전북외국어고등학교 좌표 마켓
        marker2.setMap(mymap);
        marker3.setPosition(new LatLng(35.967604, 126.736843)); // 군산시청 좌표 마켓
        marker3.setMap(mymap);
        marker4.setPosition(coord1);
        marker4.setMap(mymap);

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter( this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });

        InfoWindow infowindow1 = new InfoWindow();
        infowindow1.setAdapter(new InfoWindow.DefaultTextAdapter(this){
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infowindow1){
                return addr;
            }
        });

        marker1.setTag("군산대학교");
        marker2.setTag("전북외국어고등학교");
        marker3.setTag("군산시청");
// 지도를 클릭하면 정보 창을 닫음
        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
            infowindow1.close();
            marker4.setPosition(point);
            marker4.setMap(mymap);

            try {
                addr = new GetAddress().execute(point).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("STATE", addr);
            infowindow1.open(marker4);
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
        LayerGroup_Off();
    }

    public void ClickButton2(View v){
        mymap.setMapType(NaverMap.MapType.Satellite);
        Toast.makeText(this, "Satellite Mode", Toast.LENGTH_SHORT).show();
        LayerGroup_Off();
    }

    public void ClickButton3(View v){
        mymap.setMapType(NaverMap.MapType.Navi);
        Toast.makeText(this, "Navi Mode", Toast.LENGTH_SHORT).show();
        LayerGroup_Off();
    }

    public void ClickButton4(View v){
        mymap.setMapType(NaverMap.MapType.Basic);
        Toast.makeText(this, "Basic Mode", Toast.LENGTH_SHORT).show();
        LayerGroup_Off();
}

    public void ClickButton5(View v){
        mymap.setMapType(NaverMap.MapType.Terrain);
        Toast.makeText(this, "Terrain Mode", Toast.LENGTH_SHORT).show();
        LayerGroup_Off();
    }

    public void ClickButton6(View v){
        mymap.setMapType(NaverMap.MapType.None);
        Toast.makeText(this, "None Mode", Toast.LENGTH_SHORT).show();
        LayerGroup_Off();
    }
    public void ClickButton7(View v){

       if(check1 == true) {
           visiblesetup1();
           check1 = false;
       } else {
           invisiblesetup1();
           check1 = true;
       }

    }

    public void ClickButton8(View v){

        if(check2 == true) {
            visiblesetup2();
            check2 = false;
        } else {
            invisiblesetup2();
            check2 = true;
        }

    }

    private void visiblesetup1()
    {
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);
        button6.setVisibility(View.VISIBLE);
    }

    private void invisiblesetup1()
    {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        button6.setVisibility(View.INVISIBLE);
    }

    private void visiblesetup2()
    {
        button9.setVisibility(View.VISIBLE);
        button10.setVisibility(View.VISIBLE);
        button11.setVisibility(View.VISIBLE);
        button12.setVisibility(View.VISIBLE);
        button13.setVisibility(View.VISIBLE);
        button14.setVisibility(View.VISIBLE);
    }

    private void invisiblesetup2()
    {
        button9.setVisibility(View.INVISIBLE);
        button10.setVisibility(View.INVISIBLE);
        button11.setVisibility(View.INVISIBLE);
        button12.setVisibility(View.INVISIBLE);
        button13.setVisibility(View.INVISIBLE);
        button14.setVisibility(View.INVISIBLE);
    }

    public void ClickButton9(View v){
        Toast.makeText(this, "Building", Toast.LENGTH_SHORT).show();
        if (check3 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
            check3 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false);
            check3 = true;
        }
    }

    public void ClickButton10(View v){
        Toast.makeText(this, "Traffic", Toast.LENGTH_SHORT).show();
        if (check4 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true);
            check4 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, false);
            check4 = true;
        }
    }

    public void ClickButton11(View v){
        Toast.makeText(this, "Transit", Toast.LENGTH_SHORT).show();
        if (check5 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
            check5 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false);
            check5 = true;
        }
    }

    public void ClickButton12(View v){
        Toast.makeText(this, "Bicycle", Toast.LENGTH_SHORT).show();
        if (check6 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, true);
            check6 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, false);
            check6 = true;
        }
    }

    public void ClickButton13(View v){
        Toast.makeText(this, "Mountain", Toast.LENGTH_SHORT).show();
        if (check7 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true);
            check7 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, false);
            check7 = true;
        }
    }

    public void ClickButton14(View v){
        Toast.makeText(this, "Cadastral", Toast.LENGTH_SHORT).show();
        if (check8 == true) {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, true);
            check8 = false;
        } else {
            mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, false);
            check8 = true;
        }
    }

    public void LayerGroup_Off() {

        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false);
        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, false);
        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false);
        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, false);
        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, false);
        mymap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, false);
    }

}