package com.summerbrochtrup.myeats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.summerbrochtrup.myeats.presenters.BasePresenter;

/**
 * Created by summerbrochtrup on 8/16/16.
 */
public abstract class MvpViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder {
    protected P presenter;

    public MvpViewHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        presenter = null;
    }
}