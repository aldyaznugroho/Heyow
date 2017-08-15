package com.skripsigg.heyow.ui.splash;

import android.content.Context;

import com.skripsigg.heyow.ui.base.MvpView;

/**
 * Created by Aldyaz on 5/13/2017.
 */

public interface SplashMvpView extends MvpView {
    Context getSplashContext();
    boolean checkIsHasPermission();
    void requestPermission();
    void showProgressBar();
    void dismissProgressBar();
    void openHomeActivity();
    void openAuthActivity();
    void openInterestActivity();
}
