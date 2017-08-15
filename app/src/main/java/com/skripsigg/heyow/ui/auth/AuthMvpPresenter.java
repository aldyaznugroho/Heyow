package com.skripsigg.heyow.ui.auth;

import android.content.Intent;

import com.skripsigg.heyow.ui.base.MvpPresenter;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by Aldyaz on 5/10/2017.
 */

public interface AuthMvpPresenter<V extends AuthMvpView> extends MvpPresenter<V> {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
