package com.example.pfe_dwm;

import android.content.DialogInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {
    View myview;

    List<dataSecretaire> lstTache;

    RecyclerView myrv;
    rdv_secretaire myAdapter;

    int idn=Session.id;
    String sql = "SELECT * FROM `rendez_vous` r ,`creneaux` c,secretaire s, `account` a , patient p WHERE r.id_creneaux=c.id_creneaux and r.id_secretaire=s.id_secretaire " +
            "and s.id_account=a.user_id and r.id_patient = p.id_patient and r.etat='Reserver' and a.user_id='"+idn+"'";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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
        myview = inflater.inflate(R.layout.fragment_explore, container, false);
        SearchView search = myview.findViewById(R.id.recherche);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("haaa",query);

                 sql = "SELECT * FROM `rendez_vous` r ,`creneaux` c,secretaire s, `account` a , patient p WHERE r.id_creneaux=c.id_creneaux and r.id_secretaire=s.id_secretaire " +
                         "and s.id_account=a.user_id and r.id_patient = p.id_patient and r.etat='Reserver' and  CIN='"+query+"'";
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
                        JSONObject Tache = output.getJSONObject(i);
                        String nomPatient = Tache.getString("nom_patient")+" "+Tache.getString("prenom_patient");
                        String date_rdv = Tache.getString("date_rdv");
                        String heure = Tache.getString("heure");
                        String cin =Tache.getString("CIN");

                        lstTache.add(new dataSecretaire(nomPatient,date_rdv,heure,cin));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                myrv    = (RecyclerView) myview.findViewById(R.id.rdv_sec);
                myAdapter   = new rdv_secretaire(getContext(), lstTache);
                myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
                myrv.setAdapter(myAdapter);

            }
        });
        request.execute();

    }


}