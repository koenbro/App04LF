package com.koenbro.android.app04listview;

import android.provider.BaseColumns;

/**
 * @author laszlo
 * @date 1/18/16.
 */
public class DBContractShots {
    public static final String DB_NAME = "lfshots.sqlite";
    public static final int DB_VERSION = 3;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";

    public static abstract class TableShot implements BaseColumns {
        public static final String TABLE_NAME = "shots";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_1 = "day";
        public static final String COLUMN_2 = "time";
        public static final String COLUMN_3 = "photo_shoot";
        public static final String COLUMN_4 = "film_name";
        public static final String COLUMN_5 = "film_ei";
        public static final String COLUMN_6 = "lens_name";
        public static final String COLUMN_7 = "lens_focal";
        public static final String COLUMN_8 = "filter_name";
        public static final String COLUMN_9 = "camera_name";
        public static final String COLUMN_10 = "meter_name";
        public static final String COLUMN_11 = "aperture";
        public static final String COLUMN_12 = "bellows_extension";
        public static final String COLUMN_13 = "meter_read_ev";
        public static final String COLUMN_14 = "filter_factor";
        public static final String COLUMN_15 = "bellows_factor";
        public static final String COLUMN_16 = "rc";
        public static final String COLUMN_17 = "shutter";
        public static final String COLUMN_18 = "pretty_shutter";
        public static final String COLUMN_19 = "comment";
        public static final String COLUMN_20 = "latitude";
        public static final String COLUMN_21 = "longitude";
        public static final String COLUMN_22 = "zone_system_development"; //string
        public static final String COLUMN_23 = "film_holder_id"; //int


        public static final String[] COLUMNS = {
                COLUMN_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5,
                COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9, COLUMN_10,
                COLUMN_11, COLUMN_12, COLUMN_13, COLUMN_14, COLUMN_15,
                COLUMN_16, COLUMN_17, COLUMN_18, COLUMN_19, COLUMN_20,
                COLUMN_21, COLUMN_22, COLUMN_23};
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
                        COMMA_SEP + COLUMN_11 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_12 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_13 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_14 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_15 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_16 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_17 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_18 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_19 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_20 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_21 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_22 + TEXT_TYPE +
                        COMMA_SEP + COLUMN_23 + TEXT_TYPE +
                        " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String TAG = "ShotsDB.tag";
    }

}
