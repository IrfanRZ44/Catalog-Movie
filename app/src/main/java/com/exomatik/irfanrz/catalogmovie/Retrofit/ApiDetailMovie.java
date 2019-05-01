package com.exomatik.irfanrz.catalogmovie.Retrofit;

import com.exomatik.irfanrz.catalogmovie.Model.Detail.DetailFilm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by IrfanRZ on 12/12/2018.
 */

public interface ApiDetailMovie {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";


    @GET
    Call<DetailFilm> getDetailMovie(@Url String url);
}
