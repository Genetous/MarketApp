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

public class Register extends AppCompatActivity implements Interfaces.apiReturn {
    EditText username,pass,repass,address;
    Button signup;
    Context cx;
    Activity act;
    Interfaces.apiReturn ar;
    ApiClass api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cx=this;
        act=this;
        ar=this;
        api=new ApiClass();
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        repass=findViewById(R.id.repassword);
        address=findViewById(R.id.address);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(signup_cl);
    }

    View.OnClickListener signup_cl=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String u,p,r,a;
            u=username.getText().toString().trim();
            p=pass.getText().toString().trim();
            r=repass.getText().toString().trim();
            a=address.getText().toString().trim();
            if(u.isEmpty())
                Toast.makeText(cx, "Lütfen Kullanıcı Adınızı Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else if(p.isEmpty())
                Toast.makeText(cx, "Lütfen Şifrenizi Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else if(r.isEmpty())
                Toast.makeText(cx, "Lütfen Şifrenizi Tekrar Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else if(a.isEmpty())
                Toast.makeText(cx, "Lütfen Adresinizi Yazmayı Unutmayın", Toast.LENGTH_SHORT).show();
            else if(!p.equals(r))
                Toast.makeText(cx, "Şifreler Eşleşmiyor", Toast.LENGTH_SHORT).show();
            else
                signup(u,p,a);
        }
    };
    void signup(String u,String p,String a){
        JSONObject j=new JSONObject();
        try {
            j.put("collectionName","user");
            JSONObject content=new JSONObject();
            content.put("user_username",u);
            content.put("user_pass",p);
            content.put("user_address",a);
            content.put("uniqueField","user_username");
            j.put("content",content);
            //api.signUp(j,ar);
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