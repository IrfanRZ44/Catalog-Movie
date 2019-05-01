package com.exomatik.irfanrz.catalogmovie.Retrofit;

import com.exomatik.irfanrz.catalogmovie.Model.List.ListFilm;
import com.exomatik.irfanrz.catalogmovie.Model.Search.SearchFilm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by IrfanRZ on 12/12/2018.
 */

public interface ApiSearchMovie {
    String BASE_URL = "https://api.themoviedb.org/3/search/";

    @GET
    Call<SearchFilm> searchMovie(@Url String url);
}
