package com.koenbro.android.app04listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by laszlo on 10/27/14.
 */
public class CameraDBAdapter {
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

    public CameraDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    public CameraDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        this.mDbHelper.close();
    }

    private Camera cursorToCamera(Cursor cursor) {
        Camera camera = new Camera();
        camera.setId(Long.parseLong(cursor.getString(0)));
        camera.setCameraName(cursor.getString(1));
        camera.setBellowsMin(Integer.parseInt(cursor.getString(2)));
        camera.setBellowsMax(Integer.parseInt(cursor.getString(3)));
        return camera;
    }
    private ContentValues cameraToContentValues (Camera camera){
        ContentValues values = new ContentValues();
        values.put(DBContract.TableCamera.COLUMN_1, camera.getCameraName());
        values.put(DBContract.TableCamera.COLUMN_2, String.valueOf(camera.getBellowsMin()));
        values.put(DBContract.TableCamera.COLUMN_3, String.valueOf(camera.getBellowsMax()));
        return (values);
    }

    public void addCamera(Camera camera) {
        ContentValues values = cameraToContentValues(camera);
        this.mDb.insert(DBContract.TableCamera.TABLE_NAME, null, values);
//        Log.d(DBContract.TableCamera.TAG, "add camera " + camera.toString());
    }
    public Camera getCamera(long id) throws SQLException {
        Cursor cursor = this.mDb.query(
                DBContract.TableCamera.TABLE_NAME,
                DBContract.TableCamera.COLUMNS,
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        Camera camera;
        if (cursor != null)  cursor.moveToFirst();
        camera = cursorToCamera(cursor);
//            Log.d(DBContract.TableCamera.TAG, "getCamera(" + id + ") " + camera.toString());
        cursor.close();
        return(camera);
    }
    public ArrayList<Camera> getAllCameras(){
        ArrayList<Camera> cameras = new ArrayList<Camera>();
        String query = "SELECT * FROM " + DBContract.TableCamera.TABLE_NAME;
        Cursor cursor = this.mDb.rawQuery( query, null);
        Camera camera;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    camera = cursorToCamera(cursor);
                    //Log.d(DBContract.TableCamera.TAG, "id: " + cursor.getString(0));
                    cameras.add(camera);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
//        Log.d(DBContract.TableCamera.TAG, "get all cameras() " + cameras.toString());
        return (cameras);
    }
    public boolean updateCamera(Camera camera){
        ContentValues values = cameraToContentValues(camera);
        return this.mDb.update(DBContract.TableCamera.TABLE_NAME,
                values,
                DBContract.TableCamera.COLUMN_ID+"=?",
                new String [] {String.valueOf(camera.getId())}) > 0;
        //Log.d(DBContract.TableCamera.TAG, "updated camera " + camera.toString());
    }
    public boolean deleteCamera(Camera camera){
        return this.mDb.delete(DBContract.TableCamera.TABLE_NAME,
                DBContract.TableCamera.COLUMN_ID+" = ?",
                new String[] {String.valueOf(camera.getId())}) > 0;
        //Log.d(DBContract.TableCamera.TAG, "delete camera " + camera.toString());
    }
}
