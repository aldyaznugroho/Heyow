package com.skripsigg.heyow.ui.auth;

import com.google.android.gms.common.api.GoogleApiClient;
import com.skripsigg.heyow.ui.base.MvpView;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/10/2017.
 */

public interface AuthMvpView extends MvpView {
    Observable<Object> googleLoginButtonClick();
    void startGoogleLoginIntent();
    void openInterestActivity();
    void openHomeActivity();
}
