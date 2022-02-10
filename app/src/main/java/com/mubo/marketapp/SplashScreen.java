package com.mubo.marketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity implements Interfaces.apiReturn{
    ApiClass api;
    Interfaces.apiReturn ar;
    Ayarlar ayarlar;
    String token="";
    Context cx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ayarlar=new Ayarlar(this);
        api=new ApiClass();
        ar=this;
        cx=this;
        //ayarlar.remove_pref("token");
       /* token=ayarlar.get_pref_string("token").trim();
        if(ayarlar.get_pref_string("token").trim().isEmpty())
            create_guest_client();
        else
            verify(token);*/
        Intent i=new Intent(cx,Products.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    void verify(String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.verify(null, ar,token);
            }
        }).start();
    }
    void create_guest_client(){
        JSONObject j=new JSONObject();

        try {
            j.put("application_id",ApiClass.applicationId);
            j.put("organization_id",ApiClass.organizationId);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    api.create_client(j,ar);
                }
            }).start();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void auth(JSONObject j) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.auth(j, ar);
            }
        }).start();
    }
    @Override
    public void returnData(JSONObject j) {
        if(j!=null && j.has("client_id")){
            ayarlar.set_pref_string("client_data",j.toString());
            auth(j);
        }
    }

    @Override
    public void returnDataSecond(JSONObject j) {
        if(j!=null && j.has("token")){
            try {
                ayarlar.set_pref_string("token",j.getString("token"));
                Intent i = new Intent(cx, Products.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{}
    }

    @Override
    public void searchReturn(JSONObject j) {
        if(j!=null){
            try {
                if(j.has("applicationId")) {
                    Intent i = new Intent(cx, Products.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    String cd=ayarlar.get_pref_string("client_data").trim();
                    if(cd.isEmpty())
                        create_guest_client();
                    else
                        auth(new JSONObject(cd));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}