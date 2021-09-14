package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class create_calendar extends AppCompatActivity {
    private CalendarView calendrier;
    private CheckBox ch9,chk10,chk11,chk12,chk14,chk15,chk16,chk17;
    private CheckBox[] chkBoxs;
    private Integer[] chkBoxIds = {R.id.chk9,R.id.chk10,R.id.chk11,R.id.chk12,R.id.chk14,R.id.chk15,R.id.chk16,R.id.chk17 };
    Button inserer ;
    String date ;
    DrawerLayout mDrawerLayout ;
    Toolbar toolbar;
    int id;
    int idsec;
    ArrayList<String> heure = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_calendar);
        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }

        Navigation();
        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////

        ch9 =findViewById(R.id.chk9);
        chk10 =findViewById(R.id.chk10);
        chk11 =findViewById(R.id.chk11);
        chk12 =findViewById(R.id.chk12);
        chk14 =findViewById(R.id.chk14);
        chk15 =findViewById(R.id.chk15);
        chk16 =findViewById(R.id.chk16);
        chk17 =findViewById(R.id.chk17);

        //ArrayList<String> heure = new ArrayList<>();
        chkBoxs = new CheckBox[chkBoxIds.length];
        for(int i = 0; i < chkBoxIds.length; i++) {
            chkBoxs[i] = (CheckBox) findViewById(chkBoxIds[i]);
            chkBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        heure.add(buttonView.getText().toString());
                    }

                    else {
                        heure.remove(buttonView.getText().toString());

                    }
                }
            });
        }
        calendrier = findViewById(R.id.calendarView);

        calendrier.setMinDate(System.currentTimeMillis() - 1000);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(c);
        Log.i("ffffffff",date);
        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.i("Checkboxes :  ","tt"+heure.toString());
                 String monthn=String.valueOf(month+1);
                date=year+"-"+monthn+"-"+dayOfMonth;
                Log.i("Checkboxes :  ","Date : "+date);

            }
        });

        inserer = findViewById(R.id.btnins);
        inserer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//

                String sqlins = "INSERT INTO `calendrier`(`date_calendrier`) VALUES ('"+date+"') ";
                HashMap<String, String> paramsqlins = new HashMap<>();
                paramsqlins.put("sql",sqlins);

                PerformNetworkRequest requestins = new PerformNetworkRequest(Api.query, paramsqlins, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Log.i("Date" , date.toString());
                        String sqli = " select * from `calendrier` where `date_calendrier`='"+date+"' ";
                        HashMap<String, String> param = new HashMap<>();
                        param.put("sql",sqli);

                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, param, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output)  {
                                if(output!=null){
                                    try {
                                        JSONObject cal = output.getJSONObject(0);
                                        id = cal.getInt("id_calendrier");
                                        Log.i("TEST id cal",String.valueOf(id));
                                        int idac = Session.id;
                                        String sqlsec = " SELECT * FROM `secretaire` where `id_account`='"+idac+"' ";
                                        HashMap<String, String> paramsec = new HashMap<>();
                                        paramsec.put("sql",sqlsec);

                                        PerformNetworkRequest requestsec = new PerformNetworkRequest(Api.query, paramsec, new PerformNetworkRequest.AsyncResponse() {
                                            @Override
                                            public void processFinish(JSONArray output)  {
                                                if(output!=null){
                                                    try {
                                                        JSONObject cal = output.getJSONObject(0);
                                                        idsec = cal.getInt("id_secretaire");


                                                        Log.i("TEST id sec",String.valueOf(idsec));
                                                        for (int i = 0 ;i <heure.size();i++){
                                                            Log.i("id cal : ",String.valueOf(id));
                                                            Log.i("id sec : ",String.valueOf(idsec));
                                                            Log.i("heure : ",heure.get(i));

                                                            String sqlc = "INSERT INTO `creneaux`(`heure`, `active`, `id_calendrier`, `id_secretaire`) VALUES ('"+heure.get(i)+"','"+1+"','"+id+"','"+idsec+"')";
                                                            HashMap<String, String> paramc = new HashMap<>();
                                                            paramc.put("sql",sqlc);

                                                            PerformNetworkRequest requestc = new PerformNetworkRequest(Api.query, paramc, new PerformNetworkRequest.AsyncResponse() {
                                                                @Override
                                                                public void processFinish(JSONArray output)  {

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(create_calendar.this);
                                                                    builder.setMessage("Date ajouter avec succés")
                                                                            .setCancelable(false)
                                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int id) {
                                                                                    startActivity(new Intent(getApplicationContext(),create_calendar.class));
                                                                                }
                                                                            });
                                                                    AlertDialog alert = builder.create();
                                                                    alert.show();


                                                                }
                                                            });
                                                            requestc.execute();
                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        requestsec.execute();




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
                        request2.execute();
                    }
                });
                requestins.execute();





            }
        });

    }




    public  void Navigation(){


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.nom_menu);
        username.setText(Session.user_name);
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

                    case R.id.rdv: {
                        startActivity(new Intent(getApplicationContext(),Tabs.class));
                        break;
                    }
                    case R.id.calendrier: {
                        startActivity(new Intent(getApplicationContext(),create_calendar.class));
                        break;
                    }
                    case R.id.prf: {
                        startActivity(new Intent(getApplicationContext(),Profile.class));
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



    public void deletecal (){

        String sql = "DELETE FROM `calendrier`  , `creneaux` WHERE `calendrier`.`id_calendrier`= `creneaux`.`id_calendrier` AND calendrier.`date_calendrier`='"+date+"' ";
        HashMap<String, String> params = new HashMap<>();
        params.put("sql",sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
            }
        });
        request.execute();
    }

    public void Insertcal(){
        String sqlins = "INSERT INTO `calendrier`(`date_calendrier`) VALUES ('"+date+"') ";
        HashMap<String, String> paramsqlins = new HashMap<>();
        paramsqlins.put("sql",sqlins);

        PerformNetworkRequest requestins = new PerformNetworkRequest(Api.query, paramsqlins, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {


            }
        });
        requestins.execute();

    }

    public void getIdcal(){
        Log.i("Date" , date);
        String sqli = " select * from `calendrier` where `date_calendrier`='"+date+"' ";
        HashMap<String, String> param = new HashMap<>();
        param.put("sql",sqli);

        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, param, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output)  {
                if(output!=null){
                    try {
                        JSONObject cal = output.getJSONObject(0);
                        id = cal.getInt("id_calendrier");
                        Log.i("TEST id cal",String.valueOf(id));




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        request2.execute();
    }

    public void getIdsec(){
        int idac = Session.id;
        String sqlsec = " SELECT * FROM `secretaire` where `id_account`='"+idac+"' ";
        HashMap<String, String> paramsec = new HashMap<>();
        paramsec.put("sql",sqlsec);

        PerformNetworkRequest requestsec = new PerformNetworkRequest(Api.query, paramsec, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output)  {
                if(output!=null){
                    try {
                        JSONObject cal = output.getJSONObject(0);
                        idsec = cal.getInt("id_secretaire");
                        Log.i("TEST id sec",String.valueOf(idsec));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        requestsec.execute();
    }

    public void Insertcre(){

        for (int i = 0 ;i <heure.size();i++){
            Log.i("id cal : ",String.valueOf(id));
            Log.i("id sec : ",String.valueOf(idsec));
            Log.i("heure : ",heure.get(i));

            String sqlc = "INSERT INTO `creneaux`(`heure`, `active`, `id_calendrier`, `id_secretaire`) VALUES ('"+heure.get(i)+"','"+1+"','"+6+"','"+1+"')";
            HashMap<String, String> paramc = new HashMap<>();
            paramc.put("sql",sqlc);

            PerformNetworkRequest requestc = new PerformNetworkRequest(Api.query, paramc, new PerformNetworkRequest.AsyncResponse() {
                @Override
                public void processFinish(JSONArray output)  {

                }
            });
            requestc.execute();
        }
    }


}