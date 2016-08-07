package com.summerbrochtrup.myrestaurants.services;

import com.summerbrochtrup.myrestaurants.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public interface YelpEndpoints {

    @GET(Constants.YELP_GET_SEARCH)
    Call<YelpResponse> loadRestaurants(@Query(Constants.YELP_QUERY_TERM) String term,
                                       @Query(Constants.YELP_QUERY_LOCATION) String location);
}
