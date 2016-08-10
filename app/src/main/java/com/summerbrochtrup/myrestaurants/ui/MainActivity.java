package com.summerbrochtrup.myrestaurants.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mFindRestaurantsButton;
    private Button mSavedRestaurantsButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mSavedRestaurantsButton = (Button) findViewById(R.id.savedRestaurantsButton);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setTitle(Constants.EMPTY_STRING);
        toolbarTitle.setText(String.format(getResources().getString(R.string.welcome_toolbar_title),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
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
}
