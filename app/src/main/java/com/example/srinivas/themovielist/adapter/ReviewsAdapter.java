package com.example.srinivas.themovielist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srinivas.themovielist.DetailActivity;
import com.example.srinivas.themovielist.R;

/**
 * Created by Srinivas on 8/11/2017.
 */

public class ReviewsAdapter extends BaseAdapter {

    private Context context; //context
    ImageView playImage;
    TextView trailerName;

    public ReviewsAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return DetailActivity.reviewAuthors.size();
    }


    @Override
    public Object getItem(int position) {
        return DetailActivity.reviewAuthors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.review_item, parent, false);
        }

        // get current item to be displayed
        //Item currentItem = (Item) getItem(position);

        // get the TextView for item name and item description
        playImage = (ImageView)
                convertView.findViewById(R.id.reviewImageView);
        trailerName = (TextView) convertView.findViewById(R.id.reviewAuthorsName);
        trailerName.setText(DetailActivity.reviewAuthors.get(position));

        return convertView;
    }


}
