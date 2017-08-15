package com.skripsigg.heyow.ui.nearme;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface NearMeMvpPresenter<V extends NearMeMvpView> extends MvpPresenter<V> {
    void loadNearMeApiService();
}
