package com.summerbrochtrup.myrestaurants.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.R;
import com.summerbrochtrup.myrestaurants.database.RestaurantDataSource;
import com.summerbrochtrup.myrestaurants.models.Restaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
import com.summerbrochtrup.myrestaurants.util.RestaurantPropertyHelper;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
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
    private ArrayList<Restaurant> mRestaurants;
    private int mPosition;
    private String mSource;

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
            case R.id.action_photo:
                onLaunchCamera();
                break;
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
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            if (v == mBottomButton || v == mFAB) {
                onLaunchCamera();
            }
        } else {
            if (v == mBottomButton || v == mFAB) {
                saveRestaurant();
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(Constants.BITMAP_EXTRA);
            mImageView.setImageBitmap(imageBitmap);
            //encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
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
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mFAB.setImageResource(R.drawable.ic_camera_alt_white_24dp);
            mBottomButton.setText(getResources().getString(R.string.photo_button));
        }
    }

    private void bindLandscapeViews(View view) {
        mWebsiteIcon = (ImageView) view.findViewById(R.id.websiteIcon);
        mWebsiteIcon.setOnClickListener(this);
        mPhoneIcon = (ImageView) view.findViewById(R.id.phoneIcon);
        mPhoneIcon.setOnClickListener(this);
        mAddressIcon = (ImageView) view.findViewById(R.id.addressIcon);
        mAddressIcon.setOnClickListener(this);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mFAB.setImageResource(R.drawable.ic_camera_alt_white_24dp);
        }
    }

    private void saveRestaurant() {
        RestaurantDataSource dataSource = new RestaurantDataSource(getActivity());
        dataSource.create(mRestaurant);
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                //.child(mRestaurant.getPushId())
//                .child(Constants.FIREBASE_CHILD_IMAGEURL);
//        ref.setValue(imageEncoded);
//    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}