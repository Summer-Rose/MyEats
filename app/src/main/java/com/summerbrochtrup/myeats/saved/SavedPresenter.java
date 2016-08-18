package com.summerbrochtrup.myeats.saved;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by summerbrochtrup on 8/17/16.
 */

public class SavedPresenter {
    private SavedView mView;

    public SavedPresenter(SavedView view) {
        mView = view;
    }

    public void onLogoutSelected() {
        FirebaseAuth.getInstance().signOut();
        mView.launchLoginActivity();
    }
}
