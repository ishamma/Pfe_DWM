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
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class patient_rdv extends AppCompatActivity {
    private TextView date;
    private CalendarView calendrier;
    private RadioGroup time,timeap;
    String Ndate;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rdv);
        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }


        calendrier= findViewById(R.id.datePicker);
        date = findViewById(R.id.maDate);
        time = findViewById(R.id.radioC);
        timeap = findViewById(R.id.radioap);
        calendrier.setMinDate(System.currentTimeMillis() - 1000);

        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbarres);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////

        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                time = findViewById(R.id.radioC);
                timeap = findViewById(R.id.radioap);
                time.removeAllViews();
                timeap.removeAllViews();



                ArrayList<String>  ih = new ArrayList<>();
                 Ndate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                date.setText(Ndate);

                String sql = "SELECT  * FROM `creneaux` ";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output){
                        for(int i =0 ; i<output.length();i++){
                            try {
                                ih.add(output.getJSONObject(i).getString("heure"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        fill(ih);
                        ih.clear();
                    }
                });
                request.execute();





            }
        });


        time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                String index = radioButton.getText().toString();
                Toast toast=Toast.makeText(getApplicationContext(),index,Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                date.setText(Ndate+" "+index+""+":00");

            }
        });

    }

    public  void fill(ArrayList<String> lst){

        time = findViewById(R.id.radioC);
        timeap = findViewById(R.id.radioap);
        time.removeAllViews();
        timeap.removeAllViews();
        for ( int i = 0 ; i < 4 ; i++ ) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(lst.get(i)+":00");
            rb.setHint("22");
            time.addView(rb);
        }
        for ( int i = 4 ; i <lst.size()-1 ; i++ ) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(lst.get(i));
            rb.setHint("22");
            timeap.addView(rb);
        }
        lst.clear();


    }

    public  void Navigation(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
