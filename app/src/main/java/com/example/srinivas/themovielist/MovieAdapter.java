package com.example.srinivas.themovielist;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Srinivas on 7/24/2017.
 */

public class MovieAdapter extends BaseAdapter {


    private Context context; //context
    private ArrayList<URL> urls; //data source of the list adapter

    ImageView movieImage;

    //public constructor
    public MovieAdapter(Context context, ArrayList<URL> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_grid, parent, false);
        }

        // get current item to be displayed
        //Item currentItem = (Item) getItem(position);

        // get the TextView for item name and item description
         movieImage = (ImageView)
                convertView.findViewById(R.id.moviePoster);
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        Picasso.with(context).
                load(MainActivity.listOfImages.get(position).toString())
                .resize(MainActivity.width,MainActivity.height)

                .into(movieImage);


        // returns the view for the current row
        return convertView;

    }


}
