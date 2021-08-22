package com.example.pfe_dwm;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static pieFragment newInstance(String param1, String param2) {
        pieFragment fragment = new pieFragment();
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
        View view =inflater.inflate(R.layout.fragment_pie, container, false);


        PieChart piechart = view.findViewById(R.id.barChart);
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
        //piechart.animate();
        // XAxis xAxis  = piechart.getXAxis();
        //  xAxis.setValueFormatter(new IndexAxisValueFormatter(months));










        return  view;
    }
}