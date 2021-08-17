package com.example.pfe_dwm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

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

import java.util.ArrayList;

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
        pieDataSet.setValueTextSize(10f);



        PieData pieData = new PieData(pieDataSet);

        piechart.setData(pieData);
        piechart.getDescription().setEnabled(false);
        piechart.setCenterText("Etat de Rendez-vous");
        //piechart.animate();
       // XAxis xAxis  = piechart.getXAxis();
      //  xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
    }
}