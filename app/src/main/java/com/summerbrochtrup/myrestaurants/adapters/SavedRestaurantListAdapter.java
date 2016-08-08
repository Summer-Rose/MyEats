package com.summerbrochtrup.myrestaurants.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
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
import com.summerbrochtrup.myrestaurants.models.Restaurant;
import com.summerbrochtrup.myrestaurants.ui.RestaurantDetailActivity;
import com.summerbrochtrup.myrestaurants.ui.RestaurantDetailFragment;
import com.summerbrochtrup.myrestaurants.util.ItemTouchHelperAdapter;
import com.summerbrochtrup.myrestaurants.util.ItemTouchHelperViewHolder;
import com.summerbrochtrup.myrestaurants.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myrestaurants.util.OnStartDragListener;
import com.summerbrochtrup.myrestaurants.util.RestaurantPropertyHelper;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class SavedRestaurantListAdapter extends RecyclerView.Adapter<SavedRestaurantListAdapter.RestaurantViewHolder> implements ItemTouchHelperAdapter {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private List<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;

    private OnStartDragListener mOnStartDragListener;
    private int mOrientation;

    public SavedRestaurantListAdapter(Context context, List<Restaurant> restaurants, OnStartDragListener onStartDragListener) {
        mContext = context;
        mRestaurants = restaurants;
        mOnStartDragListener = onStartDragListener;
    }

    @Override
    public SavedRestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_drag, parent, false);
        final RestaurantViewHolder viewHolder = new RestaurantViewHolder(view, mRestaurants);
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
        mRestaurants.remove(position);
//        getRef(position).removeValue();
    }

//    private void setIndexInFirebase() {
//        for (Restaurant restaurant : mRestaurants) {
//            int index = mRestaurants.indexOf(restaurant);
//            DatabaseReference ref = getRef(index);
//            ref.child(Constants.FIREBASE_QUERY_INDEX).setValue(Integer.toString(index));
//        }
//    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        private ImageView mRestaurantImageView;
        private TextView mNameTextView;
        private TextView mCategoryTextView;
        private TextView mRatingTextView;
        private Context mContext;
        private int mOrientation;
        private List<Restaurant> mRestaurants = new ArrayList<>();

        public RestaurantViewHolder(View itemView, List<Restaurant> restaurants) {
            super(itemView);
            bindViews(itemView);
            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mRestaurants = restaurants;
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
            Picasso.with(mContext)
                    .load(RestaurantPropertyHelper.getLargeImageUrl(restaurant.getImageUrl()))
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRestaurantImageView);

            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategoryList().get(0));
            mRatingTextView.setText(String.format(mContext.getResources().getString(R.string.rating_format), restaurant.getRating()));
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

        }

        @Override
        public void onItemClear() {

        }
    }
}
