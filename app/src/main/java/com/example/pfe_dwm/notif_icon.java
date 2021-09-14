package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class notif_icon extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_icon);

     /*   ImageView notif_cards=findViewById(R.id.notif_icon);
        notif_cards.bringToFront();
        notif_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "UPDATE notification n,rendez_vous r, patient p SET n.etat_notif=1 WHERE n.id_rdv=r.id_rdv and r.id_patient=p.id_patient and p.id_account='"+Session.id+"'";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            startActivity(new Intent(getApplicationContext(),notif_patient_affichage.class));
                        }
                    }
                });
                request.execute();
            }
        });*/

    }
    public static void notif_nbr(Activity test){
        TextView txt ;
        txt=test.findViewById(R.id.notif_txt);
        String sql = "SELECT COUNT(n.id_notif) as notif FROM notification n,rendez_vous r, patient p where n.etat_notif=0 and n.id_rdv=r.id_rdv and r.id_patient=p.id_patient and p.id_account='"+Session.id+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                if (output != null) {
                    try {

                        JSONObject notif = output.getJSONObject(0);
                        txt.setText(notif.getString("notif"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        request.execute();

    }

   public static void notif_list_btn(Activity test){

                 String sql = "UPDATE notification n,rendez_vous r, patient p SET n.etat_notif=1 WHERE n.id_rdv=r.id_rdv and r.id_patient=p.id_patient and p.id_account='"+Session.id+"'";
         HashMap<String, String> params = new HashMap<String, String>();
         params.put("sql", sql);
            PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
             @Override
             public void processFinish(JSONArray output) {
                 if (output != null) {
                     test.startActivity(new Intent(test.getApplicationContext(),notif_patient_affichage.class));
                     Log.i("yyy","jjj");
                 }
             }
         });
         request.execute();
         test.startActivity(new Intent(test.getApplicationContext(),notif_patient_affichage.class));
         Log.i("yy22y","jjj");
            }

}