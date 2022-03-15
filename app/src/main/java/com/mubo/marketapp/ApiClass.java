package com.mubo.marketapp;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiClass {
    public static String applicationId="4f0f0567-a8aa-4780-bf8f-7aa0edcec572";
    public static String organizationId="6199a078bb6bb0850b8c5b16";
    static String ip="http://192.168.0.21";
    public static String main_url=ip+"/api/";
    public static String token_client=ip+"/auths/client";
    public static String token_auth=ip+"/auths/auth";
    public static String token_verify=ip+"/auths/verify";
    public static String token_logout=ip+"/auths/logout";
    public static String create_collection="";
    public static String create_unique_collection=main_url+"add/unique/collection";
    public static String get_collection="get/collection";
    public static String create_relation;
    public static String get_relation=main_url+"select/relation";
    public static String get_relations=main_url+"get/relations";
    public static String search=main_url+"search";
    public void signUp(JSONObject data, Interfaces.apiReturn ar,String token){
        String sonuc=HttpPostJson.SendHttpPost(create_unique_collection,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void login(JSONObject data, Interfaces.apiReturn ar,String token){
        String sonuc=HttpPostJson.SendHttpPost(get_collection,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void getCategoriesWithProducts(JSONObject data, Interfaces.apiReturn ar,String token){
        try {
            data.put("type","getProduct");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sonuc=HttpPostJson.SendHttpPost(get_relations,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void getProducts(JSONObject data, Interfaces.apiReturn ar,String token){
        try {
            data.put("type","getProduct");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sonuc=HttpPostJson.SendHttpPost(get_relations,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnDataSecond(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void searchData(JSONObject data, Interfaces.apiReturn ar,String token){

        String sonuc=HttpPostJson.SendHttpPost(search,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.searchReturn(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void create_client(JSONObject data, Interfaces.apiReturn ar){
        String sonuc=HttpPostJson.SendHttpPost(token_client,data,"");
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnData(j);
            } catch (JSONException e) {
                ar.returnData(null);
            }
        }
    }
    public void auth(JSONObject data, Interfaces.apiReturn ar){
        String sonuc=HttpPostJson.SendHttpPost(token_auth,data,"");
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.returnDataSecond(j);
            } catch (JSONException e) {
                ar.returnDataSecond(null);
            }
        }
    }
    public void verify(JSONObject data, Interfaces.apiReturn ar,String token){
        String sonuc=HttpPostJson.SendHttpPost(token_verify,data,token);
        if(!sonuc.isEmpty()){
            try {
                JSONObject j=new JSONObject(sonuc);
                ar.searchReturn(j);
            } catch (JSONException e) {
                ar.searchReturn(null);
            }
        }
    }
}
