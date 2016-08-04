package com.epicodus.myrestaurants.find_restaurants;

import android.app.Activity;

import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.services.YelpService;
import com.epicodus.myrestaurants.ui.MainView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by epicodus_staff on 8/4/16.
 */
public class FindRestaurantsPresenterImpl implements FindRestaurantsPresenter {
    private FindRestaurantsView view;
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Activity activity;

    public FindRestaurantsPresenterImpl(FindRestaurantsView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void getRestaurants(String zip) {
        final YelpService service = new YelpService();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //view.handleError
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                restaurants = service.processResults(response);
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        view.displayResults(restaurants);
                    }
                });
            }
        };
        service.findRestaurants(zip, callback);
    }
}
