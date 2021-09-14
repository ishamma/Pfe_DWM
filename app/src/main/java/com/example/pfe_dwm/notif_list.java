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

public class notif_list extends RecyclerView.Adapter<com.example.pfe_dwm.notif_list.MyViewHolder> {

    private Context mContext;
    private List<notifData> mData;

    public notif_list(Context mContext, List<notifData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    public notif_list(){}

    @Override
    public notif_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.notif_cards, parent, false);
        return new notif_list.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String n = String.valueOf(mData.get(position).getMessage());
        holder.tv_book_title_2.setText(mData.get(position).getDate_notif());
        holder.tv_book_title.setText(n);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void remove(int position) {

        mData.remove(position);
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        TextView tv_book_title_2;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.message);
            tv_book_title_2 = (TextView) itemView.findViewById(R.id.date_notif);


        }
    }
}