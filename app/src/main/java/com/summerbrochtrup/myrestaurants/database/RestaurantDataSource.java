package com.summerbrochtrup.myrestaurants.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.summerbrochtrup.myrestaurants.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class RestaurantDataSource {
    private Context mContext;
    private SQLiteHelper mSQLiteHelper;

    public RestaurantDataSource(Context context) {
        mContext = context;
        mSQLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase database = mSQLiteHelper.getReadableDatabase();
        database.close();
    }

    private SQLiteDatabase open() {
        return mSQLiteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Restaurant restaurant) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_RESTAURANT_NAME, restaurant.getName());
        values.put(SQLiteHelper.COLUMN_PHONE, restaurant.getPhone());
        values.put(SQLiteHelper.COLUMN_URL, restaurant.getUrl());
        values.put(SQLiteHelper.COLUMN_RATING, restaurant.getRating());
        values.put(SQLiteHelper.COLUMN_IMAGE_URL, restaurant.getImageUrl());
        values.put(SQLiteHelper.COLUMN_ADDRESS, TextUtils.join(",", restaurant.getAddress()));
        values.put(SQLiteHelper.COLUMN_LATITUDE, restaurant.getLatitude());
        values.put(SQLiteHelper.COLUMN_LONGITUDE, restaurant.getLongitude());
        values.put(SQLiteHelper.COLUMN_CATEGORIES, TextUtils.join(",", restaurant.getCategoryList()));
        values.put(SQLiteHelper.COLUMN_YELP_ID, restaurant.getYelpId());
        long restaurantID = database.insert(SQLiteHelper.RESTAURANTS_TABLE, null, values);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Restaurant> readRestaurants() {
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                SQLiteHelper.RESTAURANTS_TABLE,
                new String [] {
                        BaseColumns._ID,
                        SQLiteHelper.COLUMN_RESTAURANT_NAME,
                        SQLiteHelper.COLUMN_PHONE,
                        SQLiteHelper.COLUMN_URL,
                        SQLiteHelper.COLUMN_RATING,
                        SQLiteHelper.COLUMN_IMAGE_URL,
                        SQLiteHelper.COLUMN_ADDRESS,
                        SQLiteHelper.COLUMN_LATITUDE,
                        SQLiteHelper.COLUMN_LONGITUDE,
                        SQLiteHelper.COLUMN_CATEGORIES,
                        SQLiteHelper.COLUMN_YELP_ID
                },
                null, //Selection
                null, //selection args
                null, //group by
                null, //having
                null); //order
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant(getIntFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_RESTAURANT_NAME),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_PHONE),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_URL),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_RATING)),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_IMAGE_URL),
                        Arrays.asList(TextUtils.split(",", getStringFromColumnName(cursor, SQLiteHelper.COLUMN_ADDRESS))),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LATITUDE)),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LONGITUDE)),
                        Arrays.asList(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_CATEGORIES).split(",")),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_YELP_ID)
                );
                restaurants.add(restaurant);
            } while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return restaurants;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }
}
