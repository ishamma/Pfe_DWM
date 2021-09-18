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
 * Use the {@link notifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notifFragment extends Fragment {
    View view_notif;
    public static final String LOGIN_URL="https://pfenz.000webhostapp.com/connect/login.php";
    List<notifData> lstNotif;
    LinearLayout l;
    RecyclerView myrv;
    notif_list myAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notifFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notifFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static notifFragment newInstance(String param1, String param2) {
        notifFragment fragment = new notifFragment();
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
         view_notif = inflater.inflate(R.layout.activity_notif_list, container, false);
         stuff();
        return view_notif;    }

    public void stuff(){
        int idn=Session.id;
        String sql = "SELECT * FROM `notification` n,rendez_vous r, patient p, account a WHERE n.id_rdv=r.id_rdv and r.id_patient=p.id_patient  and p.id_account=a.user_id and a.user_id='"+Session.id+"'";

        HashMap<String, String> params = new HashMap<>();
        params.put("sql", sql);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
            @Override
            public void processFinish(JSONArray output) {
                lstNotif = new ArrayList<>();
                for(int i =0 ; i<output.length();i++){
                    try {
                        JSONObject Tache = output.getJSONObject(i);
                        String message = Tache.getString("message");
                        String date = Tache.getString("date_notif");
                        lstNotif.add(new notifData(message,date));
                    /*TextView t = new TextView(AffichagePrin.this);
                    t.setText(tache);

                     l.addView(t);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                myrv    = (RecyclerView) view_notif.findViewById(R.id.notif_recycle);
                myAdapter = new notif_list(getContext(),lstNotif);
                myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
                myrv.setAdapter(myAdapter);

            }
        });
        request.execute();
    }
}