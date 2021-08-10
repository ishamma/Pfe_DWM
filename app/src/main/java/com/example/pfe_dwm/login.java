package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login extends AppCompatActivity {
    TextView username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn1= findViewById(R.id.btn_log);
        final TextView register = findViewById(R.id.textView6);

          username = findViewById(R.id.txtemail);
          password = findViewById(R.id.txtpassword);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Accueil.class);
//                startActivity(intent);



                //Starting Read data from URL
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] fields = new String[2];
                        fields[0]="user_name";
                        fields[1]="password";


                        String[] data = new String[2];
                        data[0]=username.getText().toString();
                        data[1]=password.getText().toString();



                        PutData putData = new PutData("https://ihne.000webhostapp.com/login.php","POST",fields,data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                Log.i("putData", result);
                                if (result == "Login Success"){
                                    Intent intent=new Intent(getApplicationContext(),Accueil.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });
    }
}