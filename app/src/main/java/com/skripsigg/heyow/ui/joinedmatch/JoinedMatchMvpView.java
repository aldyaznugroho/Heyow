package com.skripsigg.heyow.ui.joinedmatch;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/16/2017.
 */

public interface JoinedMatchMvpView extends MvpView {
    void bindDataToRecyclerView(List<MatchDetailResponse> matchList);
    void setSwipeRefreshActive();
    void setSwipeRefreshNotActive();
    void showMainLayout();
    void showRetryLayout();
    void showNotFoundLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
