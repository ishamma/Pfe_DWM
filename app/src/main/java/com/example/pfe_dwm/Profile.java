package com.example.pfe_dwm;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

public class Profile extends AppCompatActivity {

    EditText nom , prenom , cin , date,cnss,tele , adress,email,password;
   private DrawerLayout mDrawerLayout;
   private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Navigation();



    }

    public  void Navigation(){
        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbarpr);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutpr);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.profile,R.string.profile);
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