package com.oadev.mining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    ImageView banner;
    TextView tittle,content,time;
    String image,tittletxt,timetxt,contentxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        banner = findViewById(R.id.banner_img);
        tittle = findViewById(R.id.tittleView);
        time = findViewById(R.id.timeView);
        content = findViewById(R.id.contentView);

        tittletxt = getIntent().getStringExtra("tittle");
        timetxt = getIntent().getStringExtra("time");
        image = getIntent().getStringExtra("image");
        contentxt = getIntent().getStringExtra("content");


        Picasso.get().load(image).into(banner);
        tittle.setText(tittletxt);
        time.setText(timetxt);
        content.setText(contentxt);

    }
}