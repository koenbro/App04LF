package com.koenbro.android.app04listview;

import android.provider.BaseColumns;

/**
 * Created by laszlo on 10/23/14.
 */
public final class DBContract {
    public static final String DB_NAME = "lfgear.sqlite";
    public static final int DB_VERSION = 58;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";

    // Empty constructor prevents accidental instantiation of the contract class.
    private DBContract() {
    }

    public static abstract class TableCamera implements BaseColumns {
        public static final String TABLE_NAME = "cameras";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "camera_name";
        public static final String COLUMN_2 = "bellows_min"; //bellows extension minimal
        public static final String COLUMN_3 = "bellows_max";
        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3};
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "CameraDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, 'Tachihara', '65', '330')";
    }

    public static abstract class TableFilm implements BaseColumns {
        public static final String TABLE_NAME = "films";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "film_name";
        public static final String COLUMN_2 = "film_type";
        public static final String COLUMN_3 = "film_ei";
        public static final String COLUMN_4 = "film_rc1";
        public static final String COLUMN_5 = "film_rc2";
        public static final String COLUMN_6 = "film_rc3";
        public static final String COLUMN_7 = "film_rc4";
        public static final String COLUMN_8 = "film_rc5";
        public static final String COLUMN_9 = "film_rc6";
        public static final String COLUMN_10 = "film_rc7";
        //    private static final String COLUMN_
        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4,
                COLUMN_5, COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9, COLUMN_10};
        //ATTENTION These ^ need to be changed to expand cursor
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_4 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_5 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_6 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_7 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_8 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_9 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_10 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "FilmDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, 'TMX100', 'bw', '100', '1.26', '1.26', '1.3', '1.4', '1.4', '1.58', '2.0')";
    }

    public static abstract class TableFilter implements BaseColumns {
        public static final String TABLE_NAME = "filters";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "filter_name";
        public static final String COLUMN_2 = "filter_for_bw"; //boolean
        public static final String COLUMN_3 = "filter_for_color"; //boolean
        public static final String COLUMN_4 = "filter_factor_bw";
        public static final String COLUMN_5 = "filter_factor_color";
        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4,
                COLUMN_5};
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_4 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_5 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "FilterDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, '#11', 'true', 'false', '1.3', '1' )";
    }

    public static abstract class TableLens implements BaseColumns {
        public static final String TABLE_NAME = "lenses";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "lens_name";
        public static final String COLUMN_2 = "lens_brand";
        public static final String COLUMN_3 = "lens_focal";
        public static final String COLUMN_4 = "lens_filter_diam";
        public static final String COLUMN_5 = "lens_aperture_open";
        public static final String COLUMN_6 = "lens_aperture_closed";
        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4,
                COLUMN_5, COLUMN_6};
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_4 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_5 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_6 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "LensDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, '135', 'Rodenstock', '135', '52', '5.6', '64')";
    }

    public static abstract class TableMeter implements BaseColumns {
        public static final String TABLE_NAME = "meters";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "meter_name";
        public static final String COLUMN_2 = "meter_results_format"; //bellows extension minimal
        public static final String COLUMN_3 = "meter_iso";
        public static final String COLUMN_4 = "meter_compensation";
        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4};
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_4 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "MeterDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, 'Pentax', 'EV', '200', '0.67' )";
    }

    public static abstract class TableFilmFilter implements BaseColumns {
        public static final String TABLE_NAME = "film_filter_factors";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "film_id";
        public static final String COLUMN_2 = "film_name";
        public static final String COLUMN_3 = "film_type";
        public static final String COLUMN_4 = "filter_id";
        public static final String COLUMN_5 = "filter_name";
        public static final String COLUMN_6 = "ff_value";
        public static final String COLUMN_7 = "specific";

        public static final String[] COLUMNS = {COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3,
                COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7};
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        COMMA_SEP + COLUMN_1 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_2 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_3 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_4 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_5 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_6 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_7 + TEXT_TYPE +
//                        COMMA_SEP + "FOREIGN KEY(" + COLUMN_1 +
//                        ") REFERENCES " + TableFilm.TABLE_NAME + " (" + TableFilm.COLUMN_ID + ")" +
//                        COMMA_SEP + "FOREIGN KEY(" + COLUMN_2 +
//                        ") REFERENCES " + TableFilter.TABLE_NAME + " (" + TableFilter.COLUMN_ID + ")" +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "FilmFilterDB.tag";
        public static final String SEED =
                "INSERT INTO " + TABLE_NAME + " VALUES (" +
                        "null, '1', 'TMX100', 'bw', '1', '#11', '1', 'false' )";
    }


}
