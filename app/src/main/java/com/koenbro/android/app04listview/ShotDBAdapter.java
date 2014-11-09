package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 11/1/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class ShotDBAdapter {
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
    public ShotDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    public ShotDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private ContentValues shotToContentValues (Shot shot){
        ContentValues values = new ContentValues();
        //values.put(DBContract.TableShot.COLUMN_1, String.valueOf(shot.getShotDate()));

        return(values);
    }
    private Shot cursorToShot(Cursor cursor){
        Shot shot = new Shot();
        shot.setId(Long.parseLong(cursor.getString(0)));
        //shot.setShotDate(Date. (cursor.getString(1)));

        shot.setComment(cursor.getString(13));
        return(shot);
    }

    public void addShot(Shot shot) {
        ContentValues values = shotToContentValues(shot);
        mDb.insert(DBContract.TableShot.TABLE_NAME, null, values);
//        Log.d(DBContract.TableShot.TAG, TAG_ADD + shot.toString());
    }
    public Shot getShot(long id) {
        Cursor cursor = mDb.query(DBContract.TableShot.TABLE_NAME,
                DBContract.TableShot.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        Shot shot = cursorToShot(cursor);
//        Log.d(DBContract.TableShot.TAG, TAG_GET_ONE + id + ") " + shot.toString());
        cursor.close();
        return(shot);
    }
    public ArrayList<Shot> getAllShots(){
        Shot shot;
        ArrayList<Shot> shots = new ArrayList<Shot>();
        String query = "SELECT * FROM " + DBContract.TableShot.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                shot = cursorToShot(cursor);
                shots.add(shot);
                Log.d(DBContract.TableShot.TAG, "id: " + cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
//        Log.d(DBContract.TableShot.TAG, TAG_GET_ALL  + shots.toString());
        cursor.close();
        return (shots);
    }
    public int updateShot(Shot shot){
        ContentValues values = shotToContentValues(shot);
        int i = mDb.update(DBContract.TableShot.TABLE_NAME,
                values,
                DBContract.TableShot.COLUMN_ID+"=?",
                new String [] {String.valueOf(shot.getId())});
//        Log.d(DBContract.TableShot.TAG,  TAG_UPDATE + shot.toString());
        return(i);
    }
    public void deleteShot(Shot shot){
        mDb.delete(DBContract.TableShot.TABLE_NAME,
                DBContract.TableShot.COLUMN_ID + " = ?",
                new String[] {String.valueOf(shot.getId())});
//        Log.d(DBContract.TableShot.TAG,  TAG_DELETE + shot.toString());
    }
}
