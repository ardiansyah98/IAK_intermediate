package com.example.ardiansyah.iak_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ardiansyah.iak_project.adapter.ReviewAdapter;
import com.example.ardiansyah.iak_project.adapter.TrailerAdapter;
import com.example.ardiansyah.iak_project.database.DatabaseHandler;
import com.example.ardiansyah.iak_project.helper.MovieHelper;
import com.example.ardiansyah.iak_project.model.ReviewModel;
import com.example.ardiansyah.iak_project.model.TrailerModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MovieActivity extends AppCompatActivity {


    public RecyclerView recyclerViewVideo, recyclerViewReview;
    public TextView title, year, rate, overview;
    public ImageView poster;
    public TrailerModel trailerModel;
    public TrailerAdapter trailerAdapter;

    public LinearLayoutManager linearLayoutManager;

    public ReviewModel reviewModel;
    public ReviewAdapter reviewAdapter;

    public String videoUrl, reviewUrl;
    public JsonParser jsonParser;
    public Button btnFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        setTitle("Movie Detail");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("movie");
        final String strTitle = bundle.getString("title");
        String strYear= bundle.getString("year");
        final String strOverview = bundle.getString("overview");
        final String strUrlPoster = bundle.getString("image");
        Double dblRate = bundle.getDouble("rate");

        title = (TextView)findViewById(R.id.detailTitle);
        year = (TextView)findViewById(R.id.detailYear);
        rate = (TextView)findViewById(R.id.detailRate);
        overview = (TextView)findViewById(R.id.detailOverview);
        poster = (ImageView)findViewById(R.id.detailImg);

        String[] arrayYear = strYear.split("-");

        Picasso.with(this).load(MovieHelper.MOVIE_POSTER_URL + strUrlPoster).into(poster);

        title.setText(strTitle);
        year.setText(arrayYear[0]);
        overview.setText(strOverview);
        rate.setText(String.valueOf(dblRate)+"/10");


        final int id = bundle.getInt("id");

        btnFav = (Button) findViewById(R.id.favBtn);

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        if(databaseHandler.checkFavorite(id)) {
            btnFav.setText("Unfavourited");
        } else {
            btnFav.setText("Favourited");
        }

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                if(databaseHandler.checkFavorite(id)) {
                    databaseHandler.unFavorite(id);
                    btnFav.setText("Favourited");
                } else {
                    databaseHandler.setFavourite(id, strTitle, strOverview, strUrlPoster);
                    btnFav.setText("Unfavourited");
                }
            }
        });



        recyclerViewVideo = (RecyclerView)findViewById(R.id.listTrailer) ;
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewVideo.setLayoutManager(linearLayoutManager);

        videoUrl = MovieHelper.MOVIE_URL + id +"/videos" + "?api_key=" + MovieHelper.API_KEY;
        jsonParser = new MovieActivity.JsonParser();
        jsonParser.execute(videoUrl, "videosParameter");

        recyclerViewReview = (RecyclerView)findViewById(R.id.listReview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewReview.setLayoutManager(linearLayoutManager);

        reviewUrl = MovieHelper.MOVIE_URL + id +"/reviews" + "?api_key=" + MovieHelper.API_KEY;
        jsonParser = new MovieActivity.JsonParser();
        jsonParser.execute(reviewUrl, "reviewParameter");
    }


    class JsonParser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("MovieActivity", "Error ", e);
                return null;
            }

            return params[1]+movieJsonStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            String sub = s.substring(0,15);
            String res = s.replace(sub,"");

            if(sub.equals("videosParameter")){
                trailerModel = new Gson().fromJson(res, TrailerModel.class);
                List<TrailerModel.Result> trailerList = new ArrayList<>();

                trailerList.addAll(trailerModel.results);

                //memasukan data ke adapter trailer
                trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerList);
                recyclerViewVideo.setAdapter(trailerAdapter);
            } else if(sub.equals("reviewParameter")) {
                reviewModel = new Gson().fromJson(res, ReviewModel.class);
                List<ReviewModel.Result> reviewList = new ArrayList<>();

                reviewList.addAll(reviewModel.results);

                //memasukan data ke adapter trailer
                reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewList);
                recyclerViewReview.setAdapter(reviewAdapter);
            }

            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
