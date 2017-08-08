package com.example.srinivas.themovielist;

import android.content.Context;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
TextView title,rating,movieReleaseDate,overView;
    ImageView v;

    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title = (TextView) findViewById(R.id.titleMovie);
        rating = (TextView) findViewById(R.id.rating);
        overView = (TextView) findViewById(R.id.overview);
        movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        v=(ImageView) findViewById(R.id.images);

        tts = new TextToSpeech(this, this);


        Intent intent = getIntent();
       Movie movie = intent.getParcelableExtra("movie");
        title.setText(movie.getOriginalTitle());
        rating.setText("rating\n"+movie.getRating()+"/10");

        movieReleaseDate.setText("release date\n"+movie.getReleaseDate());
        overView.setText(movie.getOverview());

        Log.i("url",movie.createImageURl() + "");

        Picasso.with(this).
                load(movie.createImageURl().toString())
                .into(v);
//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//
//       Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


       // v.setImageBitmap(bmp);

speakOut();


    }
    private void speakOut() {
        //String text = editText.getText().toString();
        tts.speak(title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
               // buttonSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


}
