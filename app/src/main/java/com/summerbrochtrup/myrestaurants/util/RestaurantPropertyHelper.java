package com.summerbrochtrup.myrestaurants.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class RestaurantPropertyHelper {

    public static String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }

    public static List<String> getCategories(List<List<String>> categories) {
        List<String> newCategories = new ArrayList<>();
        for (int y = 0; y < categories.size(); y++) {
            newCategories.add(categories.get(y).get(0));
        }
        return newCategories;
    }
}
