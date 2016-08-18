package com.summerbrochtrup.myeats.views;

import com.summerbrochtrup.myeats.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public interface SavedRestaurantsView {
    void showRestaurants(ArrayList<Restaurant> restaurants);
}
