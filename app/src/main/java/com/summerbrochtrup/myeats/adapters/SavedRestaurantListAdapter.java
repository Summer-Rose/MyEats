package com.summerbrochtrup.myeats.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.presenters.RestaurantPresenter;
import com.summerbrochtrup.myeats.presenters.SavedRestaurantsPresenter;
import com.summerbrochtrup.myeats.util.ItemTouchHelperAdapter;
import com.summerbrochtrup.myeats.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myeats.util.OnStartDragListener;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class SavedRestaurantListAdapter extends MvpRecyclerListAdapter<Restaurant, RestaurantPresenter, RestaurantViewHolder> implements ItemTouchHelperAdapter {
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;
    private RestaurantDataSource mDataSource;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    private RestaurantPresenter mPresenter;

    private SavedRestaurantsPresenter mSavedPresenter;

    public SavedRestaurantListAdapter(Context context, OnStartDragListener onStartDragListener, OnRestaurantSelectedListener restaurantSelectedListener, SavedRestaurantsPresenter savedRestaurantsPresenter) {
        mContext = context;
        mOnStartDragListener = onStartDragListener;
        mOnRestaurantSelectedListener = restaurantSelectedListener;
        mDataSource = new RestaurantDataSource(context);
        mSavedPresenter = savedRestaurantsPresenter;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_drag, parent, false);
        final RestaurantViewHolder viewHolder = new RestaurantViewHolder(view, mOnStartDragListener);
        return viewHolder;
    }

//    public void setSortOrder() {
//        int itemCount = getItemCount();
//        for (int i = 0; i < itemCount; i++) {
//            getItem(i).setSortOrder(i);
//            mDataSource.updateSortOrder(getItem(i));
//        }
//    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
//        swapCollections(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
//        mDataSource.delete(mRestaurants.get(position).getDatabaseId());
//        mRestaurants.remove(position);
//        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    protected RestaurantPresenter createPresenter(@NonNull Restaurant model) {
        mPresenter = new RestaurantPresenter();
        mPresenter.setModel(model);
        return mPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Restaurant model) {
        return model.getDatabaseId();
    }
}
