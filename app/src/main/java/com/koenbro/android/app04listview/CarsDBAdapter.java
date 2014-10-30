package com.koenbro.android.app04listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by laszlo on 10/27/14.
 */
public class CarsDBAdapter {
    public static final String ROW_ID = "_id";
    public static final String NAME = "name";
    public static final String MODEL = "model";
    public static final String YEAR = "year";

    private static final String DATABASE_TABLE = "cars";

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

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx
     *            the Context within which to work
     */
    public CarsDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the cars database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public CarsDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

    /**
     * Create a new car. If the car is successfully created return the new
     * rowId for that car, otherwise return a -1 to indicate failure.
     *
     * @param name
     * @param model
     * @param year
     * @return rowId or -1 if failed
     */
    public long createCar(String name, String model, String year){
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, name);
        initialValues.put(MODEL, model);
        initialValues.put(YEAR, year);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the car with the given rowId
     *
     * @param rowId
     * @return true if deleted, false otherwise
     */
    public boolean deleteCar(long rowId) {

        return this.mDb.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }

    /**
     * Return a Cursor over the list of all cars in the database
     *
     * @return Cursor over all cars
     */
    public Cursor getAllCars() {

        return this.mDb.query(DATABASE_TABLE, new String[] { ROW_ID,
                NAME, MODEL, YEAR }, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the car that matches the given rowId
     * @param rowId
     * @return Cursor positioned to matching car, if found
     * @throws SQLException if car could not be found/retrieved
     */
    public Cursor getCar(long rowId) throws SQLException {

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, new String[] { ROW_ID, NAME,
                        MODEL, YEAR}, ROW_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the car.
     *
     * @param rowId
     * @param name
     * @param model
     * @param year
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateCar(long rowId, String name, String model,
                             String year){
        ContentValues args = new ContentValues();
        args.put(NAME, name);
        args.put(MODEL, model);
        args.put(YEAR, year);

        return this.mDb.update(DATABASE_TABLE, args, ROW_ID + "=" + rowId, null) >0;
    }

}