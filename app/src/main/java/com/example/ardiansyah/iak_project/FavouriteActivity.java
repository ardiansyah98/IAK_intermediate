package com.example.ardiansyah.iak_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ardiansyah.iak_project.adapter.FavouriteAdapter;
import com.example.ardiansyah.iak_project.database.DatabaseHandler;
import com.example.ardiansyah.iak_project.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    public FavouriteAdapter favAdapter;
    public RecyclerView recyclerView;
    public List<Integer> list;
    public DatabaseHandler databaseHandler;
    LinearLayoutManager linearLayoutManager;
    public String url ;
    public GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        setTitle("Favourited Movies");

        recyclerView = (RecyclerView)findViewById(R.id.recViewFav);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        databaseHandler = new DatabaseHandler(getApplicationContext());

        list = databaseHandler.getAllFavorite();

        favAdapter = new FavouriteAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(favAdapter);
    }

}
