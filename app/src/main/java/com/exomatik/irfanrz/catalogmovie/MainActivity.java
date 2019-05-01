package com.exomatik.irfanrz.catalogmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exomatik.irfanrz.catalogmovie.Model.List.ListFilm;
import com.exomatik.irfanrz.catalogmovie.Model.List.Result;
import com.exomatik.irfanrz.catalogmovie.Model.Search.SearchFilm;
import com.exomatik.irfanrz.catalogmovie.RecyclerView.ItemClickSupport;
import com.exomatik.irfanrz.catalogmovie.RecyclerView.RecyclerListFilm;
import com.exomatik.irfanrz.catalogmovie.RecyclerView.RecyclerSearchFilm;
import com.exomatik.irfanrz.catalogmovie.Retrofit.ApiListMovie;
import com.exomatik.irfanrz.catalogmovie.Retrofit.ApiSearchMovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {
    private EditText etNama;
    private RecyclerView rcList, rcSearch;
    private ArrayList<Result> listFilm = new ArrayList<Result>();
    private ArrayList<com.exomatik.irfanrz.catalogmovie.Model.Search.Result> searchFilm = new ArrayList<com.exomatik.irfanrz.catalogmovie.Model.Search.Result>();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = (EditText) findViewById(R.id.etNama);
        rcList = (RecyclerView) findViewById(R.id.rcListFilm);
        rcSearch = (RecyclerView) findViewById(R.id.rcSearchFilm);
        button = (Button) findViewById(R.id.btn_search);

        getListFilm();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchFilm(etNama.getText().toString());
                rcList.setVisibility(View.GONE);
                rcSearch.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getListFilm(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiListMovie.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiListMovie apiListMovie = retrofit.create(ApiListMovie.class);

        Call<ListFilm> call = apiListMovie.getMovies();

        call.enqueue(new Callback<ListFilm>() {
            @Override
            public void onResponse(Call<ListFilm> call, Response<ListFilm> response) {
                ListFilm films = response.body();
                listFilm = new ArrayList<Result>();

                for (int a = 0; a < films.getResults().size(); a++){
                    listFilm.add(new Result(films.getResults().get(a).getAdult(), films.getResults().get(a).getBackdropPath()
                            , films.getResults().get(a).getGenreIds(), films.getResults().get(a).getId()
                            , films.getResults().get(a).getOriginalLanguage(), films.getResults().get(a).getOriginalTitle()
                            , films.getResults().get(a).getOverview(), films.getResults().get(a).getPosterPath()
                            , films.getResults().get(a).getReleaseDate(), films.getResults().get(a).getTitle()
                            , films.getResults().get(a).getVideo(), films.getResults().get(a).getVoteAverage()
                            , films.getResults().get(a).getVoteCount(), films.getResults().get(a).getPopularity()
                    ));
                }
                setListFilm();
            }

            @Override
            public void onFailure(Call<ListFilm> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSearchFilm(String query){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSearchMovie.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiSearchMovie apiSearchMovie = retrofit.create(ApiSearchMovie.class);

        Call<SearchFilm> call = apiSearchMovie.searchMovie("movie?api_key=ef12794562ecb503d01d3a9aedff21d0&query=" + query);

        call.enqueue(new Callback<SearchFilm>() {
            @Override
            public void onResponse(Call<SearchFilm> call, Response<SearchFilm> response) {
                SearchFilm films = response.body();
                searchFilm = new ArrayList<com.exomatik.irfanrz.catalogmovie.Model.Search.Result>();

                for (int a = 0; a < films.getResults().size(); a++){
                    searchFilm.add(new com.exomatik.irfanrz.catalogmovie.Model.Search.Result(
                            films.getResults().get(a).getVoteCount(), films.getResults().get(a).getId(),
                            films.getResults().get(a).getVideo(), films.getResults().get(a).getVoteAverage(),
                            films.getResults().get(a).getTitle(), films.getResults().get(a).getPopularity(),
                            films.getResults().get(a).getPosterPath(), films.getResults().get(a).getOriginalLanguage(),
                            films.getResults().get(a).getOriginalTitle(), films.getResults().get(a).getGenreIds(),
                            films.getResults().get(a).getBackdropPath(), films.getResults().get(a).getAdult(),
                            films.getResults().get(a).getOverview(), films.getResults().get(a).getReleaseDate()
                    ));
                }
                setSearchFilm();
            }

            @Override
            public void onFailure(Call<SearchFilm> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListFilm(){
        RecyclerListFilm adapterAgenda = new RecyclerListFilm(listFilm);
        RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcList.setLayoutManager(layoutAgenda);
        rcList.setNestedScrollingEnabled(false);
        rcList.setAdapter(adapterAgenda);

        ItemClickSupport.addTo(rcList)
                .setOnItemClickListener(this);
    }

    private void setSearchFilm(){
        RecyclerSearchFilm adapterAgenda = new RecyclerSearchFilm(searchFilm);
        RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSearch.setLayoutManager(layoutAgenda);
        rcSearch.setNestedScrollingEnabled(false);
        rcSearch.setAdapter(adapterAgenda);

        ItemClickSupport.addTo(rcSearch)
                .setOnItemClickListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (rcList.getVisibility() == View.VISIBLE){
            Detail.idMovie = Integer.toString(listFilm.get(position).getId());
        }else if (rcSearch.getVisibility() == View.VISIBLE){
            Detail.idMovie = Integer.toString(searchFilm.get(position).getId());
        }
        startActivity(new Intent(MainActivity.this, Detail.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
