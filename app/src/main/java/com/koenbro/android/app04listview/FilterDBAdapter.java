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
public class FilterDBAdapter {
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
    public FilterDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    public FilterDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private ContentValues FilterToContentValues(Filter filter){
        ContentValues values = new ContentValues();
        values.put(DBContract.TableFilter.COLUMN_1, filter.getFilterName());
        values.put(DBContract.TableFilter.COLUMN_2, String.valueOf(filter.isFilterForBW()));
        values.put(DBContract.TableFilter.COLUMN_3, String.valueOf(filter.isFilterForColor()));
        values.put(DBContract.TableFilter.COLUMN_4, String.valueOf(filter.getFilterFactorBW()));
        values.put(DBContract.TableFilter.COLUMN_5, String.valueOf(filter.getFilterFactorColor()));
        return values;
    }
    private Filter cursorToFilter (Cursor cursor){
        Filter filter = new Filter();
        filter.setId(Long.parseLong(cursor.getString(0)));
        filter.setFilterName(cursor.getString(1));
        filter.setFilterForBW(Boolean.parseBoolean(cursor.getString(2)));
        filter.setFilterForColor(Boolean.parseBoolean(cursor.getString(3)));
        filter.setFilterFactorBW(Float.parseFloat(cursor.getString(4)));
        filter.setFilterFactorColor(Float.parseFloat(cursor.getString(5)));
        return(filter);
    }

    public void addFilter(Filter filter) {
        ContentValues values = FilterToContentValues(filter);
        this.mDb.insert(DBContract.TableFilter.TABLE_NAME, null, values);
        //Log.d(DBContract.TableFilter.TAG, "add filter " + filter.toString());
    }
    public Filter getFilter(long id) {
        Cursor cursor = this.mDb.query(DBContract.TableFilter.TABLE_NAME,
                DBContract.TableFilter.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        Filter filter = cursorToFilter(cursor);
        //Log.d(DBContract.TableFilter.TAG, "getFilter("+id+") " + filter.toString());
        cursor.close();
        return(filter);
    }
    public ArrayList<Filter> getAllFilters(){
        ArrayList<Filter> filters = new ArrayList<Filter>();
        String query = "SELECT * FROM " + DBContract.TableFilter.TABLE_NAME;
        Cursor cursor = this.mDb.rawQuery(query, null);
        Filter filter =  null;
        if (cursor.moveToFirst()){
            do{
                filter = cursorToFilter(cursor);
                filters.add(filter);
                Log.d(DBContract.TableFilter.TAG, "id: "+ cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        //Log.d(DBContract.TableFilter.TAG, "get all filters() " + filters.toString());
        cursor.close();
        return (filters);
    }
    public boolean updateFilter(Filter filter){
        ContentValues values = FilterToContentValues(filter);
        return this.mDb.update(DBContract.TableFilter.TABLE_NAME,
                values,
                DBContract.TableFilter.COLUMN_ID+"=?",
                new String [] {String.valueOf(filter.getId())})>0;
        //Log.d(DBContract.TableFilter.TAG, "updated filter " + filter.toString());

    }
    public void deleteFilter(Filter filter){
        this.mDb.delete(DBContract.TableFilter.TABLE_NAME,
                DBContract.TableFilter.COLUMN_ID+" = ?",
                new String[] {String.valueOf(filter.getId())});
        //Log.d(DBContract.TableFilter.TAG, "delete filter " + filter.toString());
    }
}
