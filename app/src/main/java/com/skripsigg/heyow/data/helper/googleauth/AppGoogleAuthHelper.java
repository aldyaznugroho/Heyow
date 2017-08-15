package com.skripsigg.heyow.data.helper.googleauth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.AppScope;
import com.skripsigg.heyow.utils.others.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Aldyaz on 5/11/2017.
 */

public class AppGoogleAuthHelper {

    Context context;
    GoogleSignInResult googleSignInResult;
    GoogleApiClient googleApiClient;

    public AppGoogleAuthHelper(GoogleSignInResult googleSignInResult,
                               GoogleApiClient googleApiClient) {
        this.googleSignInResult = googleSignInResult;
        this.googleApiClient = googleApiClient;
    }

    /*@Override
    public GoogleSignInResult getGoogleSignInResultData(Intent data) {
        return Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    }

    @Override
    public AuthCredential getAuthCredential(@NonNull String idToken) {
        return GoogleAuthProvider.getCredential(idToken, null);
    }

    @Override
    public Observable<Task<AuthResult>> getGoogleAuthResultTask(
            final AuthCredential authCredential) {
        return Observable.create(new ObservableOnSubscribe<Task<AuthResult>>() {
            @Override
            public void subscribe(final ObservableEmitter<Task<AuthResult>> e) throws Exception {
                OnCompleteListener<AuthResult> authResultOnCompleteListener =
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                e.onNext(task);
                            }
                        };
                FirebaseAuth.getInstance().signInWithCredential(authCredential)
                        .addOnCompleteListener(appCompatActivity, authResultOnCompleteListener);
            }
        });
    }*/
}
