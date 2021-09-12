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

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class gestion_sec extends AppCompatActivity {
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
        setContentView(R.layout.activity_gestion_sec);
        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }
        Navigation();
        nom = findViewById(R.id.txtnom);
        prenom = findViewById(R.id.txtprenom);
        cin = findViewById(R.id.txtcin);
        date_naiss = findViewById(R.id.txtdatenaiss);
        tele = findViewById(R.id.txttele);
        adresse = findViewById(R.id.txtadress);
        psw = findViewById(R.id.txtpass);
        email =findViewById(R.id.txtemail);
        valider=findViewById(R.id.btnvalider);

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

        if ( getIntent().getBooleanExtra("Mod",false) ){

            toolbar.setTitle("Modifier Secretaire");

            String sql2 = "SELECT  * FROM `secretaire` , account  where secretaire.id_account=account.user_id and secretaire.id_secretaire= '" +getIntent().getIntExtra("id_sec",0) + "'";
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
                            email.setText(medcin.getString("email"));
                            psw.setText(medcin.getString("password"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            request2.execute();

        }


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( getIntent().getBooleanExtra("Mod",false) ){

                    String sql = "UPDATE `secretaire` SET `nom_secretaire`='"+nom.getText()+"',`prenom_secretaire`='"+prenom.getText()+"',`tele_secretaire`='"+tele.getText()+"',`sec_naiss`='"+date_naiss.getText()+"',`adresse_secretaire`='"+adresse.getText()+"',`cin_sec`='"+cin.getText()+"' WHERE id_secretaire='"+getIntent().getIntExtra("id_sec",0) +"'";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("sql", sql);

                    PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                        @Override
                        public void processFinish(JSONArray output) {
                            String sql2 = "UPDATE `account` , secretaire SET account.user_name='"+nom.getText()+"' , account.email='"+email.getText()+"' , account.password='"+psw.getText()+"'" +
                                    "where account.user_id=secretaire.id_account and secretaire.id_secretaire='"+getIntent().getIntExtra("id_sec",0)+"'";
                            HashMap<String, String> params2 = new HashMap<String, String>();
                            params2.put("sql", sql2);

                            PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                                @Override
                                public void processFinish(JSONArray output) {
                                    //Log.i("secretaire",output.toString());
                                    startActivity(new Intent(getApplicationContext(),secretaire_create_account.class));
                                }
                            });
                            request2.execute();
                        }
                    });
                    request.execute();
                }

                else {


                    String sql = "INSERT INTO account( user_name, email, password, id_role)"+" VALUES ('"+nom.getText().toString()+"','"+email.getText().toString()+"','"+psw.getText().toString()+"',2)" ;
                    /// params for sql requete
                    HashMap<String, String> params = new HashMap<>();
                    params.put("sql",sql);

                    PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {


                        @Override
                        public void processFinish(JSONArray output) {

                            String sql2 = "select Max(user_id) as id_account from account" ;

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

                                            String sql3 = "INSERT INTO `secretaire`( `nom_secretaire`, `prenom_secretaire`, `tele_secretaire`, `adresse_secretaire`, `sec_naiss`, `id_medcin`, `id_account`, `cin_sec`) " +
                                                    "VALUES ('"+nom.getText()+"','"+prenom.getText()+"','"+tele.getText()+"','"+adresse.getText()+"','"+date_naiss.getText()+"','"+1+"','"+idac+"','"+cin.getText()+"');" ;
                                            /// params for sql requete
                                            HashMap<String, String> params3 = new HashMap<>();
                                            params3.put("sql",sql3);

                                            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {


                                                @Override
                                                public void processFinish(JSONArray output) {
                                                    startActivity(new Intent(getApplicationContext(),secretaire_create_account.class));

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

            }
        });







    }




    public  void Navigation(){

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



}