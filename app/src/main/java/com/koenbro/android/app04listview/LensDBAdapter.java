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
public class LensDBAdapter {
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
    public LensDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    public LensDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private ContentValues lensToContentValues (Lens lens){
        ContentValues values = new ContentValues();
        values.put(DBContract.TableLens.COLUMN_1, lens.getLensName());
        values.put(DBContract.TableLens.COLUMN_2, lens.getLensBrand());
        values.put(DBContract.TableLens.COLUMN_3, String.valueOf(lens.getLensFocal()));
        values.put(DBContract.TableLens.COLUMN_4, String.valueOf(lens.getLensFilterDiam()));
        values.put(DBContract.TableLens.COLUMN_5, String.valueOf(lens.getLensApertureOpen()));
        values.put(DBContract.TableLens.COLUMN_6, String.valueOf(lens.getLensApertureClosed()));
        return(values);
    }
    private Lens cursorToLens(Cursor cursor){
        Lens lens = new Lens();
        lens.setId(Long.parseLong(cursor.getString(0)));
        lens.setLensName(cursor.getString(1));
        lens.setLensBrand(cursor.getString(2));
        lens.setLensFocal(Integer.parseInt(cursor.getString(3)));
        lens.setLensFilterDiam(Integer.parseInt(cursor.getString(4)));
        lens.setLensApertureOpen(Double.parseDouble(cursor.getString(5)));
        lens.setLensApertureClosed(Double.parseDouble(cursor.getString(6)));
        return(lens);
    }

    public void addLens(Lens lens) {
        ContentValues values = lensToContentValues(lens);
        mDb.insert(DBContract.TableLens.TABLE_NAME, null, values);
        //Log.d(DBContract.TableLens.TAG, TAG_ADD + lens.toString());
    }
    public Lens getLens(long id) {
        Cursor cursor = mDb.query(DBContract.TableLens.TABLE_NAME,
                DBContract.TableLens.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null) cursor.moveToFirst();
        Lens lens = cursorToLens(cursor);
        //Log.d(DBContract.TableLens.TAG, TAG_GET_ONE + id + ") " + lens.toString());
        cursor.close();
        return(lens);
    }
    public ArrayList<Lens> getAllLenses(){
        Lens lens;
        ArrayList<Lens> lenses = new ArrayList<Lens>();
        String query = "SELECT * FROM " + DBContract.TableLens.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                lens = cursorToLens(cursor);
                lenses.add(lens);
                Log.d(DBContract.TableLens.TAG, "id: "+ cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        //Log.d(DBContract.TableLens.TAG, TAG_GET_ALL  + lenses.toString());
        cursor.close();
        return (lenses);
    }
    public int updateLens(Lens lens){
        ContentValues values = lensToContentValues(lens);
        int i = mDb.update(DBContract.TableLens.TABLE_NAME,
                values,
                DBContract.TableLens.COLUMN_ID+"=?",
                new String [] {String.valueOf(lens.getId())});
        //Log.d(DBContract.TableLens.TAG,  TAG_UPDATE + lens.toString());
        return(i);
    }
    public void deleteLens(Lens lens){
        mDb.delete(DBContract.TableLens.TABLE_NAME,
                DBContract.TableLens.COLUMN_ID + " = ?",
                new String[] {String.valueOf(lens.getId())});
        //Log.d(DBContract.TableLens.TAG,  TAG_DELETE + lens.toString());
    }
}
