package com.summerbrochtrup.myrestaurants.util;


import com.summerbrochtrup.myrestaurants.models.Restaurant;

import java.util.List;

public interface OnRestaurantSelectedListener {
    void onRestaurantSelected(Integer position, List<Restaurant> restaurants, String source);

}
