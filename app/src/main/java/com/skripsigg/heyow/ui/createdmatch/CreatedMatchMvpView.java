package com.skripsigg.heyow.ui.createdmatch;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/14/2017.
 */

public interface CreatedMatchMvpView extends MvpView {
    UserModel getJoinedUserParcelable();
    void bindDataToRecyclerView(List<MatchDetailResponse> matchList);
    void setSwipeRefreshActive();
    void setSwipeRefreshNotActive();
    void showMainLayout();
    void showRetryLayout();
    void showNotFoundLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
