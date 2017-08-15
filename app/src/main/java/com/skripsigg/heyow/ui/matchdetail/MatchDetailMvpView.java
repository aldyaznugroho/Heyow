package com.skripsigg.heyow.ui.matchdetail;

import android.content.Context;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpView;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/17/2017.
 */

public interface MatchDetailMvpView extends MvpView {
    Context getContext();
    void finishActivity();
    String getMatchIdStringExtra();
    void openMapsDetailActivity(String latitude, String longitude);
    void openEditMatchActivity();
    void openJoinedMatchActivity();
    void openMatchChatActivity();
    void bindDataToMainMatchDetail(MatchDetailResponse responseResult);
    void showLeaveMatchAlertDialog();
    void showDeleteMatchAlertDialog();
    Observable<Object> mapPreviewClick();
    Observable<Object> chatFloatingButtonClick();
    Observable<Object> matchJoinButtonClick();
    Observable<Object> matchLeaveButtonClick();
    Observable<Object> matchEditButtonClick();
    Observable<Object> matchDeleteButtonClick();
    void hideDeleteLayout();
    void showUserJoinedMainLayout();
    void showUserNotJoinedMainLayout();
    void showMatchDetailLayout();
    void showRetryLayout();
    void showProgressBar();
    void setOfflineText(String offlineText);
}
