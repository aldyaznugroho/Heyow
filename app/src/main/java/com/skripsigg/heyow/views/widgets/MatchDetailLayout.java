package com.skripsigg.heyow.views.widgets;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsigg.heyow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aldyaz on 5/31/2017.
 */

public class MatchDetailLayout extends CoordinatorLayout {

    /*@BindView(R.id.detail_app_bar) AppBarLayout appBar;
    @BindView(R.id.detail_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.detail_backdrop_image) ImageView backdropImage;
    @BindView(R.id.detail_toolbar) Toolbar detailToolbar;
    @BindView(R.id.detail_chat_fab) FloatingActionButton chatFab;

    @BindView(R.id.detail_content_layout) View contentLayout;
    @BindView(R.id.detail_edit_delete_layout) View editDeleteLayout;
    @BindView(R.id.detail_leave_layout) View leaveLayout;
    @BindView(R.id.detail_not_join_layout) View notJoinLayout;

    @BindView(R.id.user_list_recycler_view) RecyclerView userListRecyclerView;
    @BindView(R.id.detail_edit_button) Button editButton;
    @BindView(R.id.detail_delete_button) Button deleteButton;
    @BindView(R.id.detail_title_text) TextView titleText;
    @BindView(R.id.detail_category_text) TextView categoryText;
    @BindView(R.id.detail_date_time_text) TextView dateTimeText;
    @BindView(R.id.detail_player_section_text) TextView playerSectionText;
    @BindView(R.id.detail_quota_now_text) TextView quotaNowText;
    @BindView(R.id.detail_quota_max_text) TextView quotaMaxText;
    @BindView(R.id.detail_venue_text) TextView venueText;
    @BindView(R.id.detail_address_text) TextView addressText;
    @BindView(R.id.detail_map_image) ImageView mapImage;
    @BindView(R.id.detail_description_text) TextView descText;
    @BindView(R.id.detail_leave_button) Button leaveButton;
    @BindView(R.id.detail_join_button) Button joinButton;*/

    public MatchDetailLayout(Context context) {
        super(context);
    }

    public MatchDetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchDetailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.match_detail_header_layout, this);
//        ButterKnife.bind(this);
    }
}
