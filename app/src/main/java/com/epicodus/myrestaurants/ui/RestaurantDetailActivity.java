package com.epicodus.myrestaurants.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.RestaurantPagerAdapter;
import com.epicodus.myrestaurants.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {
    private final int DEFAULT_POSITION = 0;
    private ViewPager mViewPager;
    private RestaurantPagerAdapter adapterViewPager;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESTAURANTS));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, DEFAULT_POSITION);
        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
        adapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), mRestaurants, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
