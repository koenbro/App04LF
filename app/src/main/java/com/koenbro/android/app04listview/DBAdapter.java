package com.koenbro.android.app04listview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by laszlo on 10/27/14.
 */
public class DBAdapter {
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;



    public DBAdapter(Context ctx) {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context) {
            super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBContract.TableCamera.CREATE_TABLE);
            db.execSQL(DBContract.TableFilm.CREATE_TABLE);
            db.execSQL(DBContract.TableFilter.CREATE_TABLE);
            db.execSQL(DBContract.TableLens.CREATE_TABLE);
            db.execSQL(DBContract.TableMeter.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DatabaseHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL(DBContract.TableCamera.DELETE_TABLE);
            db.execSQL(DBContract.TableFilm.DELETE_TABLE);
            db.execSQL(DBContract.TableFilter.DELETE_TABLE);
            db.execSQL(DBContract.TableLens.DELETE_TABLE);
            db.execSQL(DBContract.TableMeter.DELETE_TABLE);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.DBHelper.close();
    }
}
