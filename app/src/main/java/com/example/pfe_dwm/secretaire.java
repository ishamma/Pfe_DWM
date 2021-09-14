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
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class secretaire extends AppCompatActivity {

    DrawerLayout mDrawerLayout ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secretaire_acceuil);

        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }
        Navigation();
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
    }




    public  void Navigation(){


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.nom_menu);
        username.setText(Session.user_name);
        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.acceuil_secretaire: {
                        startActivity(new Intent(getApplicationContext(),secretaire.class));
                        break;
                    }
                    case R.id.calendrier: {
                        startActivity(new Intent(getApplicationContext(),create_calendar.class));
                        break;
                    }
                    case R.id.rdv: {
                        startActivity(new Intent(getApplicationContext(),Tabs.class));
                        break;
                    }
                    case R.id.prf: {
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        break;
                    }
                    case R.id.logOutSec: {
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