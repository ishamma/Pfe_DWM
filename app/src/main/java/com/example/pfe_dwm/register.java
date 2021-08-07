package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pfe_dwm.FetchData;
import com.example.pfe_dwm.PutData;
import android.os.AsyncTask;
import android.os.Bundle;

public class register extends AppCompatActivity {

    EditText nom,prenom,cin,tele,cnss,adresse,email,password,date;
    Button register ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nom=findViewById(R.id.nom);
                prenom=findViewById(R.id.prenom);
                cin=findViewById(R.id.cin);
                tele=findViewById(R.id.tele);
                cnss=findViewById(R.id.cnss);
                adresse=findViewById(R.id.adresse);
                date=findViewById(R.id.date);
                email=findViewById(R.id.email);
                password=findViewById(R.id.mdp);
                try {


                //Starting Read data from URL
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] fields = new String[7];
                        fields[0]="nom_patient";
                        fields[1]="prenom_patient";
                        fields[2]="CIN";
                        fields[3]="adress_patient";
                        fields[4]="tel_patient";
                        fields[5]="date_naiss";
                        fields[6]="cnss";

                        String[] data = new String[7];
                        fields[0]=nom.getText().toString();
                        fields[1]=prenom.getText().toString();
                        fields[2]=cin.getText().toString();
                        fields[3]=adresse.getText().toString();
                        fields[4]=tele.getText().toString();
                        fields[5]=date.getText().toString();
                        fields[6]=cnss.getText().toString();

                        PutData putData = new PutData("https://10.0.2.2/php_scripts/signup.php","POST",fields,data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                Log.i("putData", result);
                            }
                        }
                    }
                });
                }
                catch(Exception e) {
                //End Read data from URL

                Toast.makeText(getApplicationContext(),"Button khedam a requte limakhdamach",Toast.LENGTH_LONG);
                }
            }
        });






    }

}