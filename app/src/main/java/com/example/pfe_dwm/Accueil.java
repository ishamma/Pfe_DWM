 package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

 public class Accueil extends AppCompatActivity {
     private DrawerLayout mDrawerLayout;
     //private NavigationView mDrawerLayout;
     private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_accueil);




       //4 updating text view in another layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);
        username.setText(Session.user_name);
        Navigation();





    }




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
         //navigationView.setNavigationItemSelectedListener(this);
         //////////////////////////////////////////
         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
         Menu menu = navigationView.getMenu();

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()) {

                     case R.id.mesrdv: {
                         startActivity(new Intent(getApplicationContext(),MesRendezVous.class));
                         break;
                     }
                     case R.id.calendrier: {
                         startActivity(new Intent(getApplicationContext(),ReserverRdv.class));
                         break;
                     }
                     case R.id.profile: {
                         startActivity(new Intent(getApplicationContext(),Profile.class));
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