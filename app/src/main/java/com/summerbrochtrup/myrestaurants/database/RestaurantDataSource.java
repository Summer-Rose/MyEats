package com.summerbrochtrup.myrestaurants.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
        Log.d("address saving", restaurant.getAddress() + "");
        values.put(SQLiteHelper.COLUMN_ADDRESS, TextUtils.join(", ", restaurant.getAddress()));
        Log.d("address formatted s", TextUtils.join(", ", restaurant.getAddress()));
        values.put(SQLiteHelper.COLUMN_LATITUDE, restaurant.getLatitude());
        values.put(SQLiteHelper.COLUMN_LONGITUDE, restaurant.getLongitude());
        values.put(SQLiteHelper.COLUMN_CATEGORIES, TextUtils.join(",", restaurant.getCategoryList()));
        values.put(SQLiteHelper.COLUMN_YELP_ID, restaurant.getYelpId());
        values.put(SQLiteHelper.COLUMN_SORT_ORDER, restaurant.getSortOrder());
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
                        SQLiteHelper.COLUMN_YELP_ID,
                        SQLiteHelper.COLUMN_SORT_ORDER
                },
                null, //Selection
                null, //selection args
                null, //group by
                null, //having
                SQLiteHelper.COLUMN_SORT_ORDER); //order
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant(getIntFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_RESTAURANT_NAME),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_PHONE),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_URL),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_RATING)),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_IMAGE_URL),
                        Arrays.asList(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_ADDRESS).split(",")),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LATITUDE)),
                        Double.parseDouble(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LONGITUDE)),
                        Arrays.asList(getStringFromColumnName(cursor, SQLiteHelper.COLUMN_CATEGORIES).split(",")),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_YELP_ID),
                        getIntFromColumnName(cursor, SQLiteHelper.COLUMN_SORT_ORDER)
                );
                restaurants.add(restaurant);
            } while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return restaurants;
    }

    public int getNumOfRestaurants() {
        SQLiteDatabase database = open();
        long cnt  = DatabaseUtils.queryNumEntries(database, SQLiteHelper.RESTAURANTS_TABLE);
        database.close();
        return (int) cnt;
    }

    public void updateSortOrder(Restaurant restaurant) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues updateTimerValues = new ContentValues();
        updateTimerValues.put(SQLiteHelper.COLUMN_SORT_ORDER, restaurant.getSortOrder());
        database.update(SQLiteHelper.RESTAURANTS_TABLE,
                updateTimerValues,
                String.format("%s=%d", BaseColumns._ID, restaurant.getDatabaseId()),
                null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void delete(int restaurantId) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        database.delete(SQLiteHelper.RESTAURANTS_TABLE,
                "_Id=" + restaurantId,
                null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
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
