package com.example.ardiansyah.iak_project.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ardiansyah.iak_project.MovieActivity;
import com.example.ardiansyah.iak_project.R;
import com.example.ardiansyah.iak_project.helper.MovieHelper;
import com.example.ardiansyah.iak_project.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ardiansyah on 06/08/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterHolder> {

    public Context mContext;
    public List<MovieModel.Result> movieList;


    public MovieAdapter(Context mContext, List<MovieModel.Result> movieList){
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MovieAdapter.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_movies_item, parent, false);
        AdapterHolder adapterHolder = new AdapterHolder(rowView);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.AdapterHolder holder, final int position) {
        holder.movieTitle.setText(movieList.get(position).title);

        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        Picasso.with(mContext).load(MovieHelper.MOVIE_POSTER_URL + movieList.get(position).poster_path)
                .centerCrop().resize(width/2, width/2).into(holder.movieImg);

        holder.movieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), MovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putInt("id", movieList.get(position).id);
                bundle.putString("title", movieList.get(position).title);
                bundle.putString("overview", movieList.get(position).overview);
                bundle.putDouble("rate", movieList.get(position).vote_average);
                bundle.putString("image", movieList.get(position).poster_path);
                bundle.putString("year", movieList.get(position).release_date);

                intent.putExtra("movie", bundle);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        ImageView movieImg;
        TextView movieTitle;

        public AdapterHolder(View itemView) {
            super(itemView);
            movieImg = (ImageView) itemView.findViewById(R.id.imgView);
            movieTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}
