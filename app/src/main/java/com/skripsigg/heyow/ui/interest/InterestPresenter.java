package com.skripsigg.heyow.ui.interest;

import com.anton46.collectionitempicker.Item;
import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.FeedMatchResponse;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BasePresenter;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class InterestPresenter<V extends InterestMvpView> extends BasePresenter<V>
        implements InterestMvpPresenter<V> {
    @Inject
    public InterestPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void loadInterestApiService() {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(callInterestApiService());
    }

    private Disposable callInterestApiService() {
        HashMap<String, Object> categoryMap = getDataManager().getPreferenceHelper().getSharedPrefCategoryMap();
        for (String key : categoryMap.keySet()) {
            Item item = new Item(key, key);
            categoryMap.put(key, item.id);
        }
        List<Object> categoryList = new ArrayList<>(categoryMap.values());
        return getDataManager().getHeyowApiService().getInterest(categoryList)
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
