package com.skripsigg.heyow.ui.joinedmatch;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/22/2017.
 */

public interface JoinedMatchMvpFragmentView extends MvpView {
    UserModel getJoinedUserParcelable();
    void bindDataToRecyclerView(List<MatchDetailResponse> matchList);
    void showRetryLayout();
    void showRecyclerList();
    void showNotFoundLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
