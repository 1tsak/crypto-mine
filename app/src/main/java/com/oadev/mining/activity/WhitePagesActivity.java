package com.oadev.mining.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.oadev.mining.R;

public class WhitePagesActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_pages);
        webView = findViewById(R.id.webview);
        webView.loadUrl("file:///android_res/raw/whp");
    }
}