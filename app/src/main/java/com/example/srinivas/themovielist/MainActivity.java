package com.example.srinivas.themovielist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    static ArrayList<URL> listOfImages;
    static ArrayList<Movie> listOfMovies;

    static int width;
    static int height;


    static Context context;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        listOfImages = new ArrayList<>();
        listOfMovies = new ArrayList<>();


        movieAdapter = new MovieAdapter(this, listOfImages);

        gridView = (GridView) findViewById(R.id.movieGrid);
        gridView.setAdapter(movieAdapter);


        //rating = new ArrayList<>();


        //ArrayAdapter arrayAdapter = new ArrayAdapter();
        System.out.println(isOnline());

        metrics();

        if (isOnline()) {
            DownloadMoviesData moviesData = new DownloadMoviesData();
            moviesData.execute();


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // URL imageView = (URL) gridView.getItemAtPosition(position);


//               // Bitmap bm = imageView.getDrawingCache();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
                    Collections.sort(listOfMovies);

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    // intent.putExtra("title",title.get(position));
                    // intent.putExtra("rate",rating.get(position));

                    Bundle b = new Bundle();
                    b.putParcelable("movie", listOfMovies.get(position));
                    intent.putExtras(b);
                    // intent.putExtra("url",listOfMovies.get(position));
                    // Bitmap b=null; // your bitmap

                    //intent.putExtra("image",byteArray);
                    startActivity(intent);
                }

            });
        } else {

            Log.i("network", "No internet");
            Toast.makeText(MainActivity.this, "No network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void metrics() {

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            width = metrics.widthPixels / 2;
            height = metrics.heightPixels/2;
        } else {
            width = metrics.widthPixels / 3;
            height = metrics.heightPixels;

        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class DownloadMoviesData extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=a6ff5301f0ec4b238a7c143f963dab9e");

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
                JSONArray jArray = jObj.getJSONArray("results");
                Gson gson = new Gson();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    Movie movie = gson.fromJson(jObject.toString(), Movie.class);

                    listOfMovies.add(movie);

                    Collections.sort(listOfMovies);

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

                    Collections.sort(listOfMovies);


                    movieAdapter.notifyDataSetChanged();
                }
                if (listOfMovies.size() > 0) {
                    for (int i = 0; i < listOfMovies.size(); i++) {
                        try {
                            URL url = new URL("http://image.tmdb.org/t/p/w185/" + listOfMovies.get(i).getPosterPath());
                            listOfImages.add(url);


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                }
                movieAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
