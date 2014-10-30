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
public class MeterDBAdapter {
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
    public MeterDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    public MeterDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private ContentValues meterToContentValues (Meter meter){
        ContentValues values = new ContentValues();
        values.put(DBContract.TableMeter.COLUMN_1, meter.getMeterName());
        values.put(DBContract.TableMeter.COLUMN_2, meter.getMeterResultFormat());
        values.put(DBContract.TableMeter.COLUMN_3, String.valueOf(meter.getMeterISO()));
        values.put(DBContract.TableMeter.COLUMN_4, String.valueOf(meter.getMeterCompensation()));

        return(values);
    }
    private Meter cursorToMeter(Cursor cursor){
        Meter meter = new Meter();
        meter.setId(Long.parseLong(cursor.getString(0)));
        meter.setMeterName(cursor.getString(1));
        meter.setMeterResultFormat(cursor.getString(2));
        meter.setMeterISO(Integer.parseInt(cursor.getString(3)));
        meter.setMeterCompensation(Double.parseDouble(cursor.getString(4)));
        return(meter);
    }

    public void addMeter(Meter meter) {
        ContentValues values = meterToContentValues(meter);
        mDb.insert(DBContract.TableMeter.TABLE_NAME, null, values);
//        Log.d(DBContract.TableMeter.TAG, TAG_ADD + meter.toString());
    }
    public Meter getMeter(long id) {
        Cursor cursor = mDb.query(DBContract.TableMeter.TABLE_NAME,
                DBContract.TableMeter.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        Meter meter = cursorToMeter(cursor);
//        Log.d(DBContract.TableMeter.TAG, TAG_GET_ONE + id + ") " + meter.toString());
        cursor.close();
        return(meter);
    }
    public ArrayList<Meter> getAllMeters(){
        Meter meter;
        ArrayList<Meter> meters = new ArrayList<Meter>();
        String query = "SELECT * FROM " + DBContract.TableMeter.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                meter = cursorToMeter(cursor);
                meters.add(meter);
                Log.d(DBContract.TableMeter.TAG, "id: "+ cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
//        Log.d(DBContract.TableMeter.TAG, TAG_GET_ALL  + meters.toString());
        cursor.close();
        return (meters);
    }
    public int updateMeter(Meter meter){
        ContentValues values = meterToContentValues(meter);
        int i = mDb.update(DBContract.TableMeter.TABLE_NAME,
                values,
                DBContract.TableMeter.COLUMN_ID+"=?",
                new String [] {String.valueOf(meter.getId())});
//        Log.d(DBContract.TableMeter.TAG,  TAG_UPDATE + meter.toString());
        return(i);
    }
    public void deleteMeter(Meter meter){
        mDb.delete(DBContract.TableMeter.TABLE_NAME,
                DBContract.TableMeter.COLUMN_ID + " = ?",
                new String[] {String.valueOf(meter.getId())});
//        Log.d(DBContract.TableMeter.TAG,  TAG_DELETE + meter.toString());
    }
}
