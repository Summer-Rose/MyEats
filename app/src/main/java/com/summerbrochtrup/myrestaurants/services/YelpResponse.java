package com.summerbrochtrup.myrestaurants.services;

import com.summerbrochtrup.myrestaurants.models.Restaurant;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
@Generated("org.jsonschema2pojo")
public class YelpResponse {
    private List<Restaurant> businesses = new ArrayList<>();

    public List<Restaurant> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Restaurant> businesses) {
        this.businesses = businesses;
    }

}