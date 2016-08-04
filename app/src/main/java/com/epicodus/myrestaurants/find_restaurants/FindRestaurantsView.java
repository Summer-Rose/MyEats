package com.epicodus.myrestaurants.find_restaurants;

import com.epicodus.myrestaurants.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by epicodus_staff on 8/4/16.
 */
public interface FindRestaurantsView {
    void displayResults(ArrayList<Restaurant> restaurants);
}
