package com.skripsigg.heyow.ui.searchresults;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.skripsigg.heyow.models.SearchRequestModel;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.views.adapters.CustomRecyclerAdapter;
import com.skripsigg.heyow.views.widgets.DividerItemDecorationCustom;
import com.skripsigg.heyow.views.widgets.MainStateLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends BaseActivity implements SearchResultsMvpView {

    @Inject SearchResultsMvpPresenter<SearchResultsMvpView> presenter;

    @BindView(R.id.search_results_toolbar) Toolbar searchResultsToolbar;
    @BindView(R.id.search_results_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.state_layout) MainStateLayout stateLayout;

    protected CustomRecyclerAdapter<MatchDetailResponse> searchResultsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(searchResultsToolbar);
        init();
    }

    private void init() {
        initToolbar();
        initSearchTextToolbar();
        initRecyclerView();
        initStateLayout();
    }

    private void initToolbar() {
        searchResultsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = searchResultsToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
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
                presenter.loadSearchMatchResults();
            }
        });
    }

    private void initSearchTextToolbar() {
        String venue = getSearchRequestExtra().getMatchVenue();
        String location = getSearchRequestExtra().getMatchLocation();
        String category = getSearchRequestExtra().getMatchCategory();

        if (!venue.equalsIgnoreCase("") && location.equalsIgnoreCase("") && category.equalsIgnoreCase("")) {
            setTitle(venue);
        } else if (venue.equalsIgnoreCase("") && !location.equalsIgnoreCase("") && category.equalsIgnoreCase("")) {
            setTitle(location);
        } else if (venue.equalsIgnoreCase("") && location.equalsIgnoreCase("") && !category.equalsIgnoreCase("")) {
            setTitle(fetchCategoryName(Integer.parseInt(category)));
        } else if (!venue.equalsIgnoreCase("") && !location.equalsIgnoreCase("") && category.equalsIgnoreCase("")) {
            setTitle(venue + ", " + location);
        } else if (venue.equalsIgnoreCase("") && !location.equalsIgnoreCase("") && !category.equalsIgnoreCase("")) {
            setTitle(fetchCategoryName(Integer.parseInt(category)));
            searchResultsToolbar.setSubtitle(location);
        } else if (!venue.equalsIgnoreCase("") && location.equalsIgnoreCase("") && !category.equalsIgnoreCase("")) {
            setTitle(fetchCategoryName(Integer.parseInt(category)));
            searchResultsToolbar.setSubtitle(venue);
        } else if (!venue.equalsIgnoreCase("") && !location.equalsIgnoreCase("") && !category.equalsIgnoreCase("")) {
            setTitle(fetchCategoryName(Integer.parseInt(category)));
            searchResultsToolbar.setSubtitle(venue + ", " + location);
        } else {
            setTitle("No search");
        }
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
    public SearchRequestModel getSearchRequestExtra() {
        return getIntent().getExtras().getParcelable(Constants.KEY_SEARCH_QUERY_PARCELABLE);
    }

    @Override
    public void bindDataToRecyclerView(List<MatchDetailResponse> resultResponse) {
        searchResultsListAdapter = new CustomRecyclerAdapter<>(
                this,
                R.layout.match_view_holder_2,
                resultResponse,
                new CustomRecyclerAdapter.ViewHolderBinder<MatchDetailResponse>() {
                    @Override
                    public void bind(CustomRecyclerAdapter.CustomViewHolder<MatchDetailResponse> holder, final MatchDetailResponse item) {
                        LinearLayout itemListLayout = (LinearLayout) holder.itemView.findViewById(R.id.match_list_2_parent_layout);
                        ImageView matchImage = (ImageView) holder.itemView.findViewById(R.id.match_list_2_poster_image);
                        TextView matchTitleText = (TextView) holder.itemView.findViewById(R.id.match_list_2_title_text);
                        TextView matchCategoryText = (TextView) holder.itemView.findViewById(R.id.match_list_2_category_text);
                        TextView matchDateTimeText = (TextView) holder.itemView.findViewById(R.id.match_list_2_datetime_text);

                        String imagePath = item.getMatchImage();
                        String matchTitle = item.getMatchTitle();
                        String matchCategory = item.getMatchCategory();
                        String matchDate = item.getMatchDate();
                        String matchTime = item.getMatchTime();

                        Glide.with(SearchResultsActivity.this)
                                .load(imagePath)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .into(matchImage);

                        matchTitleText.setText(matchTitle);
                        matchCategoryText.setText(fetchCategoryName(Integer.parseInt(matchCategory)));
                        matchDateTimeText.setText(matchDate + " @ " + matchTime);
                        itemListLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle matchDetailBundle = new Bundle();
                                matchDetailBundle.putString(Constants.KEY_MATCH_ID, item.getMatchId());

                                Intent matchDetailIntent = new Intent(SearchResultsActivity.this, MatchDetailActivity.class);
                                matchDetailIntent.putExtras(matchDetailBundle);
                                startActivity(matchDetailIntent);
                            }
                        });
                    }
                });
        recyclerView.addItemDecoration(
                new DividerItemDecorationCustom(getBaseContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(searchResultsListAdapter);
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
