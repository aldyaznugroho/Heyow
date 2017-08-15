package com.skripsigg.heyow.ui.createdmatch;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/22/2017.
 */

public interface CreatedMatchMvpFragmentPresenter<V extends CreatedMatchMvpFragmentView>
        extends MvpPresenter<V> {
    void loadUserCreatedMatchApiService();
}
