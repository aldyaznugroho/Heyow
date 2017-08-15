package com.skripsigg.heyow.ui.addmatch;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 6/1/2017.
 */

public interface AddMatchMvpPresenter<V extends AddMatchMvpView> extends MvpPresenter<V> {
    void removeFormPreferences();
}
