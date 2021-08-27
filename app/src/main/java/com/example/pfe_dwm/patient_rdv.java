package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class patient_rdv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rdv);
        int idN=Session.id;
        String sql = "SELECT nom_patient,date_naiss FROM `account`, `patient` where account.user_id=patient.id_account and account.user_id='"+idN+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);
        TextView name = findViewById(R.id.name_patient);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                if(output!=null){
                    try {
                        if(output.getJSONObject(0).getInt("user_id")!= 0){
                            String nom_patient,date_naiss;
                            JSONObject account = output.getJSONObject(0);

                            nom_patient = account.getString("nom_patient");
                            date_naiss = account.getString("date_naiss");
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });



    }

}
