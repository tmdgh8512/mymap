package com.example.tempproject;

import android.os.AsyncTask;
import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetAddress extends AsyncTask<LatLng,Void,String> {

    int value;
    LatLng point;

    @Override
    protected void onPreExecute() {
    }


    @Override
    protected String doInBackground(LatLng... latLngs) {

        String strCoord = String.valueOf(latLngs[0].longitude) + "," + String.valueOf(latLngs[0].latitude);
        StringBuilder sb = new StringBuilder();
        StringBuilder result = new StringBuilder();

        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" + strCoord + "&sourcecrs=epsg:4326&output=json&orders=addr"); /* URL */

        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn;

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "d6nvpc15uv");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "fzGdGvib2YvAL8I3KAnS4PGCubwhLNsq8yHIO8o5");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            Log.d("STATE", sb.toString());
            String str = sb.toString();
            JSONObject jsonObject1 = new JSONObject(str);
            JSONArray jsonArray = jsonObject1.getJSONArray("results");
            Log.d("STATE", jsonArray.get(0).toString());
            JSONObject jsonObject2 = new JSONObject(jsonArray.get(0).toString());
            Log.d("STATE", jsonObject2.getString("region"));
            JSONObject jsonObject3 = new JSONObject(jsonObject2.getString("land"));
            jsonObject2 = new JSONObject(jsonObject2.getString("region"));
            Log.d("STATE", jsonObject3.toString());

            for (int i=0; i<5; i++) {
                JSONObject extra = new JSONObject(jsonObject2.getString("area" + i));
                result.append(extra.getString("name"));
                result.append(" ");
            }
            result.append(jsonObject3.getString("number1") + "-" + jsonObject3.getString("number2"));
            Log.d("STATE", jsonObject2.getString("area" + 0));

            Log.d("STATE", result.toString());

            return  result.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected  void onProgressUpdate(Integer ... value) {
    }
    protected  void onPostExecute(String result) {
        Marker mark1 = new Marker();
    }
    protected void onCancelled() {

    }
}