package com.summerbrochtrup.myeats.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.summerbrochtrup.myeats.R;
import com.summerbrochtrup.myeats.adapters.SavedRestaurantListAdapter;
import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myeats.util.OnStartDragListener;
import com.summerbrochtrup.myeats.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class SavedRestaurantListFragment extends Fragment implements OnStartDragListener {
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private SavedRestaurantListAdapter mAdapter;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    public SavedRestaurantListFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRestaurantSelectedListener = (OnRestaurantSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_restaurant_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        getRestaurants();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.setSortOrder();
    }

    private void getRestaurants() {
        RestaurantDataSource dataSource = new RestaurantDataSource(getActivity());
        ArrayList<Restaurant> restaurants = dataSource.readRestaurants();
        mAdapter = new SavedRestaurantListAdapter(getActivity(), restaurants, this, mOnRestaurantSelectedListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
