package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {
    private Button mFindRestaurantsButton;
    private TextView mAppNameTextView;
    private Button mSavedRestaurantsButton;
    private Toolbar mToolbar;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        presenter = new MainPresenterImpl(this);
        presenter.getRestaurants("97271");
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);
        mSavedRestaurantsButton = (Button) findViewById(R.id.savedRestaurantsButton);
        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), Constants.FONT_OSTRICH_REGULAR);
        mAppNameTextView.setTypeface(ostrichFont);
        getSupportActionBar().setTitle(String.format(
                getResources().getString(R.string.welcome_toolbar_title),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        mAppNameTextView.setOnClickListener(this);
        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == mFindRestaurantsButton) {
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        }
        if (v == mSavedRestaurantsButton) {
            Intent intent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void logResults(ArrayList<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            Log.d("Restaurant", restaurant.getName());
        }
    }
}
