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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {
TextView nom ,prenom,cin,tele,cnss,adresse,date,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        cin=findViewById(R.id.cin);
        tele=findViewById(R.id.tele);
        cnss=findViewById(R.id.cnss);
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
//                params.put("nom",nom.getText().toString());
//                params.put("prenom",prenom.getText().toString());
//                params.put("cin",cin.getText().toString());
//                params.put("tele",tele.getText().toString());
//                params.put("cnss",cnss.getText().toString());
//                params.put("adresse",adresse.getText().toString());
//                params.put("date",date.getText().toString());
//                params.put("email",email.getText().toString());
//                params.put("password",password.getText().toString());
//                params.put("role","3");
                //SendOnChannel1(v);

              //  PerformNetworkRequest request = new PerformNetworkRequest("https://ihne.000webhostapp.com/api.php?apicall=register", params, new PerformNetworkRequest.AsyncResponse() {
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


                                            Log.i("id account",String.valueOf(idac));

                                            String sql3 = "INSERT INTO `patient`( `nom_patient`, `prenom_patient`, `CIN`, `adress_patient`, `tel_patient`, `date_naiss`, `cnss`, `id_account`) VALUES ('"+nom.getText().toString()+"','"+prenom.getText().toString()+"','"+cin.getText().toString()+"','"+adresse.getText().toString()+"','"+tele.getText().toString()+"','"+date.getText().toString()+"','"+cnss.getText().toString()+"','"+idac+"');" ;
                                            /// params for sql requete
                                            HashMap<String, String> params3 = new HashMap<>();
                                            params3.put("sql",sql3);

                                            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {


                                                @Override
                                                public void processFinish(JSONArray output) {
                                                    Intent i = new Intent(register.this,login.class);
                                                    startActivity(i);

                                                }
                                            });
                                            request3.execute();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
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