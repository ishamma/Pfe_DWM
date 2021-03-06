package com.example.pfe_dwm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    TextView email,password,register;
    String result;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getActionBar().setSubtitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn1 = findViewById(R.id.btn_log);

        register = findViewById(R.id.textView6);

        email = findViewById(R.id.txtemail);
        password = findViewById(R.id.txtpassword);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Accueil.class);
//                startActivity(intent);


                String sql = "SELECT  * FROM `account` where email = '" + email.getText().toString() + "' and password = '" + password.getText().toString() + "'";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                         if (output != null) {
                            try {
                                if (output.getJSONObject(0).getInt("user_id") != 0) {
                                    String nom, email, date_creation,password;
                                    int id, id_role;
                                    JSONObject account = output.getJSONObject(0);

                                    id = account.getInt("user_id");
                                    Log.i("id account", String.valueOf(id));
                                    nom = account.getString("user_name");
                                    email = account.getString("email");
                                    date_creation = account.getString("date_creation");
                                    id_role = account.getInt("id_role");
                                    password=account.getString("password");

                                    Session.id = id;
                                    Session.user_name = nom;
                                    Session.email = email;
                                    Session.dateCreation = date_creation;
                                    Session.id_role = id_role;
                                    Session.password=password;
                                    if (id_role == 3) {
                                        Intent i = new Intent(login.this, Accueil.class);
                                        startActivity(i);
                                    } else if (id_role == 2) {
                                        Intent i = new Intent(login.this, secretaire.class);
                                        startActivity(i);
                                    } else if (id_role == 1) {
                                        Intent i = new Intent(login.this, medecin.class);
                                        startActivity(i);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                                    builder.setMessage("Email ou mot de pass est incorrecte ")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    startActivity(new Intent(login.this,login.class));
                                                }
                                            });
                                    AlertDialog alert = builder.create();

                                    alert.show();

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
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });

    }
    public void login(){
        Intent intent=new Intent(getApplicationContext(),Accueil.class);
        startActivity(intent);
    }
}