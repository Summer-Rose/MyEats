package com.summerbrochtrup.myrestaurants.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.summerbrochtrup.myrestaurants.Constants;
import com.summerbrochtrup.myrestaurants.R;
import com.summerbrochtrup.myrestaurants.database.RestaurantDataSource;
import com.summerbrochtrup.myrestaurants.models.Restaurant;
import com.summerbrochtrup.myrestaurants.ui.RestaurantDetailActivity;
import com.summerbrochtrup.myrestaurants.ui.RestaurantDetailFragment;
import com.summerbrochtrup.myrestaurants.util.ItemTouchHelperAdapter;
import com.summerbrochtrup.myrestaurants.util.ItemTouchHelperViewHolder;
import com.summerbrochtrup.myrestaurants.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myrestaurants.util.OnStartDragListener;
import com.summerbrochtrup.myrestaurants.util.RestaurantPropertyHelper;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class SavedRestaurantListAdapter extends RecyclerView.Adapter<SavedRestaurantListAdapter.RestaurantViewHolder> implements ItemTouchHelperAdapter {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;
    private int mOrientation;
    private RestaurantDataSource mDataSource;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    public SavedRestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants, OnStartDragListener onStartDragListener, OnRestaurantSelectedListener restaurantSelectedListener) {
        mContext = context;
        mRestaurants = restaurants;
        mOnStartDragListener = onStartDragListener;
        mOnRestaurantSelectedListener = restaurantSelectedListener;
        mDataSource = new RestaurantDataSource(context);
    }

    @Override
    public SavedRestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_drag, parent, false);
        final RestaurantViewHolder viewHolder = new RestaurantViewHolder(view, mRestaurants, mOnRestaurantSelectedListener);
        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
        viewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                    mContext.startActivity(intent);
                }
            }
        });
        return viewHolder;
    }

    private void createDetailFragment(int position) {
        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position, Constants.SOURCE_SAVED);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.restaurantDetailContainer, detailFragment);
        ft.commit();
    }

    @Override
    public void onBindViewHolder(SavedRestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mRestaurants, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mDataSource.delete(mRestaurants.get(position).getDatabaseId());
        mRestaurants.remove(position);
        notifyItemRemoved(position);
    }

    public void setSortOrder() {
        for (Restaurant restaurant : mRestaurants) {
            restaurant.setSortOrder(mRestaurants.indexOf(restaurant));
            mDataSource = new RestaurantDataSource(mContext);
            mDataSource.updateSortOrder(restaurant);
        }
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        private ImageView mRestaurantImageView;
        private TextView mNameTextView;
        private TextView mCategoryTextView;
        private TextView mRatingTextView;
        private Context mContext;
        private int mOrientation;
        private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
        private OnRestaurantSelectedListener mRestaurantSelectedListener;

        public RestaurantViewHolder(View itemView, ArrayList<Restaurant> restaurants, OnRestaurantSelectedListener restaurantSelectedListener) {
            super(itemView);
            bindViews(itemView);
            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mRestaurants = restaurants;
            mRestaurantSelectedListener = restaurantSelectedListener;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }

        private void bindViews(View view) {
            mRestaurantImageView = (ImageView) view.findViewById(R.id.restaurantImageView);
            mNameTextView = (TextView) view.findViewById(R.id.restaurantNameTextView);
            mCategoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
            mRatingTextView = (TextView) view.findViewById(R.id.ratingTextView);
        }

        public void bindRestaurant(Restaurant restaurant) {
            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategoryList().get(0));
            mRatingTextView.setText(String.format(mContext.getResources().getString(R.string.rating_format), restaurant.getRating()));

            if (!restaurant.getImageUrl().contains("http")) {
                try {
                    Bitmap imageBitmap = decodeFromFirebaseBase64(restaurant.getImageUrl());
                    mRestaurantImageView.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Picasso.with(mContext)
                        .load(RestaurantPropertyHelper.getLargeImageUrl(restaurant.getImageUrl()))
                        .resize(MAX_WIDTH, MAX_HEIGHT)
                        .centerCrop()
                        .into(mRestaurantImageView);
            }
        }

        private void createDetailFragment(int position) {
            RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position, Constants.SOURCE_FIND);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.restaurantDetailContainer, detailFragment);
            ft.commit();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mRestaurantSelectedListener.onRestaurantSelected(itemPosition, mRestaurants, Constants.SOURCE_FIND);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }

        @Override
        public void onItemSelected() {
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.drag_scale_on);
            set.setTarget(itemView);
            set.start();
        }

        @Override
        public void onItemClear() {
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.drag_scale_off);
            set.setTarget(itemView);
            set.start();
        }

        public Bitmap decodeFromFirebaseBase64(String image) throws IOException {
            byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
        }
    }
}
