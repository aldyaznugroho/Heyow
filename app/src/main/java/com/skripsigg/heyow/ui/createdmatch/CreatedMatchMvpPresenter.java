package com.skripsigg.heyow.ui.createdmatch;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/14/2017.
 */

public interface CreatedMatchMvpPresenter<V extends CreatedMatchMvpView> extends MvpPresenter<V> {
    void loadUserCreatedMatchApiService();
}
