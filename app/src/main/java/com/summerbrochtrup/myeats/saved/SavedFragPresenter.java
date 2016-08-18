package com.summerbrochtrup.myeats.saved;

import android.content.Context;

import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/17/16.
 */
public class SavedFragPresenter {
    private SavedFragView mView;

    public SavedFragPresenter(SavedFragView view) {
        mView = view;
    }

    public void getRestaurants(Context context) {
        RestaurantDataSource dataSource = new RestaurantDataSource(context);
        ArrayList<Restaurant> restaurants = dataSource.readRestaurants();
        mView.displayRestaurants(restaurants);
    }
}
