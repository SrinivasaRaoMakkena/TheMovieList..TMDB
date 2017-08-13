package com.example.srinivas.themovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Srinivas on 8/10/2017.
 */

public class MovieTrailers {
    @SerializedName("name")
    private String trailerName;

    @SerializedName("key")
    private String youtubeLinkKey;

    public MovieTrailers(String trailerName, String youtubeLinkKey) {
        this.trailerName = trailerName;
        this.youtubeLinkKey = youtubeLinkKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getYoutubeLinkKey() {
        return youtubeLinkKey;
    }

    public void setYoutubeLinkKey(String youtubeLinkKey) {
        this.youtubeLinkKey = youtubeLinkKey;
    }
}
