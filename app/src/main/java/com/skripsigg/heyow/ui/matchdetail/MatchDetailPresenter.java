package com.skripsigg.heyow.ui.matchdetail;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.ResponseMessage;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import java.net.SocketTimeoutException;
import java.util.HashMap;
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
 * Created by Aldyaz on 5/17/2017.
 */

@ActivityScope
public class MatchDetailPresenter<V extends MatchDetailMvpView> extends BasePresenter<V>
        implements MatchDetailMvpPresenter<V> {

    private MatchDetailResponse matchDetailResponseResult;

    @Inject
    public MatchDetailPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getMvpView().showProgressBar();
        getCompositeDisposable().add(callMatchDetailService());
        getCompositeDisposable().add(handleChatFabClick());
        getCompositeDisposable().add(handleMatchJoinButtonClick());
        getCompositeDisposable().add(handleMatchLeaveButtonClick());
        /*getCompositeDisposable().add(handleMatchEditButtonClick());
        getCompositeDisposable().add(handleMatchDeleteButtonClick());*/
    }

    @Override
    public void loadMatchDetailApiService() {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(callMatchDetailService());
    }

    @Override
    public void loadLeaveMatchApiService() {
        getCompositeDisposable().add(callLeaveMatchService());
    }

    @Override
    public void loadDeleteMatchApiService() {
        getCompositeDisposable().add(callDeleteMatchService());
    }

    @Override
    public MatchDetailResponse getMatchDetailResponseResult() {
        return matchDetailResponseResult;
    }

    private Disposable handleMapPreviewClick(final String latitude, final String longitude) {
        return getMvpView().mapPreviewClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().openMapsDetailActivity(latitude, longitude);
            }
        });
    }

    private Disposable handleChatFabClick() {
        return getMvpView().chatFloatingButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().openMatchChatActivity();
            }
        });
    }

    private Disposable handleMatchJoinButtonClick() {
        return getMvpView().matchJoinButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().showLoading();
                getCompositeDisposable().add(callJoinMatchService());
            }
        });
    }

    private Disposable handleMatchLeaveButtonClick() {
        return getMvpView().matchLeaveButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().showLeaveMatchAlertDialog();
            }
        });
    }

    private Disposable handleMatchEditButtonClick() {
        return getMvpView().matchEditButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().showOnErrorToast("Not available");
            }
        });
    }

    private Disposable handleMatchDeleteButtonClick() {
        return getMvpView().matchDeleteButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getMvpView().showDeleteMatchAlertDialog();
            }
        });
    }

    private Disposable callMatchDetailService() {
        String matchId = getMvpView().getMatchIdStringExtra();
        return getDataManager().getHeyowApiService().matchById(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<MatchDetailResponse>() {
                    @Override
                    public void accept(@NonNull MatchDetailResponse matchDetailResponse) throws Exception {
                        UserModel user = getDataManager().getPreferenceHelper().getSharedPrefUser();

                        getMvpView().showMatchDetailLayout();

                        if (!matchDetailResponse.getUserId().equals(user.getUserId()))
                            getMvpView().hideDeleteLayout();

                        for (UserModel checkUid : matchDetailResponse.getJoinedUser()) {
                            if (checkUid.getUserId().equalsIgnoreCase(user.getUserId())) {
                                getMvpView().showUserJoinedMainLayout();
                                break;
                            } else {
                                getMvpView().showUserNotJoinedMainLayout();
                            }
                        }

                        getMvpView().bindDataToMainMatchDetail(matchDetailResponse);
                        getCompositeDisposable().add(handleMapPreviewClick(
                                matchDetailResponse.getMatchLatitude(),
                                matchDetailResponse.getMatchLongitude()));
                        matchDetailResponseResult = matchDetailResponse;
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
                            getMvpView().setOfflineText("Ooops.. there is something wrong");
                            getMvpView().showOnErrorToast(
                                    "Sorry, we couldn't complete your request. " +
                                            "Please try again in a moment");
                        }

                        Timber.e("ERROR: " + throwable.getLocalizedMessage());
                        Timber.e("CAUSE: " + throwable.getCause());
                    }
                });
    }

    private Disposable callJoinMatchService() {
        String userId = getDataManager().getPreferenceHelper().getSharedPrefUser().getUserId();
        String matchId = getMvpView().getMatchIdStringExtra();
        Map<String, String> joinFormFields = new HashMap<>();
        joinFormFields.put(Constants.KEY_USER_ID, userId);
        joinFormFields.put(Constants.KEY_MATCH_ID, matchId);
        return getDataManager().getHeyowApiService().joinMatch(joinFormFields)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(@NonNull ResponseMessage responseMessage) throws Exception {
                        if (responseMessage.getMessage().equalsIgnoreCase("success")) {
                            getMvpView().showOnSuccessSnackbar("Join success");
                            getMvpView().openJoinedMatchActivity();
                        } else {
                            getMvpView().showOnSuccessToast("You already join this match");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView()
                                    .showOnErrorToast("Request timeout. Please try again");
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

    private Disposable callLeaveMatchService() {
        String userId = getDataManager().getPreferenceHelper().getSharedPrefUser().getUserId();
        String matchId = getMvpView().getMatchIdStringExtra();
        Map<String, String> joinFormFields = new HashMap<>();
        joinFormFields.put(Constants.KEY_USER_ID, userId);
        joinFormFields.put(Constants.KEY_MATCH_ID, matchId);
        return getDataManager().getHeyowApiService().leaveMatch(joinFormFields)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(@NonNull ResponseMessage responseMessage) throws Exception {
                        if (responseMessage.getResult()
                                .equalsIgnoreCase("you can't leave created match by you!")) {
                            getMvpView().showOnErrorToast("You cannot leave match that you created");
                        } else if (responseMessage.getResult().equalsIgnoreCase("user not exist")) {
                            getMvpView().showOnErrorToast("User not exist");
                        } else {
                            getMvpView().showOnSuccessToast("You have leave this match");
                            getMvpView().finishActivity();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView()
                                    .showOnErrorToast("Request timeout. Please try again");
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

    private Disposable callDeleteMatchService() {
        String matchId = getMvpView().getMatchIdStringExtra();
        return getDataManager().getHeyowApiService().deleteMatch(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(@NonNull ResponseMessage responseMessage) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView()
                                    .showOnErrorToast("Request timeout. Please try again");
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
