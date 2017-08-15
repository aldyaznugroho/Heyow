package com.skripsigg.heyow.ui.selectradius;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Aldyaz on 5/17/2017.
 */

@ActivityScope
public class SelectRadiusPresenter<V extends SelectRadiusMvpView> extends BasePresenter<V>
        implements SelectRadiusMvpPresenter<V> {

    @Inject
    public SelectRadiusPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getCompositeDisposable().add(checkSavedDistanceValueFlag());
        getCompositeDisposable().add(handleUserSeekbarDebounce());
        getCompositeDisposable().add(handleSeekbarChange());
    }

    @Override
    public int getDistancePreferences() {
        return getDataManager().getPreferenceHelper().getSharedPrefInt(Constants.KEY_TEMP_DISTANCE);
    }

    @Override
    public void saveDistanceValueToPreferences() {
        getDataManager().getPreferenceHelper()
                .putSharedPrefInt(Constants.KEY_TEMP_DISTANCE, getMvpView().getSeekbarValue());
    }

    private Disposable handleUserSeekbarDebounce() {
        return getMvpView().userSeekbarDebounce().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer value) throws Exception {
                if (value < 1) {
                    getMvpView().setSeekbarProgress(1);
                } else if(value > 100) {
                    getMvpView().setSeekbarProgress(100);
                } else {
                    getMvpView().setSeekbarProgress(value);
                }
            }
        });
    }

    private Disposable handleSeekbarChange() {
        return getMvpView().seekBarChange().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer value) throws Exception {
                getMvpView().setDistanceText(value);
            }
        });
    }

    private Disposable checkSavedDistanceValueFlag() {
        return Observable.just(getDistancePreferences()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer value) throws Exception {
                if (value != 0) {
                    getMvpView().setSeekbarProgress(value);
                    getMvpView().setDistanceText(value);
                }
            }
        });
    }
}
