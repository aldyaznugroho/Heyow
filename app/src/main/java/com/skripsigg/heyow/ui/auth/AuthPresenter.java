package com.skripsigg.heyow.ui.auth;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;
import com.skripsigg.heyow.utils.others.Constants;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 5/10/2017.
 */

@ActivityScope
public class AuthPresenter<V extends AuthMvpView> extends BasePresenter<V>
        implements AuthMvpPresenter<V> {

    @Inject
    public AuthPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getCompositeDisposable().add(handleGoogleLoginButtonClick());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                getMvpView().showLoading();
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                final String userFCMToken = FirebaseInstanceId.getInstance().getToken();
                final String userName = account.getDisplayName();
                final String userEmail = account.getEmail();
                final String userPhoneNumber = "";
                final String userProfileImage = String.valueOf(account.getPhotoUrl());

                FirebaseAuth.getInstance().signInWithCredential(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    getMvpView().hideLoading();
                                    Timber.e(String.valueOf(task.getException()));
                                } else {
                                    Map<String, String> formFields = new HashMap<>();
                                    formFields.put(Constants.KEY_USER_TOKEN, userFCMToken);
                                    formFields.put(Constants.KEY_USER_NAME, userName);
                                    formFields.put(Constants.KEY_USER_EMAIL, userEmail);
                                    formFields.put(Constants.KEY_USER_PHONE_NUMBER, userPhoneNumber);
                                    formFields.put(Constants.KEY_USER_PROFILE_IMAGE, userProfileImage);

                                    getCompositeDisposable().add(callCreateUserService(formFields));
                                }
                            }
                        });
            } else {
                getMvpView().showOnErrorToast("Google sign in failed");
            }
        }
    }

    private Disposable handleGoogleLoginButtonClick() {
        return getMvpView().googleLoginButtonClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getMvpView().startGoogleLoginIntent();
            }
        });
    }

    private Disposable callCreateUserService(Map<String, String> formFields) {
        return getDataManager().getHeyowApiService().createUser(formFields)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel userModel) throws Exception {
                        HashMap<String, Object> categoryMap =
                                getDataManager().getPreferenceHelper().getSharedPrefCategoryMap();

                        getDataManager().getPreferenceHelper().putSharedPrefUser(userModel);

                        if (categoryMap.keySet().size() == 0) {
                            getMvpView().openInterestActivity();
                        } else {
                            getMvpView().openHomeActivity();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
