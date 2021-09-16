package com.example.pfe_dwm;


import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class RecyclerAdapter extends RecyclerView.Adapter<com.example.pfe_dwm.RecyclerAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Tache> mData ;


    public RecyclerAdapter(Context mContext, List<Tache> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cadretache,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String n= String.valueOf(mData.get(position).getId_creneaux());
        holder.tv_book_title.setText(mData.get(position).getDate());
        holder.tv_book_title_2.setText(n);
        holder.Supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
              String sql = "Update rendez_vous set etat='Pre-Annuler' where id_rdv = '"+mData.get(position).getId()+"'";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                Log.i("hhhhhh",sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {


                        //Notification

                        String message = "Demande d''annulation de Rendez-vous "+ mData.get(position).getDate() +" à "+mData.get(position).getId_creneaux()+" de "+mData.get(position).getNom().toUpperCase()+ "  ";
                        Log.i("Message : ",message);
                        String sql2 = "INSERT INTO `notification` ( `message`,  id_rdv) VALUES ('"+message+"',"+ mData.get(position).getId()+") ";

                        Log.i("SQL : ",sql2);

                        HashMap<String, String> params2 = new HashMap<>();
                        params2.put("sql",sql2);


                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                new SendMailTask((Activity)mContext).execute(Session.email, "Cabinet médical", message);

                                Toast.makeText(mContext.getApplicationContext(), "Votre demande est en cours de traitement", Toast.LENGTH_SHORT).show();


                                Log.i("Position : ",String.valueOf(mData.get(position).getId()));

                                remove(position);


                            }
                        });
                        request2.execute();



                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Votre demande est en cours de traitement"+String.valueOf(position))
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //mContext.startActivity(new Intent(mContext.getApplicationContext(),rdv_list.class));
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();


                    }
                });
                request.execute();
            }

        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "Update rendez_vous set etat='Modifier' where id_rdv = '"+mData.get(position).getId()+"'";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                Log.i("hhhhhh",sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {


                        //Notification

                        String message = "Demande de Modification de Rendez-vous "+ mData.get(position).getDate() +" à "+mData.get(position).getId_creneaux()+" de "+mData.get(position).getNom().toUpperCase()+ "  ";
                        Log.i("Message : ",message);
                        String sql2 = "INSERT INTO `notification` ( `message`,  id_rdv) VALUES ('"+message+"',"+ mData.get(position).getId()+") ";

                        Log.i("SQL : ",sql2);

                        HashMap<String, String> params2 = new HashMap<>();
                        params2.put("sql",sql2);


                        PerformNetworkRequest request2 = new PerformNetworkRequest(Api.query, params2, new PerformNetworkRequest.AsyncResponse() {
                            @Override
                            public void processFinish(JSONArray output) {
                                new SendMailTask((Activity)mContext).execute(Session.email, "Cabinet médical", message);


                                Intent intent=new Intent(mContext.getApplicationContext(),patient_rdv.class);
                                intent.putExtra("Mod", true);
                                intent.putExtra("rdv",  mData.get(position).getId());

                                mContext.startActivity(intent);

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

        TextView tv_book_title;
        TextView tv_book_title_2;
        ImageView Supp;
        ImageView edit;



        public MyViewHolder(View itemView) {
            super(itemView);


            tv_book_title = (TextView) itemView.findViewById(R.id.Tache_Nom_id) ;
            tv_book_title_2 = (TextView) itemView.findViewById(R.id.second_task);
            Supp = itemView.findViewById(R.id.imagedelete);
            edit = itemView.findViewById(R.id.imageEdit);

        }
    }


}
