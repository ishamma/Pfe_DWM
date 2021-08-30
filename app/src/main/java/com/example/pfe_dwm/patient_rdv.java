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
    private TextView name, date;
    private CalendarView calendrier;
    private RadioGroup time,timeap;
    String Ndate;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rdv);
        name = findViewById(R.id.name_patient);
        calendrier= findViewById(R.id.datePicker);
        date = findViewById(R.id.maDate);
        time = findViewById(R.id.radioC);
        timeap = findViewById(R.id.radioap);
        name.setText(Session.user_name);
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
        for ( int i = 0 ; i < 4 ; i++ ) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(lst.get(i));
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

}
