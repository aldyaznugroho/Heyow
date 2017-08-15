package com.skripsigg.heyow.ui.home;

import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {
    UserModel getUserPref();
    LocationRadiusModel getLocationRadiusPref();
    int getDistancePreferences();
    void removeUserPref();
    void removeCategoryMapPref();
    void loadLogoutApiService();
}
