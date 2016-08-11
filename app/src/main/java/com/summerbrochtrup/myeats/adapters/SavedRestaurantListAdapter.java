package com.summerbrochtrup.myeats.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.summerbrochtrup.myeats.Constants;
import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.ui.RestaurantDetailActivity;
import com.summerbrochtrup.myeats.ui.SavedRestaurantDetailFragment;
import com.summerbrochtrup.myeats.util.ItemTouchHelperAdapter;
import com.summerbrochtrup.myeats.util.ItemTouchHelperViewHolder;
import com.summerbrochtrup.myeats.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myeats.util.OnStartDragListener;
import com.summerbrochtrup.myeats.util.RestaurantPropertyHelper;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class SavedRestaurantListAdapter extends RecyclerView.Adapter<SavedRestaurantListAdapter.RestaurantViewHolder> implements ItemTouchHelperAdapter {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;
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
        final RestaurantViewHolder viewHolder = new RestaurantViewHolder(view, mRestaurants);
        final int orientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
                mOnRestaurantSelectedListener.onRestaurantSelected(mRestaurants.get(itemPosition), Constants.SOURCE_SAVED);
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANT, Parcels.wrap(mRestaurants.get(itemPosition)));
                    intent.putExtra(Constants.EXTRA_KEY_SOURCE, Constants.SOURCE_SAVED);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, viewHolder.mRestaurantImageView,
                                mContext.getResources().getString(R.string.transition_name_rest_img));
                    mContext.startActivity(intent, options.toBundle());
                }
            }
        });
        return viewHolder;
    }

    private void createDetailFragment(int position) {
        SavedRestaurantDetailFragment detailFragment = SavedRestaurantDetailFragment.newInstance(mRestaurants.get(position));
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

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private ImageView mRestaurantImageView;
        private TextView mNameTextView;
        private TextView mCategoryTextView;
        private TextView mRatingTextView;
        private Context mContext;

        public RestaurantViewHolder(View itemView, ArrayList<Restaurant> restaurants) {
            super(itemView);
            bindViews(itemView);
            mContext = itemView.getContext();
            mRestaurants = restaurants;
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
            Picasso.with(mContext)
                    .load(RestaurantPropertyHelper.getLargeImageUrl(restaurant.getImageUrl()))
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRestaurantImageView);
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
    }
}
