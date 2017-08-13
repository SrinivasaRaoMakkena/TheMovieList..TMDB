package com.example.srinivas.themovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.srinivas.themovielist.model.MovieReviews;

public class ReviewActivity extends AppCompatActivity {
    MovieReviews reviews;
    WebView review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        review = (WebView) findViewById(R.id.reviewWebView);

        Intent intent = getIntent();
        reviews = intent.getParcelableExtra("review");

        System.out.println(reviews.getReviewUrl());


        review.getSettings().setLoadsImagesAutomatically(true);
        review.getSettings().setJavaScriptEnabled(true);
        review.getSettings().setUseWideViewPort(true);
        review.getSettings().setLoadWithOverviewMode(true);
        review.loadUrl(reviews.getReviewUrl());
        review.getSettings().setSupportZoom(true);
        review.getSettings().setBuiltInZoomControls(true);
        review.getSettings().setDisplayZoomControls(false);

       // review.getSettings().setJavaScriptEnabled(true);
        review.setWebViewClient(new WebClient());


    }


    private class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.i("Web View","Entered");
            return true;
        }
    }
}
