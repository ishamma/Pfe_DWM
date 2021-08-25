package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class cards extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list  = new ArrayList<>();
        load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this,data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    load_data_from_server(data_list.get(data_list.size()-1).getId());
                }

            }
        });

    }

    private void load_data_from_server(int id) {
       //String sql = "SELECT  * FROM `account` where email = '" + username.getText().toString() + "' and password = '" + password.getText().toString()+"'";
        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("sql", sql);

        PerformNetworkRequest request = new PerformNetworkRequest("https://ihne.000webhostapp.com/card.php?id='"+id+"'", params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                //for(int i =0 ; i<output.length();i++){
                //try {
                // Log.v("name",output.getJSONObject(i).getString("NOM"));
                // } catch (JSONException e) {
                // e.printStackTrace();
                // }
                //}
                if(output!=null){
                    try {
                        for(int i =0 ; i<output.length();i++){
                            JSONObject account = output.getJSONObject(i);
                            Log.i("Ha achnu jbna mn BD",account.toString());

                            MyData data = new MyData(account.getInt("user_id"),account.getString("user_name"),
                                    account.getString("email"));
                            data_list.add(data);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        request.execute();
    }


}