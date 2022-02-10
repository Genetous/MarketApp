package com.mubo.marketapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostJson{

    public static String SendHttpPost(String URL, JSONArray array) {
        String don="";
        try {
            java.net.URL connectURL = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(array.toString());
            os.flush();
            String conco = String.valueOf(conn.getResponseCode());
            InputStream is = conn.getInputStream();
            don=convertStreamToString(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return don;
    }
    public static String SendHttpPost(String URL, JSONObject obje,String token) {
        String don="";
        try {
            URL connectURL = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            if(!token.isEmpty())
                conn.setRequestProperty("Authorization","Bearer "+token);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            if(obje!=null) {
                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                os.write(obje.toString());
                os.flush();
            }
            String conco = String.valueOf(conn.getResponseCode());
            InputStream is = conn.getInputStream();
            don=convertStreamToString(is);

        } catch (Exception e) {
            Log.i("PostHata",e.toString());
            e.printStackTrace();
        }
        return don;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}