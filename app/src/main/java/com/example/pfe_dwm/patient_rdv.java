package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class patient_rdv extends AppCompatActivity {
    private TextView date;
    private CalendarView calendrier;
    private RadioGroup time,timeap;
    private Button reserve;
    private  String index;
    private int idpp,idc;
    String Ndate, Ndatei;
    Toolbar toolbar;
    notif_icon notif = new notif_icon();
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rdv);
        if(Session.id==0){
            startActivity(new Intent(this,login.class));
        }

        /******notif icon*************/
        notif.notif_nbr(this);
        ImageView notif_cards=findViewById(R.id.notif_icon);
        notif_cards.bringToFront();
        notif_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notif.notif_list_btn(patient_rdv.this);
            }
        });
        calendrier= findViewById(R.id.datePicker);
        date = findViewById(R.id.maDate);
        time = findViewById(R.id.radioC);
        timeap = findViewById(R.id.radioap);
        calendrier.setMinDate(System.currentTimeMillis() - 1000);
        reserve =findViewById(R.id.reserver);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int idp = Session.id;
                String sql ="select id_patient from account, patient where patient.id_account=account.user_id and user_id='"+idp+"'";
                /// params for sql requete
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {

                    @Override
                    public void processFinish(JSONArray output) {
                        if(output!=null){
                            try {


                                JSONObject account = output.getJSONObject(0);

                                idpp = account.getInt("id_patient");


                                Log.i("id account",String.valueOf(idpp));

                                } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String sql2 = "select *  from creneaux c, calendrier cl where c.id_calendrier=cl.id_calendrier and cl.date_calendrier='"+Ndatei+"'and heure='"+index+"'" ;
                        /// params for sql requete
                        HashMap<String, String> params2 = new HashMap<>();
                        params2.put("sql",sql2);
                        Log.i("reqeeee:",sql2);

                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) throws JSONException {

                                if(output!=null){
                                    try {
                                        JSONObject account = output.getJSONObject(0);

                                        idc = account.getInt("id_creneaux");


                                        Log.i("id creneaux",String.valueOf(idc));
                                        String sql3;

                                        if ( getIntent().getBooleanExtra("Mod",false)){
                                            Log.i("Modifier : " ,"True");
                                             sql3 ="INSERT INTO `rendez_vous`(`date_rdv`, `id_secretaire`, `id_patient`, `id_creneaux`, `id_medcin`, `etat`) VALUES ('"+Ndatei+"',2,"+idpp+","+idc+",1,'Pre-Valider')" ;

                                            /// params for sql requete
                                            HashMap<String, String> params3 = new HashMap<>();
                                            params3.put("sql",sql3);
                                            Log.i("ash kn inserer:",sql3);

                                            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {

                                                @Override
                                                public void processFinish(JSONArray output) {





                                                 String sqlm =" Select MAX(id_rdv) as id_rdv from rendez_vous  " ;

                                                    /// params for sql requete
                                                    HashMap<String, String> paramsm = new HashMap<>();
                                                    paramsm.put("sql",sqlm);
                                                    Log.i("ash kn inserer:",sqlm);

                                                    PerformNetworkRequest requestm = new PerformNetworkRequest(Api.query, paramsm, new PerformNetworkRequest.AsyncResponse() {

                                                        @Override
                                                        public void processFinish(JSONArray output) throws JSONException {
                                                            if (output != null){
                                                                JSONObject rdv = output.getJSONObject(0);

                                                                int id_rdv_new = rdv.getInt("id_rdv");

                                                                int id_rdv_old = getIntent().getIntExtra("rdv",0);

                                                                Log.i("id_rdv_new",String.valueOf(id_rdv_new));
                                                                Log.i("id_rdv_old",String.valueOf(id_rdv_old));


                                                                String sqlu =" UPDATE `notification` SET  `id_rdv_pm`= '"+ id_rdv_new+"' WHERE id_rdv='"+id_rdv_old+"' " ;

                                                                /// params for sql requete
                                                                HashMap<String, String> paramsu = new HashMap<>();
                                                                paramsu.put("sql",sqlu);
                                                                Log.i("ash kn inserer:",sqlu);

                                                                PerformNetworkRequest requestu = new PerformNetworkRequest(Api.query, paramsu, new PerformNetworkRequest.AsyncResponse() {
                                                                    @Override
                                                                    public void processFinish(JSONArray output) throws JSONException {


                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(patient_rdv.this);
                                                                        builder.setMessage("Votre demande est en cours de traitement")
                                                                                .setCancelable(false)
                                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                        Intent i = new Intent(patient_rdv.this,Accueil.class);
                                                                                        startActivity(i);
                                                                                    }
                                                                                });
                                                                        AlertDialog alert = builder.create();
                                                                        alert.show();

                                                                    }
                                                                });
                                                                requestu.execute();


                                                                }

                                                        }
                                                    });
                                                    requestm.execute();





                                                }
                                            });
                                            request3.execute();
                                        }

                                        else {
                                            Log.i("Modifier : " ,"false");

                                            sql3  ="INSERT INTO `rendez_vous`(`date_rdv`, `id_secretaire`, `id_patient`, `id_creneaux`, `id_medcin`, `etat`) VALUES ('"+Ndatei+"',2,"+idpp+","+idc+",1,'Reserver')" ;

                                            /// params for sql requete
                                            HashMap<String, String> params3 = new HashMap<>();
                                            params3.put("sql",sql3);
                                            Log.i("ash kn inserer:",sql3);

                                            PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {

                                                @Override
                                                public void processFinish(JSONArray output) {
                                                    String message = "Vous avez reserver un rendez-vous le "+Ndatei+" à "+index;
                                                    new SendMailTask(patient_rdv.this).execute(Session.email, "Cabinet médical", message);

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(patient_rdv.this);
                                                    builder.setMessage("Rendez-vous reservé")
                                                            .setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    Intent i = new Intent(patient_rdv.this,MesRendezVous.class);
                                                                    startActivity(i);
                                                                }
                                                            });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();


                                                }
                                            });
                                            request3.execute();

                                        }



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



        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                time = findViewById(R.id.radioC);
                timeap = findViewById(R.id.radioap);
                time.removeAllViews();
                timeap.removeAllViews();



                ArrayList<String>  ih = new ArrayList<>();
                 Ndate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
                 Ndatei= String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
                date.setText(Ndate);
                 Log.i("eee",Ndatei);
               String sql = "SELECT  * FROM creneaux,calendrier where  calendrier.id_calendrier=creneaux.id_calendrier and creneaux.id_creneaux not in(Select id_creneaux from rendez_vous where date_rdv='"+Ndatei+"') and calendrier.date_calendrier='"+Ndatei+"'";
                Log.i("requet",sql);

                // String sql ="Select * from creneaux";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output){
                        for(int i =0 ; i<output.length();i++){
                            try {
                                ih.add(output.getJSONObject(i).getString("heure"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        fill(ih);
                        ih.clear();

                    }
                });
                request.execute();





            }
        });


        time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                    timeap.setOnCheckedChangeListener(null);
//                    timeap.clearCheck();
//                    timeap.setOnCheckedChangeListener();
                for (int i = 0; i < timeap.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) timeap.getChildAt(i);
                    radioButton.setChecked(false);
                }

                RadioButton radioButton = group.findViewById(checkedId);
               index = radioButton.getText().toString();
                Toast toast=Toast.makeText(getApplicationContext(),index,Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                date.setText(Ndate+" "+index+"");
            }
        });

        timeap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


//                    time.setOnCheckedChangeListener(null);
//                    time.clearCheck();
//                    time.setOnCheckedChangeListener();
                for (int i = 0; i < time.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) time.getChildAt(i);
                    radioButton.setChecked(false);
                }
                RadioButton radioButton = group.findViewById(checkedId);
                index = radioButton.getText().toString();
                Toast toast=Toast.makeText(getApplicationContext(),index,Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                date.setText(Ndate+" "+index+"");
            }
        });

        Navigation();

    }

    public  void fill(ArrayList<String> lst){

        if (lst.size() > 0){
        time = findViewById(R.id.radioC);
        timeap = findViewById(R.id.radioap);
        time.removeAllViews();
        timeap.removeAllViews();
        for ( int i = 0 ; i < 4 ; i++ ) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(lst.get(i));
            rb.setHint("22");
            time.addView(rb);
        }


        for ( int i = 4 ; i <lst.size() ; i++ ) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(lst.get(i));
            rb.setHint("22");
            timeap.addView(rb);
        }
        lst.clear();

        }
    }

    public  void Navigation(){
        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.bringToFront();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.nom_menu);
        username.setText(Session.user_name);
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

}
