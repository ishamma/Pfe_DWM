package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    EditText nom , prenom , cin , date,cnss,tele , adress,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




    }
}