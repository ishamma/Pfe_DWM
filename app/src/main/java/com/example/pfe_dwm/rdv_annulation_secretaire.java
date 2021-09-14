package com.example.pfe_dwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class rdv_annulation_secretaire extends RecyclerView.Adapter<com.example.pfe_dwm.rdv_annulation_secretaire.MyViewHolder>  {

    private Context mContext ;
    private List<annulerData> mData ;


    public rdv_annulation_secretaire(Context mContext, List<annulerData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public rdv_annulation_secretaire.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_rdv_annulation_secretaire,parent,false);
        return new rdv_annulation_secretaire.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull rdv_annulation_secretaire.MyViewHolder holder, int position) {
        String d= String.valueOf(mData.get(position).getDate_rdv());
        String dn= String.valueOf(mData.get(position).getCin());
        String h= String.valueOf(mData.get(position).getTime());
        holder.nomPatient.setText(mData.get(position).getPatient_name());
        holder.rdvDate.setText(h+" à "+d);
        holder.dateNaiss.setText(dn);
        holder.annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(mContext.getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                Log.i("Position",String.valueOf(mData.get(position).getId()) );
                Log.i("Position",String.valueOf(position) );
                Toast.makeText(mContext.getApplicationContext(), "annuler", Toast.LENGTH_SHORT).show();
            }

        });
        holder.valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "Update rendez_vous set etat='Annuler' where id_rdv = '"+mData.get(position).getId()+"'";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                Log.i("hhhhhh",sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {


                        //Notification

                        String message = "Votre Demande d''annulation est acceptée "+ mData.get(position).getDate_rdv() +" à "+mData.get(position).getTime()+" de "+mData.get(position).getPatient_name()+ "  ";
                        Log.i("Message : ",message);
                        String sql2 = "INSERT INTO `notification` ( `message`,  id_rdv) VALUES ('"+message+"',"+ mData.get(position).getId()+") ";

                        Log.i("SQL : ",sql2);

                        HashMap<String, String> params2 = new HashMap<>();
                        params2.put("sql",sql2);


                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("Annulation avec succès")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                mContext.startActivity(new Intent(mContext.getApplicationContext(),Tabs.class));
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();



                                Log.i("Position : ",String.valueOf(mData.get(position).getId()));

                                remove(position);


                            }
                        });
                        request2.execute();




                    }
                });
                request.execute();


                //Toast.makeText(mContext.getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                Log.i("Position",String.valueOf(mData.get(position).getId()) );
                Log.i("Position",String.valueOf(position) );
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