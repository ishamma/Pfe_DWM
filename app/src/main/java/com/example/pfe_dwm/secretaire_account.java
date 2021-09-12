package com.example.pfe_dwm;


import android.content.Context;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class secretaire_account extends RecyclerView.Adapter<secretaire_account.MyViewHolder> {

    DrawerLayout mDrawerLayout;
    Toolbar toolbar ;

    private Context mContext ;
    private List<secData> mData ;


    public secretaire_account(Context mContext, List<secData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_secretaire_account,parent,false);
              
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.sec_nom.setText(mData.get(position).getNom_sec());
        holder.sec_cin.setText(mData.get(position).getCin_sec());
        holder.tele.setText(mData.get(position).getTele());
        holder.Supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext.getApplicationContext(), "supp", Toast.LENGTH_SHORT).show();

            }

        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext.getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void remove(int position){

        mData.remove(position);
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sec_nom;
        TextView sec_cin;
        TextView tele;
        ImageView Supp;
        ImageView edit;



        public MyViewHolder(View itemView) {
            super(itemView);


            sec_nom = (TextView) itemView.findViewById(R.id.nom_sec) ;
            sec_cin = (TextView) itemView.findViewById(R.id.cin_sec);
            tele =(TextView) itemView.findViewById(R.id.tele);
            Supp = itemView.findViewById(R.id.remove);
            edit = itemView.findViewById(R.id.edit);

        }
    }


}
