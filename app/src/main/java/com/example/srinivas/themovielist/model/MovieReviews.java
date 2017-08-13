package com.example.srinivas.themovielist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Srinivas on 8/8/2017.
 */

public class MovieReviews implements Parcelable {

    @SerializedName("url")
    private String reviewUrl;

    @SerializedName("author")
    private String author;

    public MovieReviews(String reviewUrl, String author) {
        this.reviewUrl = reviewUrl;
        this.author = author;
    }

    protected MovieReviews(Parcel in) {
        reviewUrl = in.readString();
        author = in.readString();
    }

    public static final Creator<MovieReviews> CREATOR = new Creator<MovieReviews>() {
        @Override
        public MovieReviews createFromParcel(Parcel in) {
            return new MovieReviews(in);
        }

        @Override
        public MovieReviews[] newArray(int size) {
            return new MovieReviews[size];
        }
    };

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewUrl);
        dest.writeString(author);

    }
}
