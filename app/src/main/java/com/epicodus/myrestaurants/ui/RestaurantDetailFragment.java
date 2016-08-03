package com.epicodus.myrestaurants.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import android.widget.Toast;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private ImageView mImageLabel;
    private TextView mNameLabel;
    private TextView mCategoriesLabel;
    private RatingBar mRatingBar;
    private TextView mWebsiteLabel;
    private TextView mPhoneLabel;
    private TextView mAddressLabel;
    private Button mSaveRestaurantButton;
    private Restaurant mRestaurant;
    private ArrayList<Restaurant> mRestaurants;
    private int mPosition;
    private String mSource;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private SupportMapFragment mMap;

    private void bindViews(View view) {
        mImageLabel = (ImageView) view.findViewById(R.id.restaurantImageView);
        mNameLabel = (TextView) view.findViewById(R.id.restaurantNameTextView);
        mCategoriesLabel = (TextView) view.findViewById(R.id.categoryTextView);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mWebsiteLabel = (TextView) view.findViewById(R.id.websiteTextView);
        mPhoneLabel = (TextView) view.findViewById(R.id.phoneTextView);
        mAddressLabel = (TextView) view.findViewById(R.id.addressTextView);
        mSaveRestaurantButton = (Button) view.findViewById(R.id.saveRestaurantButton);
    }

    public static RestaurantDetailFragment newInstance(ArrayList<Restaurant> restaurants, Integer position, String source) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(restaurants));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurants = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RESTAURANTS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRestaurant = mRestaurants.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        bindViews(view);

        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mToolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
            mToolbar.inflateMenu(R.menu.menu_main);
            collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.main_collapsing);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        }

        if (!mRestaurant.getImageUrl().contains(Constants.HTTP_FILTER)) {
            try {
                Bitmap image = decodeFromFirebaseBase64(mRestaurant.getImageUrl());
                mImageLabel.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(view.getContext())
                    .load(mRestaurant.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mImageLabel);
        }

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveRestaurantButton.setVisibility(View.GONE);
        } else {
            mSaveRestaurantButton.setOnClickListener(this);
        }

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        mRatingBar.setRating((float)mRestaurant.getRating());
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddress()));
        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        return view;
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        }
        else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(Constants.BITMAP_EXTRA);
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mRestaurant.getPushId())
                .child(Constants.FIREBASE_CHILD_IMAGEURL);
        ref.setValue(imageEncoded);
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse(String.format(getResources().getString(R.string.tel_format),
                            mRestaurant.getPhone())));
            startActivity(phoneIntent);
        }

        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(getResources().getString(R.string.map_format),
                            Double.toString(mRestaurant.getLatitude()),
                            Double.toString(mRestaurant.getLongitude()),
                            mRestaurant.getName())));
            startActivity(mapIntent);
        }

        if (v == mSaveRestaurantButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);
            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRestaurant.setPushId(pushId);
            pushRef.setValue(mRestaurant);
            Toast.makeText(getContext(), getResources().getString(R.string.saved_toast), Toast.LENGTH_SHORT).show();
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
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
    }
}