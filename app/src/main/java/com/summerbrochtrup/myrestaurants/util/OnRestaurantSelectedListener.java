package com.summerbrochtrup.myrestaurants.util;


import com.summerbrochtrup.myrestaurants.models.Restaurant;

import java.util.ArrayList;

public interface OnRestaurantSelectedListener {
    void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source);

}
