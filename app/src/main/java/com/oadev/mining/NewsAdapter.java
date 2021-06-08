package com.oadev.mining;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<NewsModel> newsModels;
    Context mContext;

    public NewsAdapter(ArrayList<NewsModel> nmodel, Context context){
        newsModels = nmodel;
        mContext =context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = newsModels.get(position);
        holder.tittle.setText(newsModel.getNewsTittle());
        holder.datetime.setText(newsModel.getTime());
        Picasso.get().load(newsModel.getImglink()).into(holder.newsImg);
        holder.newsCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,NewsActivity.class);
                i.putExtra("tittle",newsModel.getNewsTittle());
                i.putExtra("time",newsModel.getTime());
                i.putExtra("image",newsModel.getImglink());
                i.putExtra("content",newsModel.getContent());
                mContext.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tittle,datetime;
        ImageView newsImg;
        CardView newsCont;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.newstittle);
            datetime = itemView.findViewById(R.id.newstime);
            newsImg = itemView.findViewById(R.id.newsImg);
            newsCont = itemView.findViewById(R.id.newsCont);
        }
    }
}
