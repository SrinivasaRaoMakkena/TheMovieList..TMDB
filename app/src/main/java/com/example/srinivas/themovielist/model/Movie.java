package com.example.srinivas.themovielist.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Srinivas on 7/24/2017.
 */

public class Movie implements Parcelable,Comparable<Movie> {

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private  String rating;

    @SerializedName("id")
    private  long movieId;


    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        rating = in.readString();
        movieId = in.readLong();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String createImageURl(){
        String url = null;
        try {
            url = "http://image.tmdb.org/t/p/w185/"+ posterPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }



    public Movie(String originalTitle, String posterPath, String releaseDate, String overview, String rating, String url,long id) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.rating = rating;
        this.movieId = id;

    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getOverview() {
        return overview;

    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeLong(movieId);
    }

    @Override
    public int compareTo(@NonNull Movie o) {
        return o.getRating().compareTo(this.getRating());

    }
}
