package com.skripsigg.heyow.data.helper.googleauth;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/11/2017.
 */

public interface GoogleAuthHelper {
    GoogleSignInResult getGoogleSignInResultData(Intent data);
    AuthCredential getAuthCredential(String idToken);
    Observable<Task<AuthResult>> getGoogleAuthResultTask(AuthCredential authCredential);
}
