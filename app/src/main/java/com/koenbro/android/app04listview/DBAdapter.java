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

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
        }

        /**
         * The app works without seeding the db (prepopulating one row in each table) but it crashes
         * if you try to calculate an exposure, as it tries to pull info from the db.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.setForeignKeyConstraintsEnabled(true);
            db.execSQL(DBContract.TableCamera.CREATE_TABLE);
            db.execSQL(DBContract.TableFilm.CREATE_TABLE);
            db.execSQL(DBContract.TableFilter.CREATE_TABLE);
            db.execSQL(DBContract.TableLens.CREATE_TABLE);
            db.execSQL(DBContract.TableMeter.CREATE_TABLE);
            //db.execSQL(DBContract.TableShot.CREATE_TABLE);
            db.execSQL(DBContract.TableFilmFilter.CREATE_TABLE);
            seedDB(db);
        }

        public void seedDB(SQLiteDatabase db) {
            db.execSQL(DBContract.TableCamera.SEED);
            db.execSQL(DBContract.TableFilm.SEED);
            db.execSQL(DBContract.TableFilter.SEED);
            db.execSQL(DBContract.TableLens.SEED);
            db.execSQL(DBContract.TableMeter.SEED);
            db.execSQL(DBContract.TableFilmFilter.SEED);
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
            //db.execSQL(DBContract.TableShot.DELETE_TABLE);
            db.execSQL(DBContract.TableFilmFilter.DELETE_TABLE);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }
}
