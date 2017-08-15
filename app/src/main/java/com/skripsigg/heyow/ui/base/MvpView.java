package com.skripsigg.heyow.ui.base;

/**
 * Created by Aldyaz on 5/8/2017.
 */

public interface MvpView {
    void showLoading();
    void hideLoading();
    void showOnSuccessSnackbar(String message);
    void showOnSuccessToast(String message);
    void showOnErrorSnackbar(String message);
    void showOnErrorToast(String message);
    boolean isNetworkConnected();
}
