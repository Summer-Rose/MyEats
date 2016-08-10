package com.summerbrochtrup.myrestaurants;

import android.os.Build;
import android.widget.ListView;

import com.summerbrochtrup.myrestaurants.ui.FindRestaurantListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class RestaurantListActivityTest {
    private FindRestaurantListActivity activity;
    private ListView mRestaurantListView;


}
