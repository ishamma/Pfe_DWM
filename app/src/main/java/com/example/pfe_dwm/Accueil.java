 package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

 public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_accueil);
       //4 updating text view in another layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);
        username.setText(Session.user_name);


//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                FetchData fetchData = new FetchData("http://192.168.43.214:8080/php_scripts/test.php");
//                if (fetchData.startFetch()) {
//                    if (fetchData.onComplete()) {
//                        String result = fetchData.getResult();
//
//                        Log.i("FetchData", result);
//                    }
//                }
//            }a
//        });


    }

//     @Override
//     public boolean onCreateOptionsMenu(Menu menu) {
//         getMenuInflater().inflate(R.menu.menu_patient,menu);
//         return true;
//     }



 }