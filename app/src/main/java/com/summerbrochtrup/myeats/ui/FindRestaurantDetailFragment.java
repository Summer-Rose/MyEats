package com.summerbrochtrup.myeats.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.summerbrochtrup.myeats.Constants;
import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.util.RestaurantPropertyHelper;

import org.parceler.Parcels;

public class FindRestaurantDetailFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mCategoriesTextView;
    private RatingBar mRatingBar;
    private TextView mWebsiteTextView;
    private ImageView mWebsiteIcon;
    private TextView mPhoneTextView;
    private ImageView mPhoneIcon;
    private TextView mAddressTextView;
    private ImageView mAddressIcon;
    private Button mBottomButton;
    private FloatingActionButton mFAB;
    private SupportMapFragment mMap;
    private Restaurant mRestaurant;
    private CoordinatorLayout mCoordinatorLayout;

    public static FindRestaurantDetailFragment newInstance(Restaurant restaurant) {
        FindRestaurantDetailFragment restaurantDetailFragment = new FindRestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_RESTAURANT, Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RESTAURANT));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_restaurant_detail, container, false);
        bindRegularViews(view);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bindPortraitView(view);
        } else {
            bindLandscapeViews(view);
        }
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteTextView || v == mWebsiteIcon) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRestaurant.getUrl()));
            startActivity(webIntent);
        }
        if (v == mPhoneTextView || v == mPhoneIcon) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse(String.format(getResources().getString(R.string.tel_format),
                            mRestaurant.getPhone())));
            startActivity(phoneIntent);
        }
        if (v == mAddressTextView || v == mAddressIcon) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(getResources().getString(R.string.map_format),
                            Double.toString(mRestaurant.getLatitude()),
                            Double.toString(mRestaurant.getLongitude()),
                            mRestaurant.getName())));
            startActivity(mapIntent);
        }
        if (v == mBottomButton || v == mFAB) {
            saveRestaurant();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        LatLng restaurantMarker = new LatLng(mRestaurant.getLatitude(), mRestaurant.getLongitude());
        map.addMarker(new MarkerOptions().position(restaurantMarker).title(mRestaurant.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantMarker, 15));
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    private void bindRegularViews(View view) {
        mImageView = (ImageView) view.findViewById(R.id.restaurantImageView);
        Picasso.with(view.getContext())
                .load(RestaurantPropertyHelper.getLargeImageUrl(mRestaurant.getImageUrl()))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageView);
        mNameTextView = (TextView) view.findViewById(R.id.restaurantNameTextView);
        mNameTextView.setText(mRestaurant.getName());
        mCategoriesTextView = (TextView) view.findViewById(R.id.categoryTextView);
        mCategoriesTextView.setText(android.text.TextUtils.join(", ", mRestaurant.getCategoryList()));
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingBar.setRating((float)mRestaurant.getRating());
        mFAB = (FloatingActionButton) view.findViewById(R.id.fab);
        mFAB.setOnClickListener(this);
    }

    private void bindPortraitView(View view) {
        mWebsiteTextView = (TextView) view.findViewById(R.id.websiteTextView);
        mWebsiteTextView.setOnClickListener(this);
        mPhoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
        mPhoneTextView.setText(mRestaurant.getPhone());
        mPhoneTextView.setOnClickListener(this);
        mAddressTextView = (TextView) view.findViewById(R.id.addressTextView);
        mAddressTextView.setText(android.text.TextUtils.join(", ", mRestaurant.getAddress()));
        mAddressTextView.setOnClickListener(this);
        mBottomButton = (Button) view.findViewById(R.id.bottomButton);
        mBottomButton.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.mainLayout);
    }

    private void bindLandscapeViews(View view) {
        mWebsiteIcon = (ImageView) view.findViewById(R.id.websiteIcon);
        mWebsiteIcon.setOnClickListener(this);
        mPhoneIcon = (ImageView) view.findViewById(R.id.phoneIcon);
        mPhoneIcon.setOnClickListener(this);
        mAddressIcon = (ImageView) view.findViewById(R.id.addressIcon);
        mAddressIcon.setOnClickListener(this);
    }

    private void saveRestaurant() {
        RestaurantDataSource dataSource = new RestaurantDataSource(getActivity());
        dataSource.create(mRestaurant);
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout,
                String.format(getResources().getString(R.string.saved_snackar), mRestaurant.getName()),
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
