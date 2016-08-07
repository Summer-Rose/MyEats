package com.summerbrochtrup.myrestaurants.services;

import android.widget.Toast;

import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.models.Restaurant;
import com.summerbrochtrup.myrestaurants.ui.RestaurantListFragment;
import com.summerbrochtrup.myrestaurants.util.RestaurantPropertyHelper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class YelpService implements Callback<YelpResponse> {
    private RestaurantListFragment mFragment;

    public YelpService(RestaurantListFragment fragment) {
        mFragment = fragment;
    }

    public void getRestaurants(String location) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.YELP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        final YelpEndpoints api = retrofit.create(YelpEndpoints.class);

        Call<YelpResponse> call = api.loadRestaurants(Constants.YELP_TERM_FOOD, location);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<YelpResponse> call, Response<YelpResponse> response) {
        if (response.isSuccessful()) {
            YelpResponse apiResponse = response.body();
            for (Restaurant restaurant : apiResponse.getBusinesses()) {
                restaurant.setAddress(restaurant.getLocation().getDisplayAddress());
                restaurant.setCategoryList(RestaurantPropertyHelper.getCategories(restaurant.getCategories()));
            }
            mFragment.setRestaurants(apiResponse.getBusinesses());
        }
    }

    @Override
    public void onFailure(Call<YelpResponse> call, Throwable t) {
        Toast.makeText(mFragment.getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
