package com.skripsigg.heyow.ui.searchmatch;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.base.BasePresenter;

import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 6/6/2017.
 */

@ActivityScope
public class SearchMatchPresenter<V extends SearchMatchMvpView> extends BasePresenter<V>
        implements SearchMatchMvpPresenter<V> {
    @Inject
    public SearchMatchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
