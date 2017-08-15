package com.skripsigg.heyow.ui.createdmatch;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.skripsigg.heyow.models.UserModel;
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
public class CreatedMatchFragment extends BaseFragment implements CreatedMatchMvpFragmentView {

    @Inject CreatedMatchMvpFragmentPresenter<CreatedMatchMvpFragmentView> presenter;

    @BindView(R.id.state_layout)
    MainStateLayout stateLayout;
    @BindView(R.id.fragment_created_recycler_view) RecyclerView recyclerView;

    private CustomRecyclerAdapter<MatchDetailResponse> createdMatchListAdapter;

    public static CreatedMatchFragment getInstance() {
        return new CreatedMatchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_created_match_tab, container, false);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        init();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadUserCreatedMatchApiService();
    }

    private void init() {
        initRecyclerView();
        initStateLayout();
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
                presenter.loadUserCreatedMatchApiService();
            }
        });
    }

    private String fetchCategoryName(int position) {
        String[] catList = getResources().getStringArray(R.array.match_category);
        return catList[position];
    }

    @Override
    public UserModel getJoinedUserParcelable() {
        return getActivity().getIntent().getExtras().getParcelable(Constants.KEY_MATCH_RESPONSE_PARCELABLE);
    }

    @Override
    public void bindDataToRecyclerView(List<MatchDetailResponse> matchList) {
        createdMatchListAdapter = new CustomRecyclerAdapter<>(
                getContext(),
                R.layout.match_view_holder_2,
                matchList,
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

                        Glide.with(getContext())
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

                                Intent matchDetailIntent = new Intent(getContext(), MatchDetailActivity.class);
                                matchDetailIntent.putExtras(matchDetailBundle);
                                startActivity(matchDetailIntent);
                            }
                        });
                    }
                });
        recyclerView.addItemDecoration(
                new DividerItemDecorationCustom(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(createdMatchListAdapter);
    }

    @Override
    public void showRetryLayout() {
        stateLayout.showOffline();
    }

    @Override
    public void showRecyclerList() {
        stateLayout.showContent();
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
