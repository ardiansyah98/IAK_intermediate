package com.example.ardiansyah.iak_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ardiansyah.iak_project.MovieActivity;
import com.example.ardiansyah.iak_project.R;
import com.example.ardiansyah.iak_project.YoutubeActivity;
import com.example.ardiansyah.iak_project.model.TrailerModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by Ardiansyah on 12/08/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.AdapterHolder> {

    public Context mContext;
    public List<TrailerModel.Result> trailerList;

    public static final String API_KEY = "AIzaSyCq-rzj3w0s7918ILzTXd8nCyjO794efmk";


    public TrailerAdapter(Context mContext, List<TrailerModel.Result> trailerList){
        this.mContext = mContext;
        this.trailerList = trailerList;
    }

    @Override
    public TrailerAdapter.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_trailer_item, parent, false);
        AdapterHolder adapterHolder = new AdapterHolder(rowView);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, final int position) {


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(trailerList.get(position).key);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.trailerList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public AdapterHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext.getApplicationContext(), YoutubeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();
            bundle.putString("videoID", trailerList.get(getLayoutPosition()).key);
            intent.putExtra("trailer", bundle);

            mContext.startActivity(intent);
        }

    }
}