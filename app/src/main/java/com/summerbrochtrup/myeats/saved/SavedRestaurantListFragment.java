package com.summerbrochtrup.myeats.saved;


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
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.util.OnStartDragListener;
import com.summerbrochtrup.myeats.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class SavedRestaurantListFragment extends Fragment implements OnStartDragListener, SavedFragView {
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private SavedRestaurantListAdapter mAdapter;
    private SavedFragPresenter mPresenter;

    public SavedRestaurantListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_restaurant_list, container, false);
        initializeRecyclerView(view);
        mPresenter = new SavedFragPresenter(this);
        mPresenter.getRestaurants(getActivity());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    public void initializeRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SavedRestaurantListAdapter(getActivity(), this, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

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

    @Override
    public void displayRestaurants(ArrayList<Restaurant> restaurants) {
        mAdapter.addRestaurants(restaurants);
    }
}
