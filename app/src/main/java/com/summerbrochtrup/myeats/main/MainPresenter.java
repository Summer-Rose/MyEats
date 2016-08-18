package com.summerbrochtrup.myeats.main;

import com.google.firebase.auth.FirebaseAuth;
import com.summerbrochtrup.myeats.main.MainView;

/**
 * Created by summerbrochtrup on 8/17/16.
 */
public class MainPresenter {
    private MainView mView;

    public MainPresenter(MainView view) {
        mView =  view;
    }

    public void onSavedRestaurantsButtonClick() {
        mView.launchSavedRestaurantsActivity();
    }

    public void onFindRestaurantsButtonClick() {
        mView.launchFindRestaurantsActivity();
    }

    public void onLogoutSelected() {
        FirebaseAuth.getInstance().signOut();
        mView.launchLoginActivity();
    }

    public void getUsersName() {
        mView.displayWelcomeMessage(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }
}
