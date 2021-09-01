package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class rdv_secretaire extends RecyclerView.Adapter<com.example.pfe_dwm.rdv_secretaire.MyViewHolder> {

    private Context mContext ;
    private List<dataSecretaire> mData ;


    public rdv_secretaire(Context mContext, List<dataSecretaire> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_rdv_secretaire,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String d= String.valueOf(mData.get(position).getDate_rdv());
        String dn= String.valueOf(mData.get(position).getCin());
        String h= String.valueOf(mData.get(position).getTime());
        holder.nomPatient.setText(mData.get(position).getPatient_name());
        holder.rdvDate.setText(h+" à "+d+":00");
        holder.dateNaiss.setText(dn);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void remove(int position){

        mData.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomPatient;
        TextView rdvDate;
        TextView dateNaiss;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomPatient = (TextView) itemView.findViewById(R.id.nom_patient) ;
            rdvDate = (TextView) itemView.findViewById(R.id.date_rdv);
            dateNaiss = (TextView) itemView.findViewById(R.id.cin);


        }
    }
}