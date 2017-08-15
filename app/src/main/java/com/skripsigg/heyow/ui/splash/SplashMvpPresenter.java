package com.skripsigg.heyow.ui.splash;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/13/2017.
 */

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
    void startLocationService();
    void stopLocationService();
}
