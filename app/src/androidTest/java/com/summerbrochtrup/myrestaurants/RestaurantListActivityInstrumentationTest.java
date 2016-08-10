package com.summerbrochtrup.myrestaurants;

import android.support.test.rule.ActivityTestRule;

import com.summerbrochtrup.myrestaurants.ui.FindRestaurantListActivity;

import org.junit.Rule;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNot.not;

public class RestaurantListActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<FindRestaurantListActivity> activityTestRule =
            new ActivityTestRule<>(FindRestaurantListActivity.class);


}
