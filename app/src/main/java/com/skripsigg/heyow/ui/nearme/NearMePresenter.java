package com.skripsigg.heyow.ui.nearme;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.FeedMatchResponse;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 6/6/2017.
 */

@ActivityScope
public class NearMePresenter<V extends NearMeMvpView> extends BasePresenter<V>
        implements NearMeMvpPresenter<V> {
    @Inject
    public NearMePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getMvpView().isDataLoadToView(false);
    }

    @Override
    public void loadNearMeApiService() {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(callNearMeApiService());
    }

    private Disposable callNearMeApiService() {
        LocationRadiusModel locationRadius = getDataManager().getPreferenceHelper().getSharedPrefRadius();
        Map<String, String> optionsQuery = new HashMap<>();
        optionsQuery.put(Constants.NEAR_ME_LATITUDE, locationRadius.getAddressLatitude());
        optionsQuery.put(Constants.NEAR_ME_LONGITUDE, locationRadius.getAddressLongitude());
        optionsQuery.put(Constants.NEAR_ME_DISTANCE, String.valueOf(locationRadius.getDistance()));
        return getDataManager().getHeyowApiService().getNearMe(optionsQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<FeedMatchResponse>() {
                    @Override
                    public void accept(@NonNull FeedMatchResponse feedMatchResponse) throws Exception {
                        List<MatchDetailResponse> matchList = feedMatchResponse.getResult();

                        if (matchList != null && matchList.size() > 0) {
                            if (feedMatchResponse.getMessage().equals("success")) {
                                getMvpView().showMainLayout();
                                getMvpView().bindDataToRecyclerView(matchList);
                            } else {
                                getMvpView().showNotFoundLayout();
                            }
                        }

                        getMvpView().setSwipeRefreshNotActive();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().showRetryLayout();

                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView().setOfflineText("Request timeout");
                            getMvpView().showOnErrorToast("Request timeout. Please try again in a moment");
                        } else if (!getMvpView().isNetworkConnected()) {
                            getMvpView().setOfflineText("You are offline…");
                        } else {
                            getMvpView().setOfflineText("Ooops.. there is an error");
                            getMvpView().showOnErrorToast(
                                    "Sorry, we couldn't complete your request. " +
                                            "Please try again in a moment");
                        }

                        Timber.e("ERROR: " + throwable.getLocalizedMessage());
                        Timber.e("CAUSE: " + throwable.getCause());

                        getMvpView().setSwipeRefreshNotActive();
                    }
                });
    }
}
