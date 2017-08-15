package com.skripsigg.heyow.ui.createdmatch;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.HistoryModel;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BasePresenter;

import java.net.SocketTimeoutException;
import java.util.List;

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
 * Created by Aldyaz on 5/22/2017.
 */

@ActivityScope
public class CreatedMatchFragmentPresenter<V extends CreatedMatchMvpFragmentView>
        extends BasePresenter<V> implements CreatedMatchMvpFragmentPresenter<V> {
    @Inject
    public CreatedMatchFragmentPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        loadUserCreatedMatchApiService();
    }

    @Override
    public void loadUserCreatedMatchApiService() {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(callUserCreatedMatchApiService());
    }

    private Disposable callUserCreatedMatchApiService() {
        return getDataManager().getHeyowApiService().createdJoinedMatch(
                getMvpView().getJoinedUserParcelable().getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<HistoryModel>() {
                    @Override
                    public void accept(@NonNull HistoryModel historyModel)
                            throws Exception {
                        List<MatchDetailResponse> matchList =
                                historyModel.getUserCreatedMatch();

                        if (matchList != null && matchList.size() > 0) {
                            getMvpView().showRecyclerList();
                            getMvpView().bindDataToRecyclerView(matchList);
                        } else {
                            getMvpView().showNotFoundLayout();
                        }
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
                    }
                });
    }
}
