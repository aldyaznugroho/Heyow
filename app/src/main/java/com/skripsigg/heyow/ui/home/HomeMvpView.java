package com.skripsigg.heyow.ui.home;

import com.skripsigg.heyow.ui.base.MvpView;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface HomeMvpView extends MvpView {
    void clearSession();
    void openAuthActivity();
}
