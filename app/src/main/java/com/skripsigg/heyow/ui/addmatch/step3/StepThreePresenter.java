package com.skripsigg.heyow.ui.addmatch.step3;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BasePresenter;

import java.net.SocketTimeoutException;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 6/1/2017.
 */

@ActivityScope
public class StepThreePresenter<V extends StepThreeMvpView> extends BasePresenter<V>
        implements StepThreeMvpPresenter<V> {
    @Inject
    public StepThreePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public String getUserIdPref() {
        return getDataManager().getPreferenceHelper().getSharedPrefUser().getUserId();
    }

    @Override
    public MatchDetailResponse getMatchFormPref() {
        return getDataManager().getPreferenceHelper().getSharedPrefMatchDetail();
    }

    @Override
    public void loadCreateMatchApiService(Map<String, String> formFields) {
        Timber.v("Form submitted: " + formFields.size());
        getCompositeDisposable().add(callAddMatchService(formFields));
    }

    private Disposable callAddMatchService(Map<String, String> formFields) {
        return getDataManager().getHeyowApiService().addMatch(formFields)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<MatchDetailResponse>() {
                    @Override
                    public void accept(@NonNull MatchDetailResponse matchDetailResponse) throws Exception {
                        Timber.v(matchDetailResponse.getMatchId());

                        getDataManager().getPreferenceHelper().removeSharedPrefMatchDetail();
                        getMvpView().openMatchDetailActivity(matchDetailResponse.getMatchId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().hideLoading();

                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView().showOnErrorToast("Request timeout. Please try again");
                        } else {
                            getMvpView().showOnErrorToast(
                                    "Sorry, we couldn't complete your request. " +
                                            "Please try again in a moment");
                        }

                        Timber.e("ERROR: " + throwable.getLocalizedMessage());
                        Timber.e("CAUSE: " + throwable.getCause());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideLoading();
                    }
                });
    }
}
