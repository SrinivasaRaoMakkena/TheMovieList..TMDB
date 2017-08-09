package com.example.srinivas.themovielist;

import android.content.Context;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextView title, rating, movieReleaseDate, overView;
    ImageView v;

    StarView star;

    TextToSpeech tts;
    ImageButton starIBTN;

    private boolean  isStarOn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title = (TextView) findViewById(R.id.titleMovie);
        rating = (TextView) findViewById(R.id.rating);
        overView = (TextView) findViewById(R.id.overview);
        movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        v = (ImageView) findViewById(R.id.images);
        starIBTN = (ImageButton) findViewById(R.id.favorite);

       // star = (StarView) findViewById(R.id.starView);

//
        if (savedInstanceState != null){
            if (savedInstanceState.getBoolean("state") == true){
               // System.out.println("true"+savedInstanceState.getBoolean("state"));
                starIBTN.setImageResource(R.drawable.on_star);
                isStarOn = true;
            }else{
               // System.out.println("false"+savedInstanceState.getBoolean("state"));
                starIBTN.setImageResource(R.drawable.off_star);
                isStarOn = false;
            }
        }

        tts = new TextToSpeech(this, this);


        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        title.setText(movie.getOriginalTitle());
        rating.setText("rating\n" + movie.getRating() + "/10");

        movieReleaseDate.setText("release date\n" + movie.getReleaseDate());
        overView.setText(movie.getOverview());

        Log.i("url", movie.createImageURl() + "");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void onToggleStar(View view) {
        //System.out.println(starIBTN.getDrawable().getConstantState().toString());
       // starIBTN.setImageResource(R.drawable.on_star);
        if (starIBTN.getDrawable().getConstantState() .equals( getResources().getDrawable( R.drawable.on_star).getConstantState())){
            starIBTN.setImageResource(R.drawable.off_star);
            isStarOn = false;
        }else{
            starIBTN.setImageResource(R.drawable.on_star);
            isStarOn = true;
        }


    }

    public void starStatus(){

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState: "+isStarOn);
        outState.putBoolean("state",isStarOn);

    }
}
