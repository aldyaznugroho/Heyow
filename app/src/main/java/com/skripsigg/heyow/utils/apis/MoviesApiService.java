package com.skripsigg.heyow.utils.apis;

import com.skripsigg.heyow.models.test.GenreResponse;
import com.skripsigg.heyow.models.test.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aldyaz on 11/6/2016.
 */

public interface MoviesApiService {
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreResponse> getMoviesGenre(@Query("api_key") String apiKey);
}
