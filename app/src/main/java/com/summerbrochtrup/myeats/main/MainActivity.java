package com.summerbrochtrup.myeats.main;

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

import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.ui.FindRestaurantListActivity;
import com.summerbrochtrup.myeats.ui.LoginActivity;
import com.summerbrochtrup.myeats.saved.SavedRestaurantListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {
    private Button mFindRestaurantsButton;
    private Button mSavedRestaurantsButton;
    private TextView mToolbarTitle;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        initializeViews();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mSavedRestaurantsButton = (Button) findViewById(R.id.savedRestaurantsButton);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);
        mPresenter.getUsersName();
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
            mPresenter.onLogoutSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindRestaurantsButton) {
            mPresenter.onFindRestaurantsButtonClick();
        }
        if (v == mSavedRestaurantsButton) {
            mPresenter.onSavedRestaurantsButtonClick();
        }
    }

    @Override
    public void launchSavedRestaurantsActivity() {
        Intent intent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchFindRestaurantsActivity() {
        Intent intent = new Intent(MainActivity.this, FindRestaurantListActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void displayWelcomeMessage(String name) {
        mToolbarTitle.setText(String.format(getResources().getString(R.string.welcome_toolbar_title), name));
    }
}
