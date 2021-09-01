package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class rdv_modification_secretaire extends RecyclerView.Adapter<com.example.pfe_dwm.rdv_modification_secretaire.MyViewHolder> {

    private Context mContext ;
    private List<modificationData> mData ;


    public rdv_modification_secretaire(Context mContext, List<modificationData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_rdv_modification_secretaire,parent,false);
        return new rdv_modification_secretaire.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String d= String.valueOf(mData.get(position).getDate_rdv());
        String dn= String.valueOf(mData.get(position).getCin());
        String h= String.valueOf(mData.get(position).getTime());
        holder.nomPatient.setText(mData.get(position).getPatient_name());
        holder.rdvDate.setText(h+" à "+d+":00");
        holder.dateNaiss.setText(dn);
        holder.annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String sql = "DELETE FROM `Tache` WHERE `Nom`='"+ holder.tv_book_title.getText()+"' and `Id_Femme`='"+Session.id+"'";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Toast.makeText(mContext.getApplicationContext(), "suppr", Toast.LENGTH_SHORT).show();
                        remove(position);
                    }
                });
                request.execute();*/
                Toast.makeText(mContext.getApplicationContext(), "annuler", Toast.LENGTH_SHORT).show();
            }

        });
        holder.valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext.getApplicationContext(),register.class);
                mContext.startActivity(intent);
                Toast.makeText(mContext.getApplicationContext(), "valider", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();    }
    private void remove(int position){

        mData.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomPatient;
        TextView rdvDate;
        TextView dateNaiss;
        ImageView annuler;
        ImageView valider;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomPatient = (TextView) itemView.findViewById(R.id.nom_patient) ;
            rdvDate = (TextView) itemView.findViewById(R.id.date_rdv);
            dateNaiss = (TextView) itemView.findViewById(R.id.cin);
            annuler = itemView.findViewById(R.id.annuler);
            valider = itemView.findViewById(R.id.valider);

        }
    }
}