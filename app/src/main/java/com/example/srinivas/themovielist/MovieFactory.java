package com.example.srinivas.themovielist;

import com.example.srinivas.themovielist.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Srinivas on 7/24/2017.
 */

public class MovieFactory {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> results;

    public MovieFactory(int page, List<Movie> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
