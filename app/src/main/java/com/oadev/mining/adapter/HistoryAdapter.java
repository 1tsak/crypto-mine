package com.oadev.mining.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oadev.mining.R;
import com.oadev.model.HistoryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<HistoryModel> historyModels;

    public HistoryAdapter(ArrayList<HistoryModel> hmodel){
        historyModels = hmodel;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.history_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel histItem = historyModels.get(position);
        holder.tittle.setText(histItem.getHistoryTittle());
        holder.datetime.setText(histItem.getDateTime());
        holder.amount.setText(histItem.getAmount());


    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tittle,datetime,amount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.h_tittle);
            datetime = itemView.findViewById(R.id.h_time);
            amount = itemView.findViewById(R.id.coinTxt);
        }
    }
}
