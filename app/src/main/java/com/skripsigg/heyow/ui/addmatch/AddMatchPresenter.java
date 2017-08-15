package com.skripsigg.heyow.ui.addmatch;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 6/1/2017.
 */

@ActivityScope
public class AddMatchPresenter<V extends AddMatchMvpView> extends BasePresenter<V>
        implements AddMatchMvpPresenter<V> {
    @Inject
    public AddMatchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void removeFormPreferences() {
        getDataManager().getPreferenceHelper().removeSharedPrefMatchDetail();
    }
}
