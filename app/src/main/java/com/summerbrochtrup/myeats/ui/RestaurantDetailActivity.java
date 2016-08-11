package com.summerbrochtrup.myeats.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.summerbrochtrup.myeats.Constants;
import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.models.Restaurant;

import org.parceler.Parcels;

public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        Restaurant restaurant = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESTAURANT));
        String source = getIntent().getStringExtra(Constants.EXTRA_KEY_SOURCE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (source.equals(Constants.SOURCE_FIND)) {
            ft.replace(R.id.fragmentContainer, FindRestaurantDetailFragment.newInstance(restaurant));
            ft.commit();
        } else {
            ft.replace(R.id.fragmentContainer, SavedRestaurantDetailFragment.newInstance(restaurant));
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}