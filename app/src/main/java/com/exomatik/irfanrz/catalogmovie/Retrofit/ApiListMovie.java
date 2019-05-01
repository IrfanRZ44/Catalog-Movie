package com.exomatik.irfanrz.catalogmovie.Retrofit;

import com.exomatik.irfanrz.catalogmovie.Model.List.ListFilm;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by IrfanRZ on 12/12/2018.
 */

public interface ApiListMovie {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    @GET("5/recommendations?api_key=ef12794562ecb503d01d3a9aedff21d0")
    Call<ListFilm> getMovies();
}
