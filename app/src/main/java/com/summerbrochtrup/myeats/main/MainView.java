package com.summerbrochtrup.myeats.main;

/**
 * Created by summerbrochtrup on 8/17/16.
 */
public interface MainView {
    void launchSavedRestaurantsActivity();
    void launchFindRestaurantsActivity();
    void launchLoginActivity();
    void displayWelcomeMessage(String name);
}
