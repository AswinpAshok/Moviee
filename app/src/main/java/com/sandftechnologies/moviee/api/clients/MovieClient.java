package com.sandftechnologies.moviee.api.clients;

import com.sandftechnologies.moviee.models.NowPlayingMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pc4 on 12/29/2017.
 */

public interface MovieClient {

    @GET("movie/now_playing")
    Call<NowPlayingMovies> getNowPlaying(
            @Query("api_key") String API_KEY,
            @Query("language") String lang,
            @Query("page") int pageNo

    );


}
