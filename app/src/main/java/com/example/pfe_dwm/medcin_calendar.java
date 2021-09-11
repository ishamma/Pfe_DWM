package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class medcin_calendar extends AppCompatActivity {
    int buttonIdNumber = 0;
    CalendarView calendar;
    ArrayList<String> heure;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medcin_calendar);
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

        int numberOfRows = 3;
        int numberOfButtonsPerRow = 0;
        heure = new ArrayList<>();


        calendar = findViewById(R.id.cal);
        //calendar.setMinDate(System.currentTimeMillis() - 1000);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int mon = month+1;
                String date = year+"/"+mon+"/"+dayOfMonth;
                Log.i("Date",date.toString());

                String sql ="\n" +
                        "SELECT DISTINCT c.* from `creneaux` c , `rendez_vous` r , `calendrier` cl where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier and cl.date_calendrier='"+date+"' ";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql", sql);


                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Log.i("Heure ", String.valueOf(output.length()));
                        final LinearLayout verticalLayout= findViewById(R.id.ln1);
                        verticalLayout.removeAllViews();
                        for(int i =0 ; i<output.length();i++){
                        try {

                            Log.i("Heure ",output.getJSONObject(i).getString("heure"));
                         heure.add(output.getJSONObject(i).getString("heure"));

                         } catch (JSONException e) {
                         e.printStackTrace();
                         }
                        }
                        Log.i("Liste Heure",heure.toString());
                        fill(numberOfButtonsPerRow);
                    }
                });
                request.execute();

            }
        });










    }

    public  void fill(  int numberOfButtonsPerRow){
        int a = heure.size();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout verticalLayout= findViewById(R.id.ln1);
         if(a>4){
        for(int i=0;i<1;i++){
            LinearLayout newLine = new LinearLayout(this);
            newLine.setLayoutParams(params);
            newLine.setOrientation(LinearLayout.HORIZONTAL);


            for(int j=0;j<4;j++){

                Button button=new Button(this);
                // You can set button parameters here:
                // button.setWidth(30);
                button.setId(buttonIdNumber);
                button.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                button.setTextSize(20);
                button.setTextColor(Color.parseColor("#546d44"));
                button.setLayoutParams(params);
                button.setText(heure.get(j));

                newLine.addView(button);
                buttonIdNumber++;
            }


            verticalLayout.addView(newLine);



        }

        for(int i=0;i<1;i++){
            LinearLayout newLine = new LinearLayout(this);
            newLine.setLayoutParams(params);
            newLine.setOrientation(LinearLayout.HORIZONTAL);


            for(int j=4;j<heure.size();j++){

                Button button=new Button(this);
                // You can set button parameters here:
                // button.setWidth(30);
                button.setId(buttonIdNumber);
                button.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                button.setTextSize(20);
                button.setTextColor(Color.parseColor("#546d44"));
                button.setLayoutParams(params);
                button.setText(heure.get(j));

                newLine.addView(button);
                buttonIdNumber++;
            }


            verticalLayout.addView(newLine);



        }

         }
         else if (a<4){
             for(int i=0;i<1;i++){
                 LinearLayout newLine = new LinearLayout(this);
                 newLine.setLayoutParams(params);
                 newLine.setOrientation(LinearLayout.HORIZONTAL);


                 for(int j=0;j<heure.size();j++){

                     Button button=new Button(this);
                     // You can set button parameters here:
                     // button.setWidth(30);
                     button.setId(buttonIdNumber);
                     button.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                     button.setTextSize(20);
                     button.setTextColor(Color.parseColor("#546d44"));
                     button.setLayoutParams(params);
                     button.setText(heure.get(j));

                     newLine.addView(button);
                     buttonIdNumber++;
                 }


                 verticalLayout.addView(newLine);



             }

         }
         heure.clear();
    }

    public  void Navigation(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.acceuil_medcin: {
                        startActivity(new Intent(getApplicationContext(),medecin.class));
                        break;
                    }

                    case R.id.dashboard: {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;
                    }
                    case R.id.calmed: {
                        startActivity(new Intent(getApplicationContext(),medcin_calendar.class));
                        break;
                    }

                    case R.id.gstsec: {
                        startActivity(new Intent(getApplicationContext(),patient_rdv.class));
                        break;
                    }

                    case R.id.rdvlist: {
                        startActivity(new Intent(getApplicationContext(),rdv_medcin.class));
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
