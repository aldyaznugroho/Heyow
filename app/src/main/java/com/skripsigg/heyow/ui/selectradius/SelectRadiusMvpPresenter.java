package com.skripsigg.heyow.ui.selectradius;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/17/2017.
 */

public interface SelectRadiusMvpPresenter<V extends SelectRadiusMvpView> extends MvpPresenter<V> {
    int getDistancePreferences();
    void saveDistanceValueToPreferences();
}
