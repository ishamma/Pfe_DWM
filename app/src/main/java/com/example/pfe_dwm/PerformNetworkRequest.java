package com.example.pfe_dwm;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, JSONArray> {

    //the url where we need to send the request
    String url;
    Context con;
    //the parameters
    HashMap<String, String> params;

    public interface AsyncResponse {
        void processFinish(JSONArray output);
    }

    public AsyncResponse delegated = null;

    //constructor to initialize values
    PerformNetworkRequest(String url, HashMap<String, String> params, AsyncResponse delegate) {
        this.url = url;
        this.params = params;
        this.delegated = delegate;
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(JSONArray s) {
        delegated.processFinish(s);


    }

    //the network operation will be performed in background
    @Override
    protected JSONArray doInBackground(Void... voids) {
        String finalresult = null;
        RequestHandler requestHandler = new RequestHandler();
        String responseJson = requestHandler.sendPostRequest(url,params);
        JSONArray j = null;
        Log.i("Message",responseJson);
        try {

            j = new JSONArray(responseJson);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }
}