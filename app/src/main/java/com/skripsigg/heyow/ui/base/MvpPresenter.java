package com.skripsigg.heyow.ui.base;

/**
 * Created by Aldyaz on 5/8/2017.
 */

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);
    void onDetach();
}
