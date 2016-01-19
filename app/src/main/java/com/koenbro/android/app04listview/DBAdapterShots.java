package com.koenbro.android.app04listview;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by laszlo on 10/27/14.
 */
public class DBAdapterShots {
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapterShots(Context ctx) {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBContractShots.DB_NAME, null, DBContractShots.DB_VERSION);
        }

        /**
         * The app works without seeding the db (prepopulating one row in each table) but it crashes
         * if you try to calculate an exposure, as it tries to pull info from the db.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBContractShots.TableShot.CREATE_TABLE);
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DatabaseHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL(DBContractShots.TableShot.DELETE_TABLE);
            onCreate(db);
        }
    }

    public DBAdapterShots open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }
}
