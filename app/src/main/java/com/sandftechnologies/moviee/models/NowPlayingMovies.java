package com.sandftechnologies.moviee.models;

import com.google.gson.annotations.SerializedName;
import com.sandftechnologies.moviee.models.low_tier_models.Movie;

import java.util.List;

/**
 * Created by pc4 on 12/29/2017.
 */

public class NowPlayingMovies {

    @SerializedName("results")
    List<Movie> Movies;

    @SerializedName("total_pages")
    int TotalPages;

    public List<Movie> getMovies() {
        return Movies;
    }

    public int getTotalPages() {
        return TotalPages;
    }
}
