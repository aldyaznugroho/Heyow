package com.skripsigg.heyow.ui.userprofile;

import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/14/2017.
 */

public interface UserProfileMvpPresenter<V extends UserProfileMvpView> extends MvpPresenter<V> {
    UserModel getUserPreferences();
}
