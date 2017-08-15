package com.skripsigg.heyow.di.module;

import android.app.Application;
import android.content.Context;

import com.skripsigg.heyow.data.AppDataManager;
import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.data.helper.preferences.AppPreferencesHelper;
import com.skripsigg.heyow.data.helper.preferences.PreferencesHelper;
import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aldyaz on 5/9/2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @AppScope
    @Provides
    Application provideApplication() {
        return application;
    }

    @AppScope
    @AppContext
    @Provides
    Context provideContext() {
        return application;
    }

    @AppScope
    @Provides
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @AppScope
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
