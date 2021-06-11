package com.oadev.mining.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.oadev.mining.utility.PrefManager;
import com.oadev.mining.R;
import com.oadev.model.User;

public class ReferActivity extends AppCompatActivity {

    ImageButton closeScreen;
    CardView Share;
    User user;
    TextView refId;
    ImageView copyRefBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        closeScreen = findViewById(R.id.close_screen);
        Share = findViewById(R.id.share_btn);
        refId = findViewById(R.id.referId);
        copyRefBtn = findViewById(R.id.copyRefbtn);
        user = PrefManager.getInstance(this).getUser();
        refId.setText(user.getRefercode());
        closeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        copyRefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Refer Code", user.getUsername());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ReferActivity.this,"Copied!",Toast.LENGTH_LONG).show();
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "Here is My Refer Code For NiN Mining Network"+" "+user.getUsername()+" "+"https://play.google.com/store/apps/details?id="+getPackageName();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share With"));
            }
        });

    }
}