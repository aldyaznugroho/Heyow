package com.skripsigg.heyow.ui.matchdetail;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/17/2017.
 */

public interface MatchDetailMvpPresenter<V extends MatchDetailMvpView> extends MvpPresenter<V> {
    void loadMatchDetailApiService();
    void loadLeaveMatchApiService();
    void loadDeleteMatchApiService();
    MatchDetailResponse getMatchDetailResponseResult();
}
