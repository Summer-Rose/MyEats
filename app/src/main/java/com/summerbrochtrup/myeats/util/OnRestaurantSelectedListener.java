package com.summerbrochtrup.myeats.util;


import com.summerbrochtrup.myeats.models.Restaurant;

public interface OnRestaurantSelectedListener {
    void onRestaurantSelected(Restaurant restaurant, String source);

}
