package com.summerbrochtrup.myeats.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.summerbrochtrup.myeats.presenters.BasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public abstract class MvpRecyclerAdapter<M, P extends BasePresenter, VH extends MvpViewHolder> extends RecyclerView.Adapter<VH> {
    protected final Map<Object, P> presenters;

    public MvpRecyclerAdapter() {
        presenters = new HashMap<>();
    }

    @NonNull protected P getPresenter(@NonNull M model) {
        return presenters.get(getModelId(model));
    }

    @NonNull protected abstract P createPresenter(@NonNull M model);

    @NonNull protected abstract Object getModelId(@NonNull M model);


    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);

        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        holder.unbindPresenter();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindPresenter(getPresenter(getItem(position)));
    }

    protected abstract M getItem(int position);
}