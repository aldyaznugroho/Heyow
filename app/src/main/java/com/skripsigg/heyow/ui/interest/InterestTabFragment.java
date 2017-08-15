package com.skripsigg.heyow.ui.interest;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BaseFragment;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.views.adapters.CustomRecyclerAdapter;
import com.skripsigg.heyow.views.widgets.DividerItemDecorationCustom;
import com.skripsigg.heyow.views.widgets.MainStateLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterestTabFragment extends BaseFragment implements InterestMvpView {

    @Inject
    InterestMvpPresenter<InterestMvpView> presenter;

    @BindView(R.id.interest_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.interest_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    MainStateLayout stateLayout;

    private CustomRecyclerAdapter<MatchDetailResponse> interestListAdapter;
    private boolean isDataLoad = false;

    public static InterestTabFragment getInstance() {
        return new InterestTabFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_interest_tab, container, false);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        init();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isDataLoad) {
            presenter.loadInterestApiService();
            isDataLoad = true;
        }
    }

    private void init() {
        initSwipeRefresh();
        initRecyclerView();
        initStateLayout();
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
                getContext(),
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
                presenter.loadInterestApiService();
            }
        });
    }

    private String fetchCategoryName(int position) {
        String[] catList = getResources().getStringArray(R.array.match_category);
        return catList[position];
    }

    @Override
    public void bindDataToRecyclerView(List<MatchDetailResponse> matchList) {
        interestListAdapter = new CustomRecyclerAdapter<>(
                getContext(),
                R.layout.match_view_holder_1,
                matchList,
                new CustomRecyclerAdapter.ViewHolderBinder<MatchDetailResponse>() {
                    @Override
                    public void bind(CustomRecyclerAdapter.CustomViewHolder<MatchDetailResponse> holder, final MatchDetailResponse item) {
                        LinearLayout itemListLayout = (LinearLayout) holder.itemView.findViewById(R.id.match_list_1_parent_layout);
                        ImageView matchImage = (ImageView) holder.itemView.findViewById(R.id.match_list_1_poster_image);
                        TextView matchTitleText = (TextView) holder.itemView.findViewById(R.id.match_list_1_title_text);
                        TextView matchCategoryText = (TextView) holder.itemView.findViewById(R.id.match_list_1_category_text);
                        TextView matchDateTimeText = (TextView) holder.itemView.findViewById(R.id.match_list_1_datetime_text);
                        TextView matchRegionText = (TextView) holder.itemView.findViewById(R.id.match_list_1_region_text);

                        String imagePath = item.getMatchImage();
                        String matchTitle = item.getMatchTitle();
                        String matchCategory = item.getMatchCategory();
                        String matchDate = item.getMatchDate();
                        String matchTime = item.getMatchTime();
                        String matchLocation = item.getMatchLocation();

                        int afterLastComma = matchLocation.lastIndexOf(",");
                        int beforeLastComma = matchLocation.lastIndexOf(",", afterLastComma - 1);

//                        String city = matchLocation.substring(beforeLastComma + 1, afterLastComma).trim();
                        String country = matchLocation.substring(afterLastComma + 1);

                        Glide.with(getContext())
                                .load(imagePath)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .into(matchImage);

                        matchTitleText.setText(matchTitle);
                        matchCategoryText.setText(fetchCategoryName(Integer.parseInt(matchCategory)));
                        matchDateTimeText.setText(matchDate + " @ " + matchTime);
                        matchRegionText.setText(country);
                        itemListLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle matchDetailBundle = new Bundle();
                                matchDetailBundle.putString(Constants.KEY_MATCH_ID, item.getMatchId());
                                matchDetailBundle.putString(Constants.MATCH_DETAIL_TYPE, Constants.TYPE_MATCH_GENERAL);

                                Intent matchDetailIntent = new Intent(getContext(), MatchDetailActivity.class);
                                matchDetailIntent.putExtras(matchDetailBundle);
                                startActivity(matchDetailIntent);
                            }
                        });
                    }
                });
        recyclerView.addItemDecoration(
                new DividerItemDecorationCustom(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(interestListAdapter);
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
                presenter.loadInterestApiService();
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