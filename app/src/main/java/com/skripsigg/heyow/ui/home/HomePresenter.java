package com.skripsigg.heyow.ui.home;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.ResponseMessage;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 6/6/2017.
 */

@ActivityScope
public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {
    @Inject
    public HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public UserModel getUserPref() {
        return getDataManager().getPreferenceHelper().getSharedPrefUser();
    }

    @Override
    public LocationRadiusModel getLocationRadiusPref() {
        return getDataManager().getPreferenceHelper().getSharedPrefRadius();
    }

    @Override
    public int getDistancePreferences() {
        return getDataManager().getPreferenceHelper().getSharedPrefInt(Constants.KEY_TEMP_DISTANCE);
    }

    @Override
    public void removeUserPref() {
        getDataManager().getPreferenceHelper().removeSharedPrefUser();
    }

    @Override
    public void removeCategoryMapPref() {
        getDataManager().getPreferenceHelper().removeSharedPrefCategoryMap();
    }

    @Override
    public void loadLogoutApiService() {
        getMvpView().showLoading();
        getCompositeDisposable().add(callLogoutApiService());
    }

    private Disposable callLogoutApiService() {
        UserModel user = getDataManager().getPreferenceHelper().getSharedPrefUser();
        return getDataManager().getHeyowApiService().logoutUser(user.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(@NonNull ResponseMessage responseMessage) throws Exception {
                        if (responseMessage.getMessage().equals("success")) {
                            getMvpView().clearSession();
                        } else {
                            getMvpView().showOnErrorToast(
                                    "Sorry, we couldn't complete your request. " +
                                            "Please try again in a moment");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().hideLoading();

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
