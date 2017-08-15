package com.skripsigg.heyow.ui.selectinterest;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.base.BasePresenter;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Aldyaz on 5/13/2017.
 */

@ActivityScope
public class SelectInterestPresenter<V extends SelectInterestMvpView> extends BasePresenter<V>
        implements SelectInterestMvpPresenter<V> {

    @Inject
    public SelectInterestPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public HashMap<String, Object> getSharedPrefCategoryMap() {
        return getDataManager().getPreferenceHelper().getSharedPrefCategoryMap();
    }

    @Override
    public void saveSelectedInterest() {
        getDataManager().getPreferenceHelper().putSharedPrefCategoryMap(getMvpView().getCollectionPickerCheckedItems());
        if (getMvpView().getCollectionPickerCheckedItems().keySet().size() < 3) {
            getMvpView().showOnErrorToast("Please choose at least 3 category");
        } else {
            decideNextActivity();
        }
    }

    private void decideNextActivity() {
        if (getMvpView().getIntentInterestFlag() == 1001) getMvpView().openHomeActivity();
        else getMvpView().openIntentWithResultOk();
    }
}
