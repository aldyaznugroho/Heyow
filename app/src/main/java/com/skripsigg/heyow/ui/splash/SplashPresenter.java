package com.skripsigg.heyow.ui.splash;

import android.location.Location;

import com.google.firebase.auth.FirebaseAuth;
import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.ui.base.BasePresenter;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.rx.ObservableFactory;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 5/13/2017.
 */

@ActivityScope
public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getCompositeDisposable().add(handleRequestPermission());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void startLocationService() {
        getCompositeDisposable().add(handleStartSmartLocationService());
    }

    @Override
    public void stopLocationService() {
        SmartLocation.with(getMvpView().getSplashContext()).location().stop();
        Timber.e("Location stopped");
    }

    private Disposable handleRequestPermission() {
        return Observable.just(getMvpView().checkIsHasPermission())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean isHasPermission) throws Exception {
                        if (!isHasPermission) {
                            getMvpView().requestPermission();
                        } else {
                            startLocationService();
                        }
                    }
                });
    }

    private Disposable handleStartSmartLocationService() {
        return ObservableFactory.from(
                SmartLocation.with(getMvpView().getSplashContext()).location())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(@NonNull Location location) throws Exception {
                        Timber.v("Latitude: " + location.getLatitude());
                        Timber.v("Longitude: " + location.getLongitude());

                        handleLatLngFetch(location);
                    }
                });
    }

    private void handleLatLngFetch(Location location) {
        HashMap<String, Object> categoryMap =
                getDataManager().getPreferenceHelper().getSharedPrefCategoryMap();
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        LocationRadiusModel locationRadiusModel = new LocationRadiusModel();
        locationRadiusModel.setAddressLatitude(latitude);
        locationRadiusModel.setAddressLongitude(longitude);
        locationRadiusModel.setAddressName("");

        int tempDistance =
                getDataManager().getPreferenceHelper().getSharedPrefRadius().getDistance();

        if (tempDistance == 0) {
            // If distance not exist.
            locationRadiusModel.setDistance(25);
            getDataManager().getPreferenceHelper().putSharedPrefRadius(locationRadiusModel);
        } else {
            // If distance already exist.
            locationRadiusModel.setDistance(tempDistance);
            getDataManager().getPreferenceHelper().putSharedPrefRadius(locationRadiusModel);
        }

        getCompositeDisposable().add(handleDecideNextActivity(categoryMap));
    }

    private Disposable handleDecideNextActivity(final HashMap<String, Object> categoryMap) {
        return Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (categoryMap.keySet().size() == 0) {
                        getMvpView().openInterestActivity();
                    } else {
                        getMvpView().openHomeActivity();
                    }
                } else {
                    getMvpView().openAuthActivity();
                }
            }
        });
    }
}
