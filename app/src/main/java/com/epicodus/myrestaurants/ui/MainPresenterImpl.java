package com.epicodus.myrestaurants.ui;

import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by epicodus_staff on 8/4/16.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;

    ArrayList<Restaurant> restaurants = new ArrayList<>();

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }
    @Override
    public void getRestaurants(String zip) {
        final YelpService service = new YelpService();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainView.logResults(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                restaurants = service.processResults(response);
                mainView.logResults(restaurants);
            }
        };
        service.findRestaurants(zip, callback);
    }
}
