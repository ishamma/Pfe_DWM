package com.example.pfe_dwm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link secretaire_account_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class secretaire_account_fragement extends Fragment {

    View myview;

    List<secData> lstTache;

    RecyclerView myrv;
    secretaire_account myAdapter;

    int ids=Session.id;
    String sql = "SELECT * FROM secretaire ";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public secretaire_account_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment secretaire_account_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static secretaire_account_fragement newInstance(String param1, String param2) {
        secretaire_account_fragement fragment = new secretaire_account_fragement();
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
        myview = inflater.inflate(R.layout.fragment_secretaire_account, container, false);
        SearchView search = myview.findViewById(R.id.recherche);

        FloatingActionButton add = myview.findViewById(R.id.addsec);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myview.getContext(),gestion_sec.class));
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("haaa",query);

                sql = "SELECT * FROM secretaire WHERE cin_sec='"+query+"'";
                stuff(sql);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        stuff(sql);
        return myview;
    }
    public void stuff( String sql ){

        HashMap<String, String> params = new HashMap<>();
        params.put("sql", sql);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                lstTache = new ArrayList<>();
                for(int i =0 ; i<output.length();i++){
                    Log.v("mmmm",output.toString());

                    try {
                        JSONObject sec = output.getJSONObject(i);
                        String nomPatient = sec.getString("nom_secretaire")+" "+sec.getString("prenom_secretaire");
                        String tele = sec.getString("tele_secretaire");
                        String cin =sec.getString("cin_sec");
                        int id = sec.getInt("id_secretaire");

                        lstTache.add(new secData(id,nomPatient,tele,cin));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                myrv    = (RecyclerView) myview.findViewById(R.id.sec_account);
                myAdapter   = new secretaire_account(getContext(), lstTache);
                myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
                myrv.setAdapter(myAdapter);

            }
        });
        request.execute();

    }

}