package com.koenbro.android.app04listview;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Get all gear items from database
 *
 * @author laszlo
 * @date 2/25/15.
 */
public class Gear {
    private DBAdapter db;
    private FilmDBAdapter filmDB;
    private FilterDBAdapter filterDB;
    private LensDBAdapter lensDB;
    private MeterDBAdapter meterDB;
    private CameraDBAdapter cameraDB;
    private ArrayList<Film> allFilms;
    private ArrayList<Filter> allFilters;
    private ArrayList<Lens> allLenses;
    private ArrayList<Meter> allMeters;
    private ArrayList<Camera> allCameras;

    public Gear() {
        db = new DBAdapter(ApplicationContextProvider.getContext());
        filmDB = new FilmDBAdapter(ApplicationContextProvider.getContext());
        filterDB = new FilterDBAdapter(ApplicationContextProvider.getContext());
        lensDB = new LensDBAdapter(ApplicationContextProvider.getContext());
        meterDB = new MeterDBAdapter(ApplicationContextProvider.getContext());
        cameraDB = new CameraDBAdapter(ApplicationContextProvider.getContext());
        //tryDatabase();
    }

    public void tryDatabase(){
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
    }

    public ArrayList<Film> getAllFilms() {
        filmDB.open();
        allFilms = filmDB.getAllFilms();
        filmDB.close();
        return allFilms;
    }
    public ArrayList<Filter> getAllFilters() {
        filterDB.open();
        allFilters = filterDB.getAllFilters();
        filterDB.close();
        return allFilters;
    }
    public ArrayList<Lens> getAllLenses() {
        lensDB.open();
        allLenses = lensDB.getAllLenses();
        lensDB.close();
        return allLenses;
    }
    public ArrayList<Meter> getAllMeters() {
        meterDB.open();
        allMeters = meterDB.getAllMeters();
        meterDB.close();
        return allMeters;
    }
    public ArrayList<Camera> getAllCameras() {
        cameraDB.open();
        allCameras = cameraDB.getAllCameras();
        cameraDB.close();
        return allCameras;
    }

}
