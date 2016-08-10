package com.summerbrochtrup.myrestaurants.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.R;
import com.summerbrochtrup.myrestaurants.models.Restaurant;

import org.parceler.Parcels;

/**
 * Created by epicodus_staff on 8/9/16.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail_new);
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



//    private void initializeView() {
//
//    }
////
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_timer_activity, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_logout:
//                return true;
//            default:
//                return true;
//        }
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}