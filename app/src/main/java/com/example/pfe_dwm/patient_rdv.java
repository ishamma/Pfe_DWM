package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class patient_rdv extends AppCompatActivity {
    private TextView name, date;
    private CalendarView calendrier;
    private RadioGroup time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rdv);
        name = findViewById(R.id.name_patient);
        calendrier= findViewById(R.id.datePicker);
        date = findViewById(R.id.maDate);
        time = findViewById(R.id.radioC);
        name.setText(Session.user_name);
        calendrier.setMinDate(System.currentTimeMillis() - 1000);
        ArrayList<String> ih = new ArrayList<>();
        ih.add("9:00");
        ih.add("10:00");
        ih.add("11:00");
        ih.add("12:00");
        ih.add("14:00");
        ih.add("15:00");
        ih.add("16:00");
        ih.add("17:00");
        ih.add("18:00");

        for ( String heure: ih ) {
            RadioButton rb = new RadioButton(this);
            rb.setText(heure);
            time.addView(rb);
        }

        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Ndate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                date.setText(Ndate);
            }
        });
    }

}
