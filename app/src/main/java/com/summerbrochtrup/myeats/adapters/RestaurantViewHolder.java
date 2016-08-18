package com.summerbrochtrup.myeats.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.presenters.RestaurantPresenter;
import com.summerbrochtrup.myeats.util.ItemTouchHelperViewHolder;
import com.summerbrochtrup.myeats.util.OnStartDragListener;
import com.summerbrochtrup.myeats.util.RestaurantPropertyHelper;
import com.summerbrochtrup.myeats.views.RestaurantView;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public class RestaurantViewHolder extends MvpViewHolder<RestaurantPresenter> implements ItemTouchHelperViewHolder, RestaurantView {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ImageView mRestaurantImageView;
    private TextView mNameTextView;
    private TextView mCategoryTextView;
    private TextView mRatingTextView;
    private Context mContext;
    private OnStartDragListener mDragListener;

    public RestaurantViewHolder(View itemView, OnStartDragListener listener) {
        super(itemView);
        mDragListener = listener;
        bindViews(itemView);
        mContext = itemView.getContext();
    }

    private void bindViews(View view) {
        mRestaurantImageView = (ImageView) view.findViewById(R.id.restaurantImageView);
        mNameTextView = (TextView) view.findViewById(R.id.restaurantNameTextView);
        mCategoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
        mRatingTextView = (TextView) view.findViewById(R.id.ratingTextView);

        mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragListener.onStartDrag(RestaurantViewHolder.this);
                }
                return false;
            }
        });
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


    @Override
    public void setRestaurantValues(Restaurant restaurant) {
        mNameTextView.setText(restaurant.getName());
        mCategoryTextView.setText(restaurant.getCategoryList().get(0));
        mRatingTextView.setText(String.format(mContext.getResources().getString(R.string.rating_format), restaurant.getRating()));
        Picasso.with(mContext)
                .load(RestaurantPropertyHelper.getLargeImageUrl(restaurant.getImageUrl()))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mRestaurantImageView);
    }
}