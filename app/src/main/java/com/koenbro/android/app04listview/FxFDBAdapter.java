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
public class FxFDBAdapter {
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

    public FxFDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public FxFDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        this.mDbHelper.close();
    }

    private ContentValues fxFToContentValues(FxF fxF) {
        ContentValues values = new ContentValues();
        values.put(DBContract.TableFilmFilter.COLUMN_1, String.valueOf(fxF.getFilmId()));
        values.put(DBContract.TableFilmFilter.COLUMN_2, String.valueOf(fxF.getFilterId()));
        values.put(DBContract.TableFilmFilter.COLUMN_3, String.valueOf(fxF.getFactor()));
        return (values);
    }

    private FxF cursorToFxF(Cursor cursor) {
        FxF fxF = new FxF();
        fxF.setId(Long.parseLong(cursor.getString(0)));
        fxF.setFilmId(Long.parseLong(cursor.getString(1)));
        fxF.setFilterId(Long.parseLong(cursor.getString(2)));
        fxF.setFactor(Double.parseDouble(cursor.getString(3)));
        return fxF;
    }

    public void addFxF(FxF fxF) {
        ContentValues values = fxFToContentValues(fxF);
        this.mDb.insert(DBContract.TableFilmFilter.TABLE_NAME, null, values);
    }

    public FxF getFxF(long id) throws SQLException {
        Cursor cursor = this.mDb.query(
                DBContract.TableFilmFilter.TABLE_NAME,
                DBContract.TableFilmFilter.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        FxF fxF;
        if (cursor != null) cursor.moveToFirst();
        fxF = cursorToFxF(cursor);
        cursor.close();
        return (fxF);
    }

    public ArrayList<FxF> getAllFxF() {
        FxF fxF;
        ArrayList<FxF> fxFs = new ArrayList<FxF>();
        String query = "SELECT * FROM " + DBContract.TableFilmFilter.TABLE_NAME;
        Cursor cursor = this.mDb.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    fxF = cursorToFxF(cursor);
                    fxFs.add(fxF);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        return (fxFs);
    }

    public int updateFxF(FxF fxF) {
        ContentValues values = fxFToContentValues(fxF);
        int i = this.mDb.update(DBContract.TableFilmFilter.TABLE_NAME,
                values,
                DBContract.TableFilmFilter.COLUMN_ID + "=?",
                new String[]{String.valueOf(fxF.getId())});// > 0;
        //Log.d(DBContract.TableFilmFilter.TAG, TAG_UPDATE + filmFilter.toString());
        return i;
    }

    public boolean deleteFxF(FxF fxF) {
        return this.mDb.delete(DBContract.TableFilmFilter.TABLE_NAME,
                DBContract.TableFilmFilter.COLUMN_ID + " = ?",
                new String[]{String.valueOf(fxF.getId())}) > 0;
    }

    public boolean deleteAllByFilmID(int filmID) {
        return true;
    }

    public boolean deleteAllByFilterID(int filterID) {
        return true;
    }
    
    
    
}
