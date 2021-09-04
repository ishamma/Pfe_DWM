package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class pieChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);
        PieChart piechart = findViewById(R.id.barChart);
        ArrayList<PieEntry> rdv = new ArrayList<>();
        /* int[] color = {
                Color.rgb(137, 207, 240), Color.rgb(244, 194, 194),
                 Color.rgb(209, 243, 197)

        };*/        /*ArrayList<String> months = new ArrayList<>();
        months.add("Janvier"); months.add("Février");
        months.add("Mars"); months.add("Avril");
        months.add("Mai"); months.add("Juin");
        months.add("Juillet"); months.add("Aout");
        months.add("Septembre"); months.add("Octobre");
        months.add("Novembre"); months.add("Décembre");*/
        
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



        String sql = "SELECT  * FROM `account` ";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output){
//
//                try {
//                     takin = new String[output.length()][3];
//                    for (int i = 0; i < output.length(); i++) {
//                        JSONObject allClass = output.getJSONObject(i);
//                        takin[i] = new String[]{allClass.getString("email"),
//                                allClass.getString("password")};
//                }
//
//
//                }
//                catch (JSONException e) {
//                     e.printStackTrace();
//                     }
            }
        });
        request.execute();





        // TableView tableView = (TableView) findViewById(R.id.tableView);
        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setHeaderBackgroundColor(Color.WHITE);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, "Email", "Password", "Date creation", "User name"));

//        tableView.setDataAdapter(new SimpleTableDataAdapter(this,  takin));
//
//        Toast.makeText(this,tst,Toast.LENGTH_LONG);





        try {

            String[][] takin = new String[PerformNetworkRequest.jsonarray.length()][4];
            for (int i = 0; i < PerformNetworkRequest.jsonarray.length(); i++) {
                JSONObject allClass = PerformNetworkRequest.jsonarray.getJSONObject(i);
                takin[i] = new String[]{allClass.getString("email"),
                        allClass.getString("password"),
                        allClass.getString("date_creation"),
                        allClass.getString("user_name")

                };

            }
            String liste = Collections.singletonList(takin).toString();
            Log.i("Takin list" , "liste");
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, takin));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }






        //piechart.animate();
       // XAxis xAxis  = piechart.getXAxis();
      //  xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
    }
}