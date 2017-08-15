package com.skripsigg.heyow.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.database.FirebaseDatabase;
import com.skripsigg.heyow.BuildConfig;
import com.skripsigg.heyow.di.component.AppComponent;
import com.skripsigg.heyow.di.component.DaggerAppComponent;
import com.skripsigg.heyow.di.module.AppModule;

import timber.log.Timber;

/**
 * Created by Aldyaz on 10/6/2016.
 */

public class HeyowApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        MultiDex.install(this);
    }

    public static HeyowApp get(Context context) {
        return (HeyowApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }
}
