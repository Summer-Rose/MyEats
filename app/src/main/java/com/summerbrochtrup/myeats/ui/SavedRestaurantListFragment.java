package com.summerbrochtrup.myeats.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.summerbrochtrup.myeats.presenters.PresenterManager;
import com.summerbrochtrup.myeats.presenters.SavedRestaurantsPresenter;
import com.summerbrochtrup.myeats.util.OnRestaurantSelectedListener;
import com.summerbrochtrup.myeats.util.OnStartDragListener;
import com.summerbrochtrup.myeats.util.SimpleItemTouchHelperCallback;
import com.summerbrochtrup.myeats.views.SavedRestaurantsView;

import java.util.ArrayList;

public class SavedRestaurantListFragment extends Fragment implements OnStartDragListener, SavedRestaurantsView {
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private SavedRestaurantListAdapter mAdapter;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    private SavedRestaurantsPresenter mPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPresenter = new SavedRestaurantsPresenter(getContext());
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unbindView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_restaurant_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SavedRestaurantListAdapter(getActivity(), this, mOnRestaurantSelectedListener, mPresenter);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
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

    @Override
    public void showRestaurants(ArrayList<Restaurant> restaurants) {
        mAdapter.clearAndAddAll(restaurants);
    }
}
