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
 * Created by Srinivas on 8/10/2017.
 */

public class TrailersAdapter extends BaseAdapter {
    private Context context; //context
    ImageView playImage;
    TextView trailerName;

    public TrailersAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return DetailActivity.trailerNames.size();
    }


    @Override
    public Object getItem(int position) {
        return DetailActivity.trailerNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.trailer_link_item, parent, false);
        }

        // get current item to be displayed
        //Item currentItem = (Item) getItem(position);

        // get the TextView for item name and item description
        playImage = (ImageView)
                convertView.findViewById(R.id.trailerImageView);
        trailerName = (TextView) convertView.findViewById(R.id.trailerName);
        trailerName.setText(DetailActivity.trailerNames.get(position));

        return convertView;
    }
}
