package com.skripsigg.heyow.ui.userprofile;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 5/14/2017.
 */

@ActivityScope
public class UserProfilePresenter<V extends UserProfileMvpView> extends BasePresenter<V>
        implements UserProfileMvpPresenter<V> {
    @Inject
    public UserProfilePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public UserModel getUserPreferences() {
        return getDataManager().getPreferenceHelper().getSharedPrefUser();
    }
}
