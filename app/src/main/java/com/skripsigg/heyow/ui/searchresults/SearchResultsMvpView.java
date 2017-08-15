package com.skripsigg.heyow.ui.searchresults;

import com.skripsigg.heyow.models.FeedMatchResponse;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.SearchRequestModel;
import com.skripsigg.heyow.ui.base.MvpView;

import java.util.List;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface SearchResultsMvpView extends MvpView {
    SearchRequestModel getSearchRequestExtra();
    void bindDataToRecyclerView(List<MatchDetailResponse> resultResponse);
    void showMainLayout();
    void showRetryLayout();
    void showNotFoundLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
