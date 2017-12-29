package com.sandftechnologies.moviee.models.low_tier_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc4 on 12/29/2017.
 */

public class Movie {

    @SerializedName("id")
    int ID;

    @SerializedName("title")
    String Name;

    @SerializedName("poster_path")
    String Poster;

    @SerializedName("overview")
    String Overview;

    @SerializedName("release_date")
    String Release;

    @SerializedName("backdrop_path")
    String wideImage;

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getPoster() {
        return Poster;
    }

    public String getWideImage() {
        return wideImage;
    }

    public String getOverview() {
        return Overview;
    }

    public String getRelease() {
        return Release;
    }
}
