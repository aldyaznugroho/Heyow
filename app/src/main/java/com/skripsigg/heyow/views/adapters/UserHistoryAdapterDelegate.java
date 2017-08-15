package com.skripsigg.heyow.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.utils.others.Constants;

import java.util.List;

/**
 * Created by Aldyaz on 6/22/2017.
 */

public class UserHistoryAdapterDelegate extends AdapterDelegate<List<MatchDetailResponse>> {

    private Context context;

    public UserHistoryAdapterDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<MatchDetailResponse> items, int position) {
        return items.get(position) != null;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.match_view_holder_2, parent, false);
        return new JoinedMatchViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<MatchDetailResponse> items,
                                    int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        JoinedMatchViewHolder viewHolder = (JoinedMatchViewHolder) holder;
        final MatchDetailResponse item = items.get(position);

        String imagePath = item.getMatchImage();
        String matchTitle = item.getMatchTitle();
        String matchCategory = item.getMatchCategory();
        String matchDate = item.getMatchDate();
        String matchTime = item.getMatchTime();

        String[] catList = context.getResources().getStringArray(R.array.match_category);

        Glide.with(context)
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(viewHolder.matchImage);

        viewHolder.matchTitleText.setText(matchTitle);
        viewHolder.matchCategoryText.setText(catList[Integer.parseInt(matchCategory)]);
        viewHolder.matchDateTimeText.setText(matchDate + " @ " + matchTime);
        viewHolder.itemListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle matchDetailBundle = new Bundle();
                matchDetailBundle.putString(Constants.KEY_MATCH_ID, item.getMatchId());

                Intent matchDetailIntent = new Intent(context, MatchDetailActivity.class);
                matchDetailIntent.putExtras(matchDetailBundle);
                context.startActivity(matchDetailIntent);
            }
        });
    }

    protected class JoinedMatchViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemListLayout;
        ImageView matchImage;
        TextView matchTitleText, matchCategoryText, matchDateTimeText;

        public JoinedMatchViewHolder(View itemView) {
            super(itemView);

            itemListLayout = (LinearLayout) itemView.findViewById(R.id.match_list_2_parent_layout);
            matchImage = (ImageView) itemView.findViewById(R.id.match_list_2_poster_image);
            matchTitleText = (TextView) itemView.findViewById(R.id.match_list_2_title_text);
            matchCategoryText = (TextView) itemView.findViewById(R.id.match_list_2_category_text);
            matchDateTimeText = (TextView) itemView.findViewById(R.id.match_list_2_datetime_text);
        }
    }
}
