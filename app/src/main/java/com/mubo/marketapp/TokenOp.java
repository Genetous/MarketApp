package com.mubo.marketapp;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenOp implements Interfaces.apiReturn{
    Context cx;
    Ayarlar ayarlar;
    String token;
    ApiClass api;
    Interfaces.apiReturn ar;
    Interfaces.checkToken ct;
    public TokenOp(Context cx,Interfaces.checkToken ct){
        this.cx=cx;
        this.ar=this;
        this.ct=ct;
        ayarlar=new Ayarlar(cx);
        this.token=ayarlar.get_pref_string("token");
        api=new ApiClass();
    }
    public void verify() {
        if(this.token.trim().isEmpty())
            create_guest_client();
        else
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
                this.token=j.getString("token");
                ayarlar.set_pref_string("token",j.getString("token"));
                ct.getToken(token);
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
                   ct.getToken(token);
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
