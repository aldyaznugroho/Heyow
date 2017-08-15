package com.skripsigg.heyow.ui.addmatch.step2;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aldyaz on 6/1/2017.
 */

@ActivityScope
public class StepTwoPresenter<V extends StepTwoMvpView> extends BasePresenter<V>
        implements StepTwoMvpPresenter<V> {
    @Inject
    public StepTwoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void saveFormToPreferences(String matchVenueName,
                                      String matchVenueAddress,
                                      String tempLat,
                                      String tempLng,
                                      String matchDate,
                                      String matchTime,
                                      String matchMaxPeople) {
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_VENUE,
                matchVenueName);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_LOCATION,
                matchVenueAddress);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_LATITUDE,
                tempLat);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_LONGITUDE,
                tempLng);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_DATE,
                matchDate);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_TIME,
                matchTime);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_QUOTA_MAX,
                matchMaxPeople);
    }

    @Override
    public void saveFormDelayProcessing(Consumer<Long> consumer) {
        getCompositeDisposable().add(handleSavedFormDelayProcessing(consumer));
    }

    private Disposable handleSavedFormDelayProcessing(Consumer<Long> consumer) {
        return Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(consumer);
    }
}
