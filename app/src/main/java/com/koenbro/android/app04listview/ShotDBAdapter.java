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
            super(context, DBContractShots.DB_NAME, null, DBContractShots.DB_VERSION);
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
        values.put(DBContractShots.TableShot.COLUMN_1, shot.getShotDay());
        values.put(DBContractShots.TableShot.COLUMN_2, shot.getShotTime());
        values.put(DBContractShots.TableShot.COLUMN_3, String.valueOf(shot.getPhotoShootId()));
        values.put(DBContractShots.TableShot.COLUMN_4, shot.getFilmName());
        values.put(DBContractShots.TableShot.COLUMN_5, shot.getFilmEi());
        values.put(DBContractShots.TableShot.COLUMN_6, shot.getLensName());
        values.put(DBContractShots.TableShot.COLUMN_7, shot.getLensFocal());
        values.put(DBContractShots.TableShot.COLUMN_8, shot.getFilterName());
        values.put(DBContractShots.TableShot.COLUMN_9, shot.getCameraName());
        values.put(DBContractShots.TableShot.COLUMN_10, shot.getMeterName());
        values.put(DBContractShots.TableShot.COLUMN_11, String.valueOf(shot.getAperture()));
        values.put(DBContractShots.TableShot.COLUMN_12, String.valueOf(shot.getBellowsExtension()));
        values.put(DBContractShots.TableShot.COLUMN_13, String.valueOf(shot.getMeterRead()));
        values.put(DBContractShots.TableShot.COLUMN_14, String.valueOf(shot.getFf()));
        values.put(DBContractShots.TableShot.COLUMN_15, String.valueOf(shot.getBf()));
        values.put(DBContractShots.TableShot.COLUMN_16, String.valueOf(shot.getRc()));
        values.put(DBContractShots.TableShot.COLUMN_17, String.valueOf(shot.getShutter()));
        values.put(DBContractShots.TableShot.COLUMN_18, String.valueOf(shot.getPrettyShutter()));
        values.put(DBContractShots.TableShot.COLUMN_19, String.valueOf(shot.getComment()));
        values.put(DBContractShots.TableShot.COLUMN_20, String.valueOf(shot.getLatitude()));
        values.put(DBContractShots.TableShot.COLUMN_21, String.valueOf(shot.getLongitude()));
        values.put(DBContractShots.TableShot.COLUMN_22, shot.getZoneSytemPushPull());
        values.put(DBContractShots.TableShot.COLUMN_23, String.valueOf(shot.getFilmHolderID()));
        return(values);
    }
    private Shot cursorToShot(Cursor cursor){
        Shot shot = new Shot();
        shot.setId(Long.parseLong(cursor.getString(0)));
        shot.setShotDay(cursor.getString(1));
        shot.setShotTime(cursor.getString(2));
        shot.setPhotoShootId(Long.parseLong(cursor.getString(3)));
        shot.setFilmName(cursor.getString(4));
        shot.setFilmEi(Integer.parseInt(cursor.getString(5)));
        shot.setLensName(cursor.getString(6));
        shot.setLensFocal(Integer.parseInt(cursor.getString(7)));
        shot.setFilterName(cursor.getString(8));
        shot.setCameraName(cursor.getString(9));
        shot.setMeterName(cursor.getString(10));
        shot.setAperture(Double.parseDouble(cursor.getString(11)));
        shot.setBellowsExtension(Integer.parseInt(cursor.getString(12)));
        shot.setMeterRead(Double.parseDouble(cursor.getString(13)));
        shot.setFf(Double.parseDouble(cursor.getString(14)));
        shot.setBf(Double.parseDouble(cursor.getString(15)));
        shot.setRc(Double.parseDouble(cursor.getString(16)));
        shot.setShutter(Double.parseDouble(cursor.getString(17)));
        shot.setPrettyShutter((cursor.getString(18)));
        shot.setComment(cursor.getString(19));
        shot.setLatitude(Double.parseDouble(cursor.getString(20)));
        shot.setLongitude(Double.parseDouble(cursor.getString(21)));
        shot.setZoneSytemPushPull(cursor.getString(22));
        shot.setFilmHolderID(Integer.parseInt(cursor.getString(23)));
        return(shot);
    }

    // CRUD
    public void addShot(Shot shot) {
        ContentValues values = shotToContentValues(shot);
        this.mDb.insert(DBContractShots.TableShot.TABLE_NAME, null, values);
    }
    public Shot getShot(long id) {
        Cursor cursor = mDb.query(DBContractShots.TableShot.TABLE_NAME,
                DBContractShots.TableShot.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        Shot shot = cursorToShot(cursor);
//        Log.d(DBContractShots.TableShot.TAG, TAG_GET_ONE + id + ") " + shot.toString());
        cursor.close();
        return(shot);
    }
    public ArrayList<Shot> getAllShots(){
        Shot shot;
        ArrayList<Shot> shots = new ArrayList<Shot>();
        String query = "SELECT * FROM " + DBContractShots.TableShot.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                shot = cursorToShot(cursor);
                shots.add(shot);
                Log.d(DBContractShots.TableShot.TAG, "id: " + cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
//        Log.d(DBContractShots.TableShot.TAG, TAG_GET_ALL  + shots.toString());
        cursor.close();
        return (shots);
    }
    public int updateShot(Shot shot){
        ContentValues values = shotToContentValues(shot);
        int i = mDb.update(DBContractShots.TableShot.TABLE_NAME,
                values,
                DBContractShots.TableShot.COLUMN_ID+"=?",
                new String [] {String.valueOf(shot.getId())});
//        Log.d(DBContractShots.TableShot.TAG,  TAG_UPDATE + shot.toString());
        return(i);
    }
    public void deleteShot(Shot shot){
        mDb.delete(DBContractShots.TableShot.TABLE_NAME,
                DBContractShots.TableShot.COLUMN_ID + " = ?",
                new String[] {String.valueOf(shot.getId())});
//        Log.d(DBContractShots.TableShot.TAG,  TAG_DELETE + shot.toString());
    }
}
