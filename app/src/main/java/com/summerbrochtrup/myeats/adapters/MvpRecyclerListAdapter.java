package com.summerbrochtrup.myeats.adapters;

import com.summerbrochtrup.myeats.database.RestaurantDataSource;
import com.summerbrochtrup.myeats.models.Restaurant;
import com.summerbrochtrup.myeats.presenters.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public abstract class MvpRecyclerListAdapter<M, P extends BasePresenter, VH extends MvpViewHolder<P>> extends MvpRecyclerAdapter<M, P, VH> {
    private final List<M> models;

    public MvpRecyclerListAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data) {
        models.clear();
        presenters.clear();
        for (M item : data) {
            addInternal(item);
        }
        notifyDataSetChanged();
    }

    public void swapCollections(int fromPosition, int toPosition) {
        Collections.swap(models, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(item);
        }
        presenters.remove(getModelId(item));

        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;
        for (int i = 0; i < models.size(); i++) {
            M model = models.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void addInternal(M item) {
        System.err.println("Adding item " + getModelId(item));
        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int position) {
        return models.get(position);
    }
}