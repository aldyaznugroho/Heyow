package com.skripsigg.heyow.ui.interest;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpView;

import java.util.List;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface InterestMvpView extends MvpView {
    void bindDataToRecyclerView(List<MatchDetailResponse> matchList);
    void setSwipeRefreshActive();
    void setSwipeRefreshNotActive();
    void showMainLayout();
    void showRetryLayout();
    void showNotFoundLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
