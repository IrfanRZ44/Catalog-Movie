package com.exomatik.irfanrz.catalogmovie.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.irfanrz.catalogmovie.Model.Search.Result;
import com.exomatik.irfanrz.catalogmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerSearchFilm extends RecyclerView.Adapter<RecyclerSearchFilm.bidangViewHolder> {
    private ArrayList<Result> dataList;
    private Context context;

    public RecyclerSearchFilm(ArrayList<Result> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_films, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {
        Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500" + dataList.get(position).getPosterPath());
        Picasso.with(context).load(uri).into(holder.imageView);

        holder.txtNama.setText(dataList.get(position).getTitle());
        holder.txtStatus.setText(dataList.get(position).getOverview());
        holder.txtAlamat.setText("Release Date : " + dataList.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtStatus, txtAlamat;
        private ImageView imageView;

        public bidangViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.text_title);
            imageView = (ImageView) itemView.findViewById(R.id.listImage);
            txtStatus = (TextView) itemView.findViewById(R.id.text_status);
            txtAlamat = (TextView) itemView.findViewById(R.id.text_alamat);
        }
    }
}
