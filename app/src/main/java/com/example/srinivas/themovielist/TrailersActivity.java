package com.example.srinivas.themovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TrailersActivity extends AppCompatActivity {
WebView trailer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        Intent i = getIntent();
        trailer = (WebView) findViewById(R.id.trailerView);
//


        trailer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = trailer.getSettings();
        webSettings.setJavaScriptEnabled(true);
       //webSettings.(false);//WebSettings.PluginState.ON);
        trailer.loadUrl("https://www.youtube.com/watch?v="+i.getStringExtra("key"));




        //trailer.getSettings().setJavaScriptEnabled(true);
//        trailer.getSettings().setPluginState(WebSettings.PluginState.ON);
//        trailer.loadUrl("https://www.youtube.com/watch?v="+i.getStringExtra("key"));
        trailer.setWebChromeClient(new WebClient());
    }

    private class WebClient extends WebChromeClient {


    }
}
