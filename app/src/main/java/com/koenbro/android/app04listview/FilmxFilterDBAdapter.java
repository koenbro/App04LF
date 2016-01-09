package com.koenbro.android.app04listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * @author laszlo
 * @date 3/8/15.
 */
public class FilmxFilterDBAdapter {
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

    public FilmxFilterDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public FilmxFilterDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    private FilmxFilter cursorToFilmxFilter(Cursor cursor) {
        FilmxFilter filmxFilter = new FilmxFilter();
        filmxFilter.setId(Long.parseLong(cursor.getString(0)));
        filmxFilter.setFilmId(Long.parseLong(cursor.getString(1)));
        filmxFilter.setFilterId(Long.parseLong(cursor.getString(2)));
        filmxFilter.setFactor(Double.parseDouble(cursor.getString(3)));
        return filmxFilter;
    }

    private ContentValues filmxFilterToContentValues(FilmxFilter filmxFilter) {
        ContentValues values = new ContentValues();
        values.put(DBContract.TableFilmFilter.COLUMN_1, String.valueOf(filmxFilter.getFilmId()));
        values.put(DBContract.TableFilmFilter.COLUMN_2, String.valueOf(filmxFilter.getFilterId()));
        values.put(DBContract.TableFilmFilter.COLUMN_3, String.valueOf(filmxFilter.getFactor()));
        return (values);
    }

    public void addFilmxFilter(FilmxFilter filmxFilter) {
        ContentValues values = filmxFilterToContentValues(filmxFilter);
        this.mDb.insert(DBContract.TableFilmFilter.TABLE_NAME, null, values);
    }

    public FilmxFilter getFilmxFilter(long id) throws SQLException {
        Cursor cursor = this.mDb.query(
                DBContract.TableFilmFilter.TABLE_NAME,
                DBContract.TableFilmFilter.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        FilmxFilter filmxFilter;
        if (cursor != null) cursor.moveToFirst();
        filmxFilter = cursorToFilmxFilter(cursor);
        cursor.close();
        return (filmxFilter);
    }

    public ArrayList<FilmxFilter> getAllFilmxFilters() {
        ArrayList<FilmxFilter> filmxFilters = new ArrayList<FilmxFilter>();
        String query = "SELECT * FROM " + DBContract.TableFilmFilter.TABLE_NAME;
        Cursor cursor = this.mDb.rawQuery(query, null);
        FilmxFilter filmxFilter;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    filmxFilter = cursorToFilmxFilter(cursor);
                    filmxFilters.add(filmxFilter);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        return (filmxFilters);
    }

    public boolean updateFilmxFilter(FilmxFilter filmxFilter) {
        ContentValues values = filmxFilterToContentValues(filmxFilter);
        return this.mDb.update(DBContract.TableFilmFilter.TABLE_NAME,
                values,
                DBContract.TableFilmFilter.COLUMN_ID + "=?",
                new String[]{String.valueOf(filmxFilter.getId())}) > 0;
    }

    public boolean deleteFilmxFilter(FilmxFilter filmxFilter) {
        return this.mDb.delete(DBContract.TableFilmFilter.TABLE_NAME,
                DBContract.TableFilmFilter.COLUMN_ID + " = ?",
                new String[]{String.valueOf(filmxFilter.getId())}) > 0;
    }
    
    
    
}
