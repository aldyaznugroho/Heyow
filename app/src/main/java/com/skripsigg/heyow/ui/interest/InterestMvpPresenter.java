package com.skripsigg.heyow.ui.interest;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface InterestMvpPresenter<V extends InterestMvpView> extends MvpPresenter<V> {
    void loadInterestApiService();
}
