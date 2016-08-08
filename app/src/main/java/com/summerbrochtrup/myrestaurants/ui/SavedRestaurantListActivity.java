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

import java.util.ArrayList;

public class SavedRestaurantListActivity extends AppCompatActivity implements OnRestaurantSelectedListener {
    private Integer mPosition;
    private ArrayList<Restaurant> mRestaurants;
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mRestaurants = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_RESTAURANTS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);
                if (mPosition != null && mRestaurants != null) {
                    Intent intent = new Intent(this, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
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
        if (mPosition != null && mRestaurants != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }

    @Override
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source) {
        mPosition = position;
        mRestaurants = restaurants;
        mSource = source;
    }
}
