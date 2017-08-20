package com.example.ardiansyah.iak_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.widget.Toast;


import com.example.ardiansyah.iak_project.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ardiansyah on 13/08/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "MovieDB";
    //table
    public static final String TABLE_FAVORITE = "favorited";
    //table column
    public static final String COL_ID = "id";
    public static final String COL_POSTER = "poster_path";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";

    private Context mContext;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + COL_ID + " INTEGER,"
                + COL_TITLE + " TEXT,"
                + COL_OVERVIEW + " TEXT,"
                + COL_POSTER  + " TEXT )";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

        // Create tables again
        onCreate(db);
    }

    /**
     * CRUD SECTION
     */

    public void setFavourite(int id, String title, String overview, String poster_path) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_TITLE, title);
        values.put(COL_OVERVIEW, overview);
        values.put(COL_POSTER, poster_path);

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
        Toast.makeText(mContext, "Berhasil ditambahkan ke favorit...", Toast.LENGTH_SHORT).show();
    }

    public void unFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        Toast.makeText(mContext, "Berhasil dihapus dari favorit...", Toast.LENGTH_SHORT).show();
    }

    public boolean checkFavorite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITE + " WHERE "
                + COL_ID + " = " + String.valueOf(id), null);
        if(cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }


    public List<Integer> getAllFavorite() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITE, null);
        List<Integer> list = new ArrayList<>();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                SharedPreferences pref = mContext.getSharedPreferences(String.valueOf(cursor.getInt(0)),0);
                SharedPreferences.Editor edit = pref.edit();

                edit.putString("title",cursor.getString(1));
                edit.putString("poster_path", cursor.getString(3));
                edit.commit();

                list.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }

        return list;
    }
}
