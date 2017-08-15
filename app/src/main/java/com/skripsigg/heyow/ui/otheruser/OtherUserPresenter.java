package com.skripsigg.heyow.ui.otheruser;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 5/22/2017.
 */

@ActivityScope
public class OtherUserPresenter<V extends OtherUserMvpView> extends BasePresenter<V>
        implements OtherUserMvpPresenter<V> {
    @Inject
    public OtherUserPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
