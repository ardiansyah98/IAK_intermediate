package com.example.ardiansyah.iak_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ardiansyah.iak_project.adapter.MovieAdapter;
import com.example.ardiansyah.iak_project.helper.MovieHelper;
import com.example.ardiansyah.iak_project.model.MovieModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public MovieModel movieModel;
    public GridLayoutManager gridLayoutManager;
    public MovieAdapter movieAdapter;
    public String apiUrl;
    public LinearLayoutManager linearLayoutManager;
    public JsonParser jsonParser;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.gridView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        apiUrl = MovieHelper.MOVIE_URL + "popular" + "?api_key=" + MovieHelper.API_KEY;
        jsonParser = new JsonParser();
        jsonParser.execute(apiUrl);
        setTitle("Popular Movies");
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
                Log.e("MainActivity", "Error ", e);
                return null;
            }
            return movieJsonStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            movieModel = new Gson().fromJson(s, MovieModel.class);
            List<MovieModel.Result> mListMovie = new ArrayList<>();

            mListMovie.addAll(movieModel.results);

            //memasukan data ke adapter movie
            movieAdapter = new MovieAdapter(getApplicationContext(), mListMovie);
            recyclerView.setAdapter(movieAdapter);
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clicked = item.getItemId();
        switch (clicked){
            case R.id.action_popular:
                apiUrl = MovieHelper.MOVIE_URL + "popular" + "?api_key=" + MovieHelper.API_KEY;
                jsonParser = new JsonParser();
                jsonParser.execute(apiUrl);
                setTitle("Popular Movies");
                break;
            case R.id.action_top_rated:
                apiUrl = MovieHelper.MOVIE_URL + "top_rated" + "?api_key=" + MovieHelper.API_KEY;
                jsonParser = new JsonParser();
                jsonParser.execute(apiUrl);
                setTitle("Top Rated Movies");
                break;
            case R.id.action_favorite:
                Intent intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
