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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    EditText nom;
    EditText prenom;
    EditText cin;
    EditText date_naiss;
    EditText tele;
    EditText adresse;
    EditText psw ;
    EditText email;
    Button valider;
   private DrawerLayout mDrawerLayout;
   private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }
        nom = findViewById(R.id.txtnom);
        prenom = findViewById(R.id.txtprenom);
        cin = findViewById(R.id.txtcin);
        date_naiss = findViewById(R.id.txtdatenaiss);
        tele = findViewById(R.id.txttele);
        adresse = findViewById(R.id.txtadress);
        psw = findViewById(R.id.txtpass);
        email =findViewById(R.id.txtemail);
        valider=findViewById(R.id.btnvalider);
        /////////////////////btn de validation///////////////////////
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Session.id_role==1){
                  Update_medcin();
                    startActivity(new Intent(getApplicationContext(),medecin.class));
                }else if(Session.id_role==2){
                    Update_secretaire();
                    startActivity(new Intent(getApplicationContext(),secretaire.class));

                }else if(Session.id_role==3){
                    Update_patient();
                    startActivity(new Intent(getApplicationContext(),Accueil.class));

                }
            }
        });


        ////////////////******kn3emruuuu djaj*****//////////////////
        email.setText(Session.email);
        psw.setText(Session.password);


        if(Session.id_role==1) {
            String sql = "SELECT  * FROM `medcin`  where id_account = '" +Session.id + "'";
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("sql", sql);

            PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                @Override
                public void processFinish(JSONArray output) {

                    if (output != null) {
                        try {
                            JSONObject medcin= output.getJSONObject(0);
                            nom.setText(medcin.getString("nom_medcin"));
                            prenom.setText(medcin.getString("prenom_medcin"));
                            cin.setText(medcin.getString("medcin_cin"));
                            date_naiss.setText(medcin.getString("naiss_medcin"));
                            tele.setText(medcin.getString("tele_medcin"));
                            adresse.setText(medcin.getString("adresse"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            request.execute();
            Navigation_medcin();

        }else if(Session.id_role==2){
            String sql2 = "SELECT  * FROM `secretaire`  where id_account = '" +Session.id + "'";
            HashMap<String, String> params2 = new HashMap<String, String>();
            params2.put("sql", sql2);

            PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                @Override
                public void processFinish(JSONArray output) {

                    if (output != null) {
                        try {
                            JSONObject medcin= output.getJSONObject(0);
                            nom.setText(medcin.getString("nom_secretaire"));
                            prenom.setText(medcin.getString("prenom_secretaire"));
                            cin.setText(medcin.getString("cin_sec"));
                            date_naiss.setText(medcin.getString("sec_naiss"));
                            tele.setText(medcin.getString("tele_secretaire"));
                            adresse.setText(medcin.getString("adresse_secretaire"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            request2.execute();
            Navigation_secretaire();

        }else if(Session.id_role==3){
            String sql3 = "SELECT  * FROM `patient`  where id_account = '" +Session.id + "'";
            HashMap<String, String> params3 = new HashMap<String, String>();
            params3.put("sql", sql3);

            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {
                @Override
                public void processFinish(JSONArray output) {

                    if (output != null) {
                        try {
                            JSONObject medcin= output.getJSONObject(0);
                            nom.setText(medcin.getString("nom_patient"));
                            prenom.setText(medcin.getString("prenom_patient"));
                            cin.setText(medcin.getString("CIN"));
                            date_naiss.setText(medcin.getString("date_naiss"));
                            tele.setText(medcin.getString("tel_patient"));
                            adresse.setText(medcin.getString("adress_patient"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            request3.execute();
            Navigation_patient();
        }

        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbarpr);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutpr);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////
    }

    public  void Navigation_patient(){


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_patient);
       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public  void Navigation_secretaire(){


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_sectaire);
        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.acceuil_secretaire: {
                        startActivity(new Intent(getApplicationContext(),secretaire.class));
                        break;
                    }
                    case R.id.calendrier: {
                        startActivity(new Intent(getApplicationContext(),create_calendar.class));
                        break;
                    }
                    case R.id.rdv: {
                        startActivity(new Intent(getApplicationContext(),Tabs.class));
                        break;
                    }
                    case R.id.logOutSec: {
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
    public  void Navigation_medcin(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.nav_menu);
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
                        startActivity(new Intent(getApplicationContext(),secretaire_create_account.class));
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


    public void Update_medcin(){
        String sql = "UPDATE `medcin` SET `nom_medcin`='"+nom.getText()+"',`prenom_medcin`='"+prenom.getText()+"',`tele_medcin`='"+tele.getText()+"',`naiss_medcin`='"+date_naiss.getText()+"',`adresse`='"+adresse.getText()+"',`medcin_cin`='"+cin.getText()+"' WHERE id_account='"+Session.id+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                String sql2 = "UPDATE `account` SET `user_name`='"+nom.getText()+"',`email`='"+email.getText()+"',`password`='"+psw.getText()+"' WHERE id_account='"+Session.id+"'";
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("sql", sql2);

                PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                    Log.i("medcin",output.toString());
                    }
                });
                request2.execute();
            }
        });
        request.execute();
    }
    public void Update_secretaire(){
        String sql = "UPDATE `secretaire` SET `nom_secretaire`='"+nom.getText()+"',`prenom_secretaire`='"+prenom.getText()+"',`tele_secretaire`='"+tele.getText()+"',`sec_naiss`='"+date_naiss.getText()+"',`adresse_secretaire`='"+adresse.getText()+"',`cin_sec`='"+cin.getText()+"' WHERE id_account='"+Session.id+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                String sql2 = "UPDATE `account` SET `user_name`='"+nom.getText()+"',`email`='"+email.getText()+"',`password`='"+psw.getText()+"' WHERE id_account='"+Session.id+"'";
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("sql", sql2);

                PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Log.i("secretaire",output.toString());
                    }
                });
                request2.execute();
            }
        });
        request.execute();
    }
    public void Update_patient(){
        String sql = "UPDATE `patient` SET `nom_patient`='"+nom.getText()+"',`prenom_patient`='"+prenom.getText()+"',`tel_patient`='"+tele.getText()+"',`date_naiss`='"+date_naiss.getText()+"',`adress_patient`='"+adresse.getText()+"',`CIN`='"+cin.getText()+"' WHERE id_account='"+Session.id+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                String sql2 = "UPDATE `account` SET `user_name`='"+nom.getText()+"',`email`='"+email.getText()+"',`password`='"+psw.getText()+"' WHERE id_account='"+Session.id+"'";
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("sql", sql2);

                PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Log.i("patient",output.toString());
                    }
                });
                request2.execute();
            }
        });
        request.execute();
    }


}