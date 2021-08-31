package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;

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
    private static final String[][] SPACESHIPS = { { "Casini", "Chemical", "NASA", "Jupiter" },
            { "Spitzer", "Nuclear", "NASA", "Alpha Centauri" } };
    String tst;
    ArrayList<String> rdv =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







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
        tableView.setDataAdapter(new SimpleTableDataAdapter(MainActivity.this, takin));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }




    }
