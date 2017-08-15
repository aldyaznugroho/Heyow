package com.skripsigg.heyow.ui.joinedmatch;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.views.adapters.CreatedJoinedAdapter;
import com.skripsigg.heyow.views.adapters.CustomRecyclerAdapter;
import com.skripsigg.heyow.views.widgets.DividerItemDecorationCustom;
import com.skripsigg.heyow.views.widgets.MainStateLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinedMatchActivity extends BaseActivity implements JoinedMatchMvpView {

    @Inject JoinedMatchMvpPresenter<JoinedMatchMvpView> presenter;

    @BindView(R.id.joined_match_toolbar) Toolbar toolbar;
    @BindView(R.id.joined_match_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.joined_match_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.state_layout) MainStateLayout stateLayout;

    CustomRecyclerAdapter<MatchDetailResponse> recyclerListAdapter;
    CreatedJoinedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_match);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.joined_match));
        init();
    }

    private void init() {
        initToolbar();
        initSwipeRefresh();
        initRecyclerView();
        initStateLayout();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setSwipeRefreshActive();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getBaseContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void initStateLayout() {
        stateLayout.setTransitionsEnabled(false);
        stateLayout.setOfflineRetryOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadUserJoinedMatchApiService();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String fetchCategoryName(int position) {
        String[] catList = getResources().getStringArray(R.array.match_category);
        return catList[position];
    }

    @Override
    public void bindDataToRecyclerView(List<MatchDetailResponse> matchList) {
        adapter = new CreatedJoinedAdapter(this, matchList);
        recyclerView.addItemDecoration(
                new DividerItemDecorationCustom(getBaseContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setSwipeRefreshActive() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefresh != null)
                    swipeRefresh.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefresh.setRefreshing(true);
                        }
                    });
                presenter.loadUserJoinedMatchApiService();
            }
        });
    }

    @Override
    public void setSwipeRefreshNotActive() {
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMainLayout() {
        stateLayout.showContent();
    }

    @Override
    public void showRetryLayout() {
        stateLayout.showOffline();
    }

    @Override
    public void showNotFoundLayout() {
        stateLayout.showEmpty();
    }

    @Override
    public void showProgressBar() {
        stateLayout.showProgress();
    }

    @Override
    public void setOfflineText(String offlineText) {
        stateLayout.setOfflineText(offlineText);
    }
}
