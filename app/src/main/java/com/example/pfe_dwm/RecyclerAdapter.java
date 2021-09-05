package com.example.pfe_dwm;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

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
        String n= String.valueOf(mData.get(position).getId_creneaux())+":00";
        holder.tv_book_title.setText(mData.get(position).getDate());
        holder.tv_book_title_2.setText(n);
        holder.Supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
              String sql = "INSERT INTO `notification`(`message`,`id_rdv`) VALUES ('Votre demande est en traitement',1)";
                HashMap<String, String> params = new HashMap<>();
                params.put("sql",sql);
                Log.i("hhhhhh",sql);

                PerformNetworkRequest request = new PerformNetworkRequest(Api.query, params, new PerformNetworkRequest.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        Toast.makeText(mContext.getApplicationContext(), "Votre demande est en traitement", Toast.LENGTH_SHORT).show();
                        remove(position);
                    }
                });
                request.execute();
            }

        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext.getApplicationContext(),register.class);
                mContext.startActivity(intent);
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
