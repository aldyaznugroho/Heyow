package com.skripsigg.heyow.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.skripsigg.heyow.models.MatchDetailResponse;

import java.util.List;

/**
 * Created by Aldyaz on 6/22/2017.
 */

public class CreatedJoinedAdapter extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<MatchDetailResponse>> delegatesManager;
    private List<MatchDetailResponse> items;

    public CreatedJoinedAdapter(Context context, List<MatchDetailResponse> items) {
        this.items = items;

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new UserHistoryAdapterDelegate(context));
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
