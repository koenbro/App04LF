package com.koenbro.android.app04listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by laszlo on 10/27/14.
 */
public class FilmDBAdapter {
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public FilmDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public FilmDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private ContentValues FilmToContentValues (Film film){
        ContentValues values = new ContentValues();
        values.put(DBContract.TableFilm.COLUMN_1, film.getFilmName());
        values.put(DBContract.TableFilm.COLUMN_2, film.getFilmType());
        values.put(DBContract.TableFilm.COLUMN_3, String.valueOf(film.getFilmEi()));
        values.put(DBContract.TableFilm.COLUMN_4, String.valueOf(film.getFilmRc1()));
        values.put(DBContract.TableFilm.COLUMN_5, String.valueOf(film.getFilmRc2()));
        values.put(DBContract.TableFilm.COLUMN_6, String.valueOf(film.getFilmRc3()));
        values.put(DBContract.TableFilm.COLUMN_7, String.valueOf(film.getFilmRc4()));
        values.put(DBContract.TableFilm.COLUMN_8, String.valueOf(film.getFilmRc5()));
        values.put(DBContract.TableFilm.COLUMN_9, String.valueOf(film.getFilmRc6()));
        values.put(DBContract.TableFilm.COLUMN_10, String.valueOf(film.getFilmRc7()));
        return(values);
    }
    private Film cursorToFilm(Cursor cursor){
        Film film = new Film();
        film.setId(Long.parseLong(cursor.getString(0)));
        film.setFilmName(cursor.getString(1));
        film.setFilmType(cursor.getString(2));
        film.setFilmEi(Integer.parseInt(cursor.getString(3)));
        film.setFilmRc1(Double.parseDouble(cursor.getString(4)));
        film.setFilmRc2(Double.parseDouble(cursor.getString(5)));
        film.setFilmRc3(Double.parseDouble(cursor.getString(6)));
        film.setFilmRc4(Double.parseDouble(cursor.getString(7)));
        film.setFilmRc5(Double.parseDouble(cursor.getString(8)));
        film.setFilmRc6(Double.parseDouble(cursor.getString(9)));
        film.setFilmRc7(Double.parseDouble(cursor.getString(10)));
        return(film);
    }

    public void addFilm(Film film) {
        ContentValues values = FilmToContentValues(film);
        this.mDb.insert(DBContract.TableFilm.TABLE_NAME, null, values);
        //Log.d(DBContract.TableFilm.TAG, TAG_ADD + film.toString());
    }
    public Film getFilm(long id) {
        Film film;
        Cursor cursor = this.mDb.query(DBContract.TableFilm.TABLE_NAME,
                DBContract.TableFilm.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        film = cursorToFilm(cursor);
        //Log.d(DBContract.TableFilm.TAG, TAG_GET_ONE + id + ") " + film.toString());
        cursor.close();
        return(film);
    }
    public ArrayList<Film> getAllFilms(){
        Film film;
        ArrayList<Film> films = new ArrayList<Film>();
        String query = "SELECT * FROM " + DBContract.TableFilm.TABLE_NAME;
        Cursor cursor = this.mDb.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                film = cursorToFilm(cursor);
                films.add(film);
                Log.d(DBContract.TableFilm.TAG, "id: "+ cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        //Log.d(DBContract.TableFilm.TAG, DBContract.TAG_GET_ALL  + films.toString());
        cursor.close();
        return (films);
    }
    public int updateFilm(Film film){
        ContentValues values = FilmToContentValues(film);
        int i = this.mDb.update(DBContract.TableFilm.TABLE_NAME,
                values,
                DBContract.TableFilm.COLUMN_ID+"=?",
                new String [] {String.valueOf(film.getId())});
        //Log.d(DBContract.TableFilm.TAG,  TAG_UPDATE + film.toString());
        return(i);
    }
    public void deleteFilm(Film film){
        this.mDb.delete(DBContract.TableFilm.TABLE_NAME,
                DBContract.TableFilm.COLUMN_ID + " = ?",
                new String[] {String.valueOf(film.getId())});
        //Log.d(DBContract.TableFilm.TAG,  TAG_DELETE + film.toString());
    }
}
