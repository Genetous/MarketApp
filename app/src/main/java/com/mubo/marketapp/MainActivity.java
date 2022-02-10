package com.mubo.marketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Interfaces.apiReturn {

    EditText username,pass;
    Button login;
    Context cx;
    Activity act;
    ApiClass api;
    Interfaces.apiReturn ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cx=this;
        act=this;
        ar=this;
        api=new ApiClass();
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(login_cl);
    }

    View.OnClickListener login_cl=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String u,p;
            u=username.getText().toString().trim();
            p=pass.getText().toString().trim();
            if(u.isEmpty())
                Toast.makeText(cx, "Lütfen Kullanıcı Adınızı Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else if(p.isEmpty())
                Toast.makeText(cx, "Lütfen Şifrenizi Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else
                login(u,p);
        }
    };
    void login(String u, String p){
        JSONObject j=new JSONObject();
        try {
            j.put("user_username",u);
            j.put("user_pass",p);
            String[] rf={"user_userpass"};
            j.put("remove_fields",rf);
            //api.login(j,ar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnData(JSONObject j) {

    }

    @Override
    public void returnDataSecond(JSONObject j) {

    }

    @Override
    public void searchReturn(JSONObject j) {

    }
}