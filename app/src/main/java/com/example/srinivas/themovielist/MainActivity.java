package com.example.srinivas.themovielist;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    static ArrayList<URL> listOfImages;
    static ArrayList<String> title;
    static ArrayList<String> rating;
    static Context context;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        listOfImages = new ArrayList<>();
        movieAdapter = new MovieAdapter(this,listOfImages);

        gridView = (GridView) findViewById(R.id.movieGrid);
        gridView.setAdapter(movieAdapter);

        //ArrayAdapter arrayAdapter = new ArrayAdapter();

        DownloadMoviesData moviesData = new DownloadMoviesData();
        moviesData.execute();


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
                for (int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);

                    try {
                        URL url = new URL("http://image.tmdb.org/t/p/w185/"+jObject.getString("poster_path"));

                        listOfImages.add(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    System.out.println(jObject.getString("original_title"));
                    System.out.println(jObject.getString("poster_path"));
                    //gson.fromJson(jObject,Movie.class);

                    movieAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
