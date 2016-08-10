package com.summerbrochtrup.myrestaurants.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.R;
import com.google.firebase.auth.FirebaseAuth;
import com.summerbrochtrup.myrestaurants.models.Restaurant;
import com.summerbrochtrup.myrestaurants.util.OnRestaurantSelectedListener;

import org.parceler.Parcels;

public class SavedRestaurantListActivity extends AppCompatActivity implements OnRestaurantSelectedListener {
    private Restaurant mRestaurant;
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            inflater.inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SavedRestaurantListActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
}
