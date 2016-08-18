package com.summerbrochtrup.myeats.presenters;

import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.views.RestaurantView;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public class RestaurantPresenter extends BasePresenter<Restaurant, RestaurantView> {

    @Override
    protected void updateView() {
        view().setRestaurantValues(model);
    }
}
