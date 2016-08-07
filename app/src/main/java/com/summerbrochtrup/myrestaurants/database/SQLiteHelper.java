package com.summerbrochtrup.myrestaurants.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myrestaurants.db";
    private static final int DB_VERSION = 1;
    public static final String RESTAURANTS_TABLE = "RESTAURANTS";
    public static final String COLUMN_RESTAURANT_NAME = "NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_URL = "URL";
    public static final String COLUMN_RATING = "RATING";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_CATEGORIES = "CATEGORIES";
    public static final String COLUMN_YELP_ID = "YELP_ID";
    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private static String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + RESTAURANTS_TABLE
            + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESTAURANT_NAME + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_URL + " TEXT, "
            + COLUMN_RATING + " TEXT, "
            + COLUMN_IMAGE_URL + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_LATITUDE + " TEXT, "
            + COLUMN_LONGITUDE + " TEXT, "
            + COLUMN_CATEGORIES + " TEXT, "
            + COLUMN_YELP_ID + " TEXT)";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
