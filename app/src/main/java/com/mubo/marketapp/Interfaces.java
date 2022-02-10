package com.mubo.marketapp;

import org.json.JSONObject;

public class Interfaces {
    public interface apiReturn{
        void returnData(JSONObject j);
        void returnDataSecond(JSONObject j);
        void searchReturn(JSONObject j);
    }
    public interface checkToken{
        void getToken(String token);
    }
}
