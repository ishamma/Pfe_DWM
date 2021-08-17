package com.example.pfe_dwm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    TextView username,password,register;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getActionBar().setSubtitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn1= findViewById(R.id.btn_log);
        register = findViewById(R.id.textView6);

          username = findViewById(R.id.txtemail);
          password = findViewById(R.id.txtpassword);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Accueil.class);
//                startActivity(intent);


                String sql = "SELECT  * FROM `account` where email = '" + username.getText().toString() + "' and password = '" + password.getText().toString()+"'";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        //for(int i =0 ; i<output.length();i++){
                        //try {
                        // Log.v("name",output.getJSONObject(i).getString("NOM"));
                        // } catch (JSONException e) {
                        // e.printStackTrace();
                        // }
                        //}
                        if(output!=null){
                            try {
                                if(output.getJSONObject(0).getInt("user_id")!= 0){
                                    String nom,email,date_creation;
                                    int id,id_role;
                                    JSONObject account = output.getJSONObject(0);

                                    id = account.getInt("user_id");
                                    nom = account.getString("user_name");
                                    email = account.getString("email");
                                    date_creation = account.getString("date_creation");
                                    id_role = account.getInt("id_role");

                                    Session.id = id; Session.user_name= nom;
                                    Session.email = email; Session.dateCreation = date_creation;Session.id_role = id_role;
//                                    if (id_role==3){}
//                                    else if (id_role==3){}
//                                    else {}
                                    Intent i = new Intent(login.this, Accueil.class);
                                    startActivity(i);}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                request.execute();



            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });

        Button btn = findViewById(R.id.chart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),pieChart.class));
            }
        });
    }


    public void login(){
        Intent intent=new Intent(getApplicationContext(),Accueil.class);
        startActivity(intent);
    }
}