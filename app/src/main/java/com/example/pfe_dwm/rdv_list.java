package com.example.pfe_dwm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rdv_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rdv_list extends Fragment {
    View myview;
    public static final String LOGIN_URL="https://pfenz.000webhostapp.com/connect/login.php";
    List<Tache> lstTache;
    LinearLayout l;
    RecyclerView myrv;
    RecyclerAdapter myAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public rdv_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rdv_list.
     */
    // TODO: Rename and change types and number of parameters
    public static rdv_list newInstance(String param1, String param2) {
        rdv_list fragment = new rdv_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.rdv_list, container, false);
        stuff();
        return myview;
    }


    public void stuff(){
        int idn=Session.id;
        String sql = "SELECT * FROM `rendez_vous` r ,`creneaux` c,`patient` p, `account` a  WHERE r.id_creneaux=c.id_creneaux " +
                "and r.id_patient=p.id_patient and p.id_account=a.user_id and a.user_id='"+idn+"'";
        HashMap<String, String> params = new HashMap<>();
        params.put("sql", sql);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                lstTache = new ArrayList<>();
                for(int i =0 ; i<output.length();i++){
                    try {
                        JSONObject Tache = output.getJSONObject(i);
                        String tache = Tache.getString("date_rdv");
                        int id_creneaux = Tache.getInt("heure");
                        lstTache.add(new Tache(tache,id_creneaux));
                    /*TextView t = new TextView(AffichagePrin.this);
                    t.setText(tache);

                     l.addView(t);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                myrv    = (RecyclerView) myview.findViewById(R.id.recyclerview_id);
                myAdapter   = new RecyclerAdapter(getContext(), lstTache);
                myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
                myrv.setAdapter(myAdapter);

            }
        });
        request.execute();
    }

}