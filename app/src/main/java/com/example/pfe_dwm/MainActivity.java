package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    //private NavigationView mDrawerLayout;
    private Toolbar toolbar;
    private ArrayAdapter<String> SpinList;
    ;
    private String etat;
    private static final String[][] SPACESHIPS = {{"Casini", "Chemical", "NASA", "Jupiter"},
            {"Spitzer", "Nuclear", "NASA", "Alpha Centauri"}};
    String tst;
    ArrayList<String> rdv = new ArrayList<>();
    ArrayList<String> spinList;
    ArrayList<PieEntry> rdvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigation();
        //////////////////////Button pour ouvrir naviagtion ////////////////////
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////


        /////Pie Code //////////////////////
/*
        PieChart piechart = findViewById(R.id.barChart);
        ArrayList<PieEntry> rdv = new ArrayList<>();
        rdv.add(new PieEntry(70,"Réserver"));
        rdv.add(new PieEntry(10,"Annuler"));
        rdv.add(new PieEntry(20,"Disponible"));


        PieDataSet pieDataSet =new PieDataSet(rdv,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(8f);
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTextSize(8f);

        PieData pieData = new PieData(pieDataSet);

        piechart.setData(pieData);
        piechart.getDescription().setEnabled(false);
        piechart.setCenterText("Etat de Rendez-vous");
        //piechart.animate();
        // XAxis xAxis  = piechart.getXAxis();
        //  xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
*/

        ////////////////////////////////////////////////////////

        Spinner spin1 = findViewById(R.id.spin1);
        Spinner spin2 = findViewById(R.id.spin2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        String sql5 = "SELECT CONCAT(p.nom_patient,' ',p.prenom_patient)as nom,p.CIN,r.date_rdv,c.heure FROM `rendez_vous` r,`patient` p, `creneaux` c where r.id_patient=p.id_patient AND r.id_creneaux = c.id_creneaux ";
                        HashMap<String, String> params5 = new HashMap<String, String>();
                        params5.put("sql", sql5);

                        PerformNetworkRequest request5 = new PerformNetworkRequest(Api.query, params5, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());


                                // TableView tableView = (TableView) findViewById(R.id.tableView);
                                TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tbl);
                                tableView.setColumnCount(4);
                                tableView.setHeaderBackgroundColor(Color.WHITE);
                                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(MainActivity.this, "Nom", "CIN", "Date RDV", "Heure"));

                                try {

                                    String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
                                    for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                                        JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                                        takin[i] = new String[]{allClass.getString("nom"),
                                                allClass.getString("CIN"),
                                                allClass.getString("date_rdv"),
                                                allClass.getString("heure")

                                        };

                                    }
                                    String liste = Collections.singletonList(takin).toString();
                                    Log.i("Takin list", "liste");
                                    tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request5.execute();

                        String sql6 = "SELECT COUNT(IF(r.etat='Reserver',1,null)) AS reserver, COUNT(IF(r.etat='Annuler',1,null)) AS annuler, (SELECT COUNT(heure) from creneaux where id_creneaux not IN(SELECT id_creneaux FROM rendez_vous)) AS dispo from `creneaux` c , `rendez_vous` r , `calendrier` cl where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier";
                        HashMap<String, String> params6 = new HashMap<String, String>();
                        params6.put("sql", sql6);
                        Log.i("ooooow", sql6);
                        PerformNetworkRequest request6 = new PerformNetworkRequest(Api.query, params6, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());

                                try {
                                    float r, a, d;


                                    JSONObject etat_rdv = output.getJSONObject(0);
                                    r = Float.valueOf(etat_rdv.getString("reserver"));
                                    a = Float.valueOf(etat_rdv.getString("annuler"));
                                    d = Float.valueOf(etat_rdv.getString("dispo"));
                                    Log.i("etaaat:", String.valueOf(r));
                                    float l = 66;
                                    /////Pie Code //////////////////////

                                    fillPie(r, a, d);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request6.execute();

                        break;
                    case 1:
                        etat = "year";
                        spinList = new ArrayList<>();
                        spinList.clear();
                        //SpinList.notifyDataSetChanged();
                        String sql = "select DISTINCT year(`date_rdv`) as year from rendez_vous order by year";
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("sql", sql);

                        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {

                                try {
                                    for (int i = 0; i < output.length(); i++) {
                                        JSONObject years = output.getJSONObject(i);
                                        spinList.add(years.getString("year"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("jjjj", spinList.toString());
                                SpinList = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, spinList);
                                spin2.setAdapter(SpinList);
                            }

                        });
                        request.execute();

                        break;
                    case 2:
                        etat = "month";
                        spinList = new ArrayList<>();

                        spinList.clear();
                        // SpinList.notifyDataSetChanged();
                        String sql2 = "select DISTINCT Date_Format(`date_rdv`,'%M','fr_FR') as month from rendez_vous order by month";
                        HashMap<String, String> params2 = new HashMap<String, String>();
                        params2.put("sql", sql2);

                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {

                                try {
                                    for (int i = 0; i < output.length(); i++) {
                                        JSONObject years = output.getJSONObject(i);
                                        spinList.add(years.getString("month"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("jjjj", spinList.toString());
                                SpinList = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, spinList);
                                spin2.setAdapter(SpinList);
                            }

                        });
                        request2.execute();

                        break;
                    case 3:
                        etat = "day";
                        spinList = new ArrayList<>();

                        spinList.clear();
                        // SpinList.notifyDataSetChanged();
                        String sql3 = "select DISTINCT date_rdv FROM rendez_vous ORDER by date_rdv";
                        HashMap<String, String> params3 = new HashMap<String, String>();
                        params3.put("sql", sql3);

                        PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {

                                try {
                                    for (int i = 0; i < output.length(); i++) {
                                        JSONObject years = output.getJSONObject(i);
                                        spinList.add(years.getString("date_rdv"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("jjjj", spinList.toString());
                                SpinList = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, spinList);
                                spin2.setAdapter(SpinList);
                            }

                        });
                        request3.execute();

                        break;
                    case 4:
                        etat = "patient";
                        spinList = new ArrayList<>();

                        spinList.clear();
                        //  SpinList.notifyDataSetChanged();
                        String sql4 = "select DISTINCT CONCAT(nom_patient,' ',prenom_patient) as nom FROM patient ORDER by nom_patient";
                        HashMap<String, String> params4 = new HashMap<String, String>();
                        params4.put("sql", sql4);

                        PerformNetworkRequest request4 = new PerformNetworkRequest(Api.query, params4, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {

                                try {
                                    for (int i = 0; i < output.length(); i++) {
                                        JSONObject years = output.getJSONObject(i);
                                        spinList.add(years.getString("nom"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("jjjj", spinList.toString());

                                SpinList = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, spinList);

                                spin2.setAdapter(SpinList);
                            }

                        });
                        request4.execute();

                        break;
                }
                //Log.i("item",String.valueOf( parent.getItemAtPosition(position).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (etat) {
                    case "year":
                        Log.i("Selected ", spin2.getSelectedItem().toString());
                        String sql = "SELECT CONCAT(p.nom_patient,' ',p.prenom_patient)as nom,p.CIN,r.date_rdv,c.heure FROM `rendez_vous` r,`patient` p, `creneaux` c where r.id_patient=p.id_patient AND r.id_creneaux = c.id_creneaux and year(`date_rdv`) ='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("sql", sql);

                        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());


                                // TableView tableView = (TableView) findViewById(R.id.tableView);
                                TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tbl);
                                tableView.setColumnCount(4);
                                tableView.setHeaderBackgroundColor(Color.WHITE);
                                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(MainActivity.this, "Nom", "CIN", "Date RDV", "Heure"));

                                try {

                                    String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
                                    for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                                        JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                                        takin[i] = new String[]{allClass.getString("nom"),
                                                allClass.getString("CIN"),
                                                allClass.getString("date_rdv"),
                                                allClass.getString("heure")

                                        };

                                    }
                                    String liste = Collections.singletonList(takin).toString();
                                    Log.i("Takin list", "liste");
                                    tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request.execute();

                        String sqly = "SELECT COUNT(IF(r.etat='Reserver',1,null)) AS reserver, COUNT(IF(r.etat='Annuler',1,null)) AS annuler, (SELECT COUNT(heure) from creneaux where creneaux.id_creneaux not IN(SELECT id_creneaux FROM rendez_vous where year(rendez_vous.date_rdv) ='" + spin2.getSelectedItem().toString() + "')) AS dispo from `creneaux` c , `rendez_vous` r , `calendrier` cl where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier and year(r.date_rdv) ='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> paramsy = new HashMap<String, String>();
                        paramsy.put("sql", sqly);
                        Log.i("ooooow", sqly);
                        PerformNetworkRequest requesty = new PerformNetworkRequest(Api.query, paramsy, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());

                                try {
                                    float r, a, d;


                                    JSONObject etat_rdv = output.getJSONObject(0);
                                    r = Float.valueOf(etat_rdv.getString("reserver"));
                                    a = Float.valueOf(etat_rdv.getString("annuler"));
                                    d = Float.valueOf(etat_rdv.getString("dispo"));
                                    Log.i("etaaat:", String.valueOf(r));
                                    /////Pie Code //////////////////////

                                    fillPie(r, a, d);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        requesty.execute();
                        break;
                    case "month":
                        Log.i("Selected ", spin2.getSelectedItem().toString());
                        String sql2 = "SELECT CONCAT(p.nom_patient,' ',p.prenom_patient)as nom,p.CIN,r.date_rdv,c.heure FROM `rendez_vous` r,`patient` p, `creneaux` c where r.id_patient=p.id_patient AND r.id_creneaux = c.id_creneaux and Date_Format(`date_rdv`,'%M','fr_FR')='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> params2 = new HashMap<String, String>();
                        params2.put("sql", sql2);

                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());


                                // TableView tableView = (TableView) findViewById(R.id.tableView);
                                TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tbl);
                                tableView.setColumnCount(4);
                                tableView.setHeaderBackgroundColor(Color.WHITE);
                                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(MainActivity.this, "Nom", "CIN", "Date RDV", "Heure"));

                                try {

                                    String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
                                    for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                                        JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                                        takin[i] = new String[]{allClass.getString("nom"),
                                                allClass.getString("CIN"),
                                                allClass.getString("date_rdv"),
                                                allClass.getString("heure")

                                        };

                                    }
                                    String liste = Collections.singletonList(takin).toString();
                                    Log.i("Takin list", "liste");
                                    tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request2.execute();
                        String sqlm = "SELECT COUNT(IF(r.etat='Reserver',1,null)) AS reserver, COUNT(IF(r.etat='Annuler',1,null)) AS annuler, (SELECT COUNT(heure) from creneaux where creneaux.id_creneaux not IN(SELECT id_creneaux FROM rendez_vous where Date_Format(rendez_vous.date_rdv,'%M','fr_FR') ='" + spin2.getSelectedItem().toString()+"' )) AS dispo from `creneaux` c , `rendez_vous` r , `calendrier` cl where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier and  Date_Format(r.date_rdv,'%M','fr_FR') ='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> paramsm = new HashMap<String, String>();
                        paramsm.put("sql", sqlm);
                        Log.i("ooooow", sqlm);
                        PerformNetworkRequest requestm = new PerformNetworkRequest(Api.query, paramsm, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());

                                try {
                                    float r, a, d;


                                    JSONObject etat_rdv = output.getJSONObject(0);
                                    r = Float.valueOf(etat_rdv.getString("reserver"));
                                    a = Float.valueOf(etat_rdv.getString("annuler"));
                                    d = Float.valueOf(etat_rdv.getString("dispo"));
                                    Log.i("etaaat:", String.valueOf(r));
                                    /////Pie Code //////////////////////

                                    fillPie(r, a, d);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        requestm.execute();

                        break;
                    case "day":
                        Log.i("Selected ", spin2.getSelectedItem().toString());
                        String sql3 = "SELECT CONCAT(p.nom_patient,' ',p.prenom_patient)as nom,p.CIN,r.date_rdv,c.heure FROM `rendez_vous` r,`patient` p, `creneaux` c where r.id_patient=p.id_patient AND r.id_creneaux = c.id_creneaux and date_rdv ='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> params3 = new HashMap<String, String>();
                        params3.put("sql", sql3);

                        PerformNetworkRequest request3 = new PerformNetworkRequest(Api.query, params3, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());


                                // TableView tableView = (TableView) findViewById(R.id.tableView);
                                TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tbl);
                                tableView.setColumnCount(4);
                                tableView.setHeaderBackgroundColor(Color.WHITE);
                                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(MainActivity.this, "Nom", "CIN", "Date RDV", "Heure"));

                                try {

                                    String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
                                    for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                                        JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                                        takin[i] = new String[]{allClass.getString("nom"),
                                                allClass.getString("CIN"),
                                                allClass.getString("date_rdv"),
                                                allClass.getString("heure")

                                        };

                                    }
                                    String liste = Collections.singletonList(takin).toString();
                                    Log.i("Takin list", "liste");
                                    tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request3.execute();
                        String sqld = "SELECT COUNT(IF(r.etat='Reserver',1,null)) AS reserver, COUNT(IF(r.etat='Annuler',1,null)) AS annuler, (SELECT COUNT(heure) from creneaux where creneaux.id_creneaux not IN(SELECT id_creneaux FROM rendez_vous where rendez_vous.date_rdv='" + spin2.getSelectedItem().toString() + "')) AS dispo from `creneaux` c , `rendez_vous` r , `calendrier` cl where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier and  r.date_rdv='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> paramsd = new HashMap<String, String>();
                        paramsd.put("sql", sqld);
                        Log.i("ooooow", sqld);
                        PerformNetworkRequest requestd = new PerformNetworkRequest(Api.query, paramsd, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());

                                try {
                                    float r, a, d;


                                    JSONObject etat_rdv = output.getJSONObject(0);
                                    r = Float.valueOf(etat_rdv.getString("reserver"));
                                    a = Float.valueOf(etat_rdv.getString("annuler"));
                                    d = Float.valueOf(etat_rdv.getString("dispo"));
                                    Log.i("etaaat:", String.valueOf(r));
                                    /////Pie Code //////////////////////

                                    fillPie(r, a, d);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        requestd.execute();

                        break;
                    case "patient":
                        Log.i("Selected ", spin2.getSelectedItem().toString());
                        String sql4 = "SELECT CONCAT(p.nom_patient,' ',p.prenom_patient)as nom,p.CIN,r.date_rdv,c.heure FROM `rendez_vous` r,`patient` p, `creneaux` c where r.id_patient=p.id_patient AND r.id_creneaux = c.id_creneaux and CONCAT(p.nom_patient,' ',p.prenom_patient) ='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> params4 = new HashMap<String, String>();
                        params4.put("sql", sql4);

                        PerformNetworkRequest request4 = new PerformNetworkRequest(Api.query, params4, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());


                                // TableView tableView = (TableView) findViewById(R.id.tableView);
                                TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tbl);
                                tableView.setColumnCount(4);
                                tableView.setHeaderBackgroundColor(Color.WHITE);
                                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(MainActivity.this, "Nom", "CIN", "Date RDV", "Heure"));

                                try {

                                    String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
                                    for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                                        JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                                        takin[i] = new String[]{allClass.getString("nom"),
                                                allClass.getString("CIN"),
                                                allClass.getString("date_rdv"),
                                                allClass.getString("heure")

                                        };

                                    }
                                    String liste = Collections.singletonList(takin).toString();
                                    Log.i("Takin list", "liste");
                                    tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        request4.execute();

                        String sqlp = "SELECT COUNT(IF(r.etat='Reserver',1,null)) AS reserver, COUNT(IF(r.etat='Annuler',1,null)) AS annuler, (SELECT COUNT(heure) from creneaux where creneaux.id_creneaux not IN(SELECT id_creneaux FROM rendez_vous,patient p where rendez_vous.id_patient=p.id_patient and CONCAT(p.nom_patient,' ',p.prenom_patient)='" + spin2.getSelectedItem().toString() + "')) AS dispo from `creneaux` c , `rendez_vous` r , `calendrier` cl,`patient` p where r.id_creneaux = c.id_creneaux and c.id_calendrier = cl.id_calendrier and r.id_patient = p.id_patient and CONCAT(p.nom_patient,' ',p.prenom_patient)='" + spin2.getSelectedItem().toString() + "' ";
                        HashMap<String, String> paramsp = new HashMap<String, String>();
                        paramsp.put("sql", sqlp);
                        Log.i("ooooow", sqlp);
                        PerformNetworkRequest requestp = new PerformNetworkRequest(Api.query, paramsp, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                Log.i("pppp", output.toString());

                                try {
                                    float r, a, d;


                                    JSONObject etat_rdv = output.getJSONObject(0);
                                    r = Float.valueOf(etat_rdv.getString("reserver"));
                                    a = Float.valueOf(etat_rdv.getString("annuler"));
                                    d = Float.valueOf(etat_rdv.getString("dispo"));
                                    Log.i("etaaat:", String.valueOf(r));
                                    /////Pie Code //////////////////////

                                    fillPie(r, a, d);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        requestp.execute();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void fillPie(float a, float b, float c) {
        PieChart piechart = findViewById(R.id.barChart);

        /////Pie Code //////////////////////
        rdvp = new ArrayList<PieEntry>();
        if (a != 0 || b != 0 || c != 0) {
            if (a != 0) {
                rdvp.add(new PieEntry(a, "Réserver"));
            }
            if (c != 0) {
                rdvp.add(new PieEntry(c, "Disponible"));
            }

            if (b != 0) {
                rdvp.add(new PieEntry(b, "Annuler"));
            }
        }


        PieDataSet pieDataSet = new PieDataSet(rdvp, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(8f);
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTextSize(8f);

        PieData pieData = new PieData(pieDataSet);

        piechart.setData(pieData);
        piechart.getDescription().setEnabled(false);
        piechart.setCenterText("Etat de Rendez-vous");

        piechart.invalidate();
}

    public  void Navigation(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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
                        startActivity(new Intent(getApplicationContext(),patient_rdv.class));
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
