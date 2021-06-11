package com.oadev.mining.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.oadev.mining.R;
import com.oadev.mining.activity.NewsActivity;
import com.oadev.model.NewsModel;
import com.oadev.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private ArrayList<TeamModel> teamModels;
    Context mContext;

    public TeamAdapter(ArrayList<TeamModel> nmodel, Context context){
        this.teamModels = nmodel;
        this.mContext =context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.team_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamModel teamModel = teamModels.get(position);
        holder.tittle.setText(teamModel.getTeamTittle());
        if (teamModel.getStatus().equals("1")){

            holder.status.setText("Active");
            holder.status.setTextColor(Color.parseColor("#1de9b6"));
            for (Drawable drawable : holder.status.getCompoundDrawablesRelative()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#1de9b6"), PorterDuff.Mode.SRC_IN));
                }
            }
        }else {
            holder.status.setText("Inactive");
            holder.status.setTextColor(Color.parseColor("#F44336"));
            for (Drawable drawable : holder.status.getCompoundDrawablesRelative()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.SRC_IN));
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return teamModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tittle,status;
        ImageView teamImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.teamtittle);
            status = itemView.findViewById(R.id.team_status);
            teamImg = itemView.findViewById(R.id.teamImg);
        }
    }
}
