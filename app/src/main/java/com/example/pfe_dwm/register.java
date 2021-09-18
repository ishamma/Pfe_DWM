package com.example.pfe_dwm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {
EditText nom ,prenom,cin,tele,adresse,date,email,password;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        cin=findViewById(R.id.cin);
        tele=findViewById(R.id.tele);
        adresse=findViewById(R.id.adresse);
        date=findViewById(R.id.date);

        email=findViewById(R.id.email);
        password=findViewById(R.id.mdp);

        Button register  = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sql = "INSERT INTO account( user_name, email, password, id_role)"+" VALUES ('"+nom.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"',3)" ;
                /// params for sql requete
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                    PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {


                    @Override
                    public void processFinish(JSONArray output) {

                        String sql2 = "select Max(user_id) as id_account from account" ;
                        /// params for sql requete
                        HashMap<String, String> params2 = new HashMap<>();
                        params2.put("sql",sql2);
                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {


                            @Override
                            public void processFinish(JSONArray output) throws JSONException {

                                if(output!=null){
                                    try {

                                            int idac;
                                            JSONObject account = output.getJSONObject(0);

                                            idac = account.getInt("id_account");



                                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                                        df.setTimeZone(TimeZone.getDefault()); // Whatever time zone you want to use
                                        Date datenaiss = new java.sql.Date(df.parse(date.getText().toString()).getTime());


                                            Log.i("id account",String.valueOf(idac));
                                             Log.i("Date ",String.valueOf(datenaiss));

                                            String sql3 = "INSERT INTO `patient`( `nom_patient`, `prenom_patient`, `CIN`, `adress_patient`, `tel_patient`, `date_naiss`,`id_account`) VALUES ('"+nom.getText().toString()+"','"+prenom.getText().toString()+"','"+cin.getText().toString()+"','"+adresse.getText().toString()+"','"+tele.getText().toString()+"','"+datenaiss+"','"+idac+"');" ;
                                            /// params for sql requete
                                            HashMap<String, String> params3 = new HashMap<>();
                                            params3.put("sql",sql3);

                                            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {


                                                @Override
                                                public void processFinish(JSONArray output) {
                                                    String message = "Bonjour '"+prenom.getText().toString()+"',\n Votre login est : "+email.getText().toString()+" et votre mot de pass est : "+password.getText().toString();
                                                    new SendMailTask(register.this).execute(email.getText().toString(), "Votre compte est actif", message);

                                                    Intent i = new Intent(register.this,login.class);
                                                    startActivity(i);

                                                }
                                            });
                                            request3.execute();


                                    } catch (JSONException | ParseException e) {
                                        e.printStackTrace();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                                        builder.setMessage("Format de date de naissance est incorrect \n (jj/mm/aaaa )")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }






                            }
                        });
                        request2.execute();

                    }
                });
                request.execute();

            }
        });






    }

}