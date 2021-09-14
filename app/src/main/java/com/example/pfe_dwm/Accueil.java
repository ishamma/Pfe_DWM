 package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;

import java.util.HashMap;

 public class Accueil extends AppCompatActivity {
     private DrawerLayout mDrawerLayout;
     //private NavigationView mDrawerLayout;
     notif_icon notif = new notif_icon();

     private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_accueil);
        if(Session.id==0){
              startActivity(new Intent(this,login.class));
        }
        Button btn = findViewById(R.id.rserve_acceuil);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),patient_rdv.class);
                startActivity(intent);
            }
        });
        /******notif icon*************/
        notif.notif_nbr(this);
        ImageView notif_cards=findViewById(R.id.notif_icon);
        notif_cards.bringToFront();
        notif_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notif.notif_list_btn(Accueil.this);
            }
        });
       //4 updating text view in another layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.nom_menu);
        username.setText(Session.user_name);
        Navigation();


    }

     /*public  void notif_list_btn(){

         String sql = "UPDATE notification n,rendez_vous r, patient p SET n.etat_notif=1 WHERE n.id_rdv=r.id_rdv and r.id_patient=p.id_patient and p.id_account='"+Session.id+"'";
         HashMap<String, String> params = new HashMap<String, String>();
         params.put("sql", sql);
            PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
             @Override
             public void processFinish(JSONArray output) {
                 if (output != null) {
                     startActivity(new Intent(getApplicationContext(),notif_patient_affichage.class));
                     Log.i("yyy","jjj");
                 }
             }
         });
         request.execute();
         startActivity(new Intent(getApplicationContext(),notif_patient_affichage.class));
         Log.i("yy22y","jjj");


     }*/



//     @Override
//     public boolean onCreateOptionsMenu(Menu menu) {
//         getMenuInflater().inflate(R.menu.menu_patient,menu);
//         return true;
//     }

     public  void Navigation(){

         //////////////////////Button pour ouvrir naviagtion ////////////////////
         toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                 this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
         mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
         actionBarDrawerToggle.syncState();
         getSupportActionBar().setDisplayShowTitleEnabled(false);
         //navigationView.setNavigationItemSelectedListener(this);
         //////////////////////////////////////////
         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
         Menu menu = navigationView.getMenu();

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()) {
                     case R.id.acceuil_patient: {
                         startActivity(new Intent(getApplicationContext(),Accueil.class));
                         break;
                     }

                     case R.id.mesrdv: {
                         startActivity(new Intent(getApplicationContext(),MesRendezVous.class));
                         break;
                     }
                     case R.id.calendrier: {
                         startActivity(new Intent(getApplicationContext(),patient_rdv.class));
                         break;
                     }
                     case R.id.profile: {
                         startActivity(new Intent(getApplicationContext(),Profile.class));
                         break;
                     }
                     case R.id.log_out: {
                         Session.id=0;
                         startActivity(new Intent(getApplicationContext(),login.class));
                         break;
                     }
                 }
                 //close navigation drawer
                 mDrawerLayout.closeDrawer(GravityCompat.START);
                 return true;
             }
         });

     }



 }