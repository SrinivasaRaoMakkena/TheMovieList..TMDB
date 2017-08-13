package com.example.srinivas.themovielist;


import android.content.Intent;


import android.os.AsyncTask;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.srinivas.themovielist.adapter.ReviewsAdapter;
import com.example.srinivas.themovielist.adapter.TrailersAdapter;
import com.example.srinivas.themovielist.model.Movie;
import com.example.srinivas.themovielist.model.MovieReviews;
import com.example.srinivas.themovielist.model.MovieTrailers;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextView title, rating, movieReleaseDate, overView;
    ImageView v;

    StarView star;

    TextToSpeech tts;
    ImageButton starIBTN;

    Movie movie;
    private boolean isStarOn;

    ListView reviewListView;
    public static List<String> reviewAuthors;
    List<MovieReviews> listOfMovieReviewsObject;
    ReviewsAdapter reviewsArrayAdapter;


    ListView trailersListView;
   public static List<String> trailerNames;
    List<MovieTrailers> listOfMovieTrailersObject;
    TrailersAdapter trailersAdapter;

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

        reviewListView = (ListView) findViewById(R.id.reviewList);
        reviewAuthors = new ArrayList<>();
        listOfMovieReviewsObject = new ArrayList<>();

        trailersListView = (ListView) findViewById(R.id.trailersListView);
        listOfMovieTrailersObject = new ArrayList<>();
        trailerNames = new ArrayList<>();


        trailersAdapter = new TrailersAdapter(this);
        trailersListView.setAdapter(trailersAdapter);

//reviews list code

        reviewsArrayAdapter = new ReviewsAdapter(this);
        reviewListView.setAdapter(reviewsArrayAdapter);


//save status of star (favourite or not)
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("state") == true) {
                // System.out.println("true"+savedInstanceState.getBoolean("state"));
                starIBTN.setImageResource(R.drawable.on_star);
                isStarOn = true;
            } else {
                // System.out.println("false"+savedInstanceState.getBoolean("state"));
                starIBTN.setImageResource(R.drawable.off_star);
                isStarOn = false;
            }
        }

        // tts = new TextToSpeech(this, this);


        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
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

        //speakOut();


        DownloadReviewsAndTrailers reviews = new DownloadReviewsAndTrailers();
        reviews.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("review", listOfMovieReviewsObject.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        DownloadTrailers downloadTrailers = new DownloadTrailers();
        downloadTrailers.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(DetailActivity.this, TrailersActivity.class);
                //Bundle b = new Bundle();
                //b.putParcelable("review", listOfMovieReviewsObject.get(position));
               // intent.putExtras(b);

                intent.putExtra("key",listOfMovieTrailersObject.get(position).getYoutubeLinkKey());
                startActivity(intent);
            }
        });

        //To disable scrolling touch of parent when we touched listview


        reviewListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        trailersListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }

//    private void speakOut() {
//        //String text = editText.getText().toString();
//        tts.speak(title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
//    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // buttonSpeak.setEnabled(true);
                //speakOut();
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
        //System.out.println("Clicked"+starIBTN.getDrawable().getConstantState().toString());
        //System.out.println(getResources().getDrawable(R.drawable.off_star).getConstantState());
        // starIBTN.setImageResource(R.drawable.on_star);
        if (isStarOn) {
            starIBTN.setImageResource(R.drawable.off_star);
            // System.out.println("off"+starIBTN.getDrawable().getConstantState().toString());
            isStarOn = false;
        } else {
            starIBTN.setImageResource(R.drawable.on_star);
            //System.out.println("on"+starIBTN.getDrawable().getConstantState().toString());
            isStarOn = true;
        }


    }

    public void starStatus() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState: " + isStarOn);
        outState.putBoolean("state", isStarOn);

    }


    public class DownloadReviewsAndTrailers extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL url = null;

            HttpURLConnection urlConnection = null;
            try {
                //url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=a6ff5301f0ec4b238a7c143f963dab9e");
//reviews  //http://api.themoviedb.org/3/movie/211672/reviews?api_key=a6ff5301f0ec4b238a7c143f963dab9e
                url = new URL("http://api.themoviedb.org/3/movie/" + movie.getMovieId()
                        + "/reviews?api_key=a6ff5301f0ec4b238a7c143f963dab9e");
// trailers      //https://www.youtube.com/watch?v=key (eg: jc86EFjLFV4 for minions)
                //http://api.themoviedb.org/3/movie/211672/videos?api_key=a6ff5301f0ec4b238a7c143f963dab9e

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //InputStreamReader inputStream = new InputStreamReader(in);

                // System.out.println(isw);


                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(in, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                in.close();
                String result = sBuilder.toString();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }


            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jObj = null;
            try {
                jObj = new JSONObject(s);
                if (jObj !=null) {
                    JSONArray jArray = jObj.getJSONArray("results");
                    System.out.println(jArray.length());
                    Gson gson = new Gson();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        MovieReviews movieRiview = gson.fromJson(jObject.toString(), MovieReviews.class);

                        reviewAuthors.add("Author: " + movieRiview.getAuthor());
                        System.out.println(i + movieRiview.getAuthor());
                        System.out.println(reviewAuthors.size());
                        listOfMovieReviewsObject.add(movieRiview);


                        // Collections.sort(listOfMovies);

//                    try {
//
//                        URL url = new URL("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath());
//
//                        listOfImages.add(url);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//
//                    title.add(jObject.getString("original_title"));
//                    rating.add(jObject.getString("vote_average"));
//
//                    System.out.println(jObject.getString("original_title"));
//                    System.out.println(jObject.getString("poster_path"));
                        //gson.fromJson(jObject,Movie.class);

                        //Collections.sort(listOfMovies);


                        // movieAdapter.notifyDataSetChanged();
                    }
                    reviewsArrayAdapter.notifyDataSetChanged();
//                if (listOfMovies.size() > 0) {
//                    for (int i = 0; i < listOfMovies.size(); i++) {
//                        try {
//                            URL url = new URL("http://image.tmdb.org/t/p/w185/" + listOfMovies.get(i).getPosterPath());
//                            listOfImages.add(url);
//
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//                movieAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class DownloadTrailers extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL url = null;

            HttpURLConnection urlConnection = null;
            try {
                //url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=a6ff5301f0ec4b238a7c143f963dab9e");
//reviews  //http://api.themoviedb.org/3/movie/211672/reviews?api_key=a6ff5301f0ec4b238a7c143f963dab9e
                url = new URL("http://api.themoviedb.org/3/movie/" + movie.getMovieId()
                        + "/videos?api_key=a6ff5301f0ec4b238a7c143f963dab9e");
// trailers      //https://www.youtube.com/watch?v=key (eg: jc86EFjLFV4 for minions)
                //http://api.themoviedb.org/3/movie/211672/videos?api_key=a6ff5301f0ec4b238a7c143f963dab9e

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //InputStreamReader inputStream = new InputStreamReader(in);

                 System.out.println("output "+in);


                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(in, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                in.close();
                String result = sBuilder.toString();
                System.out.println("result "+result);
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }


            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jObj = null;
            try {



                    jObj = new JSONObject(s);
                if (jObj != null) {
                    JSONArray jArray = jObj.getJSONArray("results");
                    System.out.println("length "+jArray.length());
                    Gson gson = new Gson();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        MovieTrailers movieTrailer = gson.fromJson(jObject.toString(), MovieTrailers.class);

                        trailerNames.add(movieTrailer.getTrailerName());
                        listOfMovieTrailersObject.add(movieTrailer);
                        System.out.println("trailers: " + trailerNames.size());

                        // Collections.sort(listOfMovies);

//                    try {
//
//                        URL url = new URL("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath());
//
//                        listOfImages.add(url);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//
//                    title.add(jObject.getString("original_title"));
//                    rating.add(jObject.getString("vote_average"));
//
//                    System.out.println(jObject.getString("original_title"));
//                    System.out.println(jObject.getString("poster_path"));
                        //gson.fromJson(jObject,Movie.class);

                        //Collections.sort(listOfMovies);


                        // movieAdapter.notifyDataSetChanged();
                    }
                    trailersAdapter.notifyDataSetChanged();
//                if (listOfMovies.size() > 0) {
//                    for (int i = 0; i < listOfMovies.size(); i++) {
//                        try {
//                            URL url = new URL("http://image.tmdb.org/t/p/w185/" + listOfMovies.get(i).getPosterPath());
//                            listOfImages.add(url);
//
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
                }
//                movieAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
