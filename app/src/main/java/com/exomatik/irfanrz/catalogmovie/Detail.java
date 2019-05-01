package com.exomatik.irfanrz.catalogmovie;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.catalogmovie.Model.Detail.DetailFilm;
import com.exomatik.irfanrz.catalogmovie.Retrofit.ApiDetailMovie;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detail extends AppCompatActivity {
    private TextView textNama, textGenre, textProduction, textStatus, textTanggal, textDescrip;
    private ImageView imageView;
    public static String idMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        textNama = (TextView) findViewById(R.id.text_nama);
        textGenre = (TextView) findViewById(R.id.text_genres);
        textProduction = (TextView) findViewById(R.id.text_production);
        textStatus = (TextView) findViewById(R.id.text_release);
        textTanggal = (TextView) findViewById(R.id.text_rilis);
        textDescrip = (TextView) findViewById(R.id.text_description);
        imageView = (ImageView) findViewById(R.id.img_poster);

        getListFilm();
    }
    private void getListFilm(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiDetailMovie apiDetailMovie = retrofit.create(ApiDetailMovie.class);

        Call<DetailFilm> call = apiDetailMovie.getDetailMovie(idMovie + "?api_key=ef12794562ecb503d01d3a9aedff21d0");

        call.enqueue(new Callback<DetailFilm>() {
            @Override
            public void onResponse(Call<DetailFilm> call, Response<DetailFilm> response) {
                DetailFilm films = response.body();

                Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500" + films.getPosterPath());
                Picasso.with(Detail.this).load(uri).into(imageView);
                textNama.setText(films.getOriginalTitle());
                textDescrip.setText("     " + films.getOverview());
                textGenre.setText(films.getGenres().get(0).getName());
                textProduction.setText(films.getProductionCompanies().get(0).getName());
                textStatus.setText(films.getStatus());
                textTanggal.setText(films.getReleaseDate());
            }

            @Override
            public void onFailure(Call<DetailFilm> call, Throwable t) {
                Toast.makeText(Detail.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
