package com.summerbrochtrup.myeats.saved;

import com.summerbrochtrup.myeats.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/17/16.
 */
public interface SavedFragView {
    void displayRestaurants(ArrayList<Restaurant> restaurants);
}
