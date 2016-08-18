package com.summerbrochtrup.myeats.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.views.SavedRestaurantsView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public class SavedRestaurantsPresenter extends BasePresenter<ArrayList<Restaurant>, SavedRestaurantsView> {
    private Context mContext;
    private RestaurantDataSource mDataSource;

    public SavedRestaurantsPresenter(Context context) {
        mContext = context;
    }

    @Override
    protected void updateView() {
        if (model.size() != 0) {
            view().showRestaurants(model);
        }
    }

    @Override
    public void bindView(@NonNull SavedRestaurantsView view) {
        super.bindView(view);
        mDataSource = new RestaurantDataSource(mContext);
        getRestaurants();
    }

    private void getRestaurants() {
        model = mDataSource.readRestaurants();
        view().showRestaurants(model);
    }

    public void setSortOrder() {
        Log.d("set sort order", "called");
        for (Restaurant restaurant : model) {
            Log.d("name", restaurant.getName() + " " + model.indexOf(restaurant));
            restaurant.setSortOrder(model.indexOf(restaurant));
            mDataSource = new RestaurantDataSource(mContext);
            mDataSource.updateSortOrder(restaurant);
        }
    }
 }
