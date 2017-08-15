package com.skripsigg.heyow.ui.addmatch.step1;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;
import com.stepstone.stepper.VerificationError;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 5/31/2017.
 */

@ActivityScope
public class StepOnePresenter<V extends StepOneMvpView> extends BasePresenter<V>
        implements StepOneMvpPresenter<V> {

    @Inject
    public StepOnePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getCompositeDisposable().add(handleImageUploadLayoutClick());
    }

    @Override
    public void saveFormToPreferences(String selectedImage, String title, Integer selectedCategory) {
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_IMAGE,
                selectedImage);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_TITLE,
                title);
        getDataManager().getPreferenceHelper().putSharedPref(Constants.POST_MATCH_CATEGORY,
                String.valueOf(selectedCategory));
    }

    @Override
    public void saveFormDelayProcessing(Consumer<Long> consumer) {
        getCompositeDisposable().add(handleSavedFormDelayProcessing(consumer));
    }

    private Disposable handleImageUploadLayoutClick() {
        return getMvpView().ImageUploadLayoutClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                getCompositeDisposable().add(handleRequestPermission());
            }
        });
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
                            getMvpView().showImagePickerDialog();
                        }
                    }
                });
    }

    private Disposable handleSavedFormDelayProcessing(Consumer<Long> consumer) {
        return Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(consumer);
    }
}
