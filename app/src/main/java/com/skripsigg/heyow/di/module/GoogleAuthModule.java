package com.skripsigg.heyow.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.di.qualifier.ActivityContext;
import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aldyaz on 5/10/2017.
 */

@Module
public class GoogleAuthModule {
    @ActivityScope
    @Provides
    GoogleSignInOptions provideGoogleSignInOptions(@ActivityContext Context context) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @ActivityScope
    @Provides
    GoogleApiClient provideGoogleApiClient(@ActivityContext Context context,
                                           GoogleSignInOptions googleSignInOptions) {
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }
}
