package com.example.ardiansyah.iak_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ardiansyah.iak_project.DetailFavActivity;
import com.example.ardiansyah.iak_project.MovieActivity;
import com.example.ardiansyah.iak_project.R;
import com.example.ardiansyah.iak_project.database.DatabaseHandler;
import com.example.ardiansyah.iak_project.helper.MovieHelper;
import com.example.ardiansyah.iak_project.model.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ardiansyah on 13/08/2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.AdapterHolder>{

    public Context mContext;
    public List<Integer> favList;

    public FavouriteAdapter (Context mContext, List<Integer> favList){
        this.mContext = mContext;
        this.favList = favList;
    }

    @Override
    public FavouriteAdapter.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_favourite_item, parent, false);
        FavouriteAdapter.AdapterHolder adapterHolder = new FavouriteAdapter.AdapterHolder(rowView);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(final FavouriteAdapter.AdapterHolder holder, final int position) {

        int width = mContext.getResources().getDisplayMetrics().widthPixels;

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(String.valueOf(favList.get(position)),0);
        String title = sharedPreferences.getString("title","");
        String poster_path = sharedPreferences.getString("poster_path","");


        holder.favTitle.setText(title);
        Picasso.with(mContext).load(MovieHelper.MOVIE_POSTER_URL + poster_path)
                .centerCrop().resize(width/2, width/2).into(holder.favImg);

        holder.favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), DetailFavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putInt("id", favList.get(position));

                intent.putExtra("movie", bundle);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        TextView favTitle;
        ImageView favImg;

        public AdapterHolder(View itemView) {
            super(itemView);

            favTitle = (TextView)itemView.findViewById(R.id.favTitle);
            favImg = (ImageView)itemView.findViewById(R.id.favImg);
        }
    }
}
