package com.summerbrochtrup.myeats.ui;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.summerbrochtrup.myeats.Constants;
import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.util.OnRestaurantSelectedListener;
import org.parceler.Parcels;

public class FindRestaurantListActivity extends AppCompatActivity implements OnRestaurantSelectedListener {
    private Restaurant mRestaurant;
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurants);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRestaurant = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_RESTAURANT));
                mSource = savedInstanceState.getString(Constants.EXTRA_KEY_SOURCE);
                if (mRestaurant != null) {
                    Intent intent = new Intent(this, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANT, Parcels.wrap(mRestaurant));
                    intent.putExtra(Constants.EXTRA_KEY_SOURCE, mSource);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRestaurant != null) {
            outState.putParcelable(Constants.EXTRA_KEY_RESTAURANT, Parcels.wrap(mRestaurant));
            outState.putString(Constants.EXTRA_KEY_SOURCE, mSource);
        }
    }

    @Override
    public void onRestaurantSelected(Restaurant restaurant, String source) {
        mRestaurant = restaurant;
        mSource = source;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}

