package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ReserverRdv extends AppCompatActivity {

    ArrayList <String> arrayList = new ArrayList<>();


    DrawerLayout mDrawerLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserver_rdv);

        Navigation();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(new DateValidatorWeekdays());
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setSelection(today);
//        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.from(today);
//        constraintBuilder.setValidator(dateValidator);
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        EditText txtdate = findViewById(R.id.lbldate);
        ImageButton btndate = findViewById(R.id.btn_date);
       btndate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
           }
       });



        AutoCompleteTextView creneaux = findViewById(R.id.auto);
        TextInputEditText lbldate = findViewById(R.id.lbldate);

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                txtdate.setText(materialDatePicker.getHeaderText());
                String sql = "SELECT  * FROM `account` ";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output){
                        for(int i =0 ; i<output.length();i++){
                        try {
                         arrayList.add(output.getJSONObject(i).getString("user_name"));
                         } catch (JSONException e) {
                         e.printStackTrace();
                         }
                        }
                    }
                });
                request.execute();


            }
        });
        ArrayAdapter Myadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,arrayList);
        creneaux.setAdapter(Myadapter);
    }

    public  void Navigation(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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