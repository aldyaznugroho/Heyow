package com.skripsigg.heyow.di.component;

import android.app.Application;
import android.content.Context;

import com.skripsigg.heyow.application.HeyowApp;
import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.module.AppModule;
import com.skripsigg.heyow.di.module.HeyowServiceModule;
import com.skripsigg.heyow.di.module.NetworkModule;
import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.AppScope;

import dagger.Component;

/**
 * Created by Aldyaz on 5/8/2017.
 */

@AppScope
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        HeyowServiceModule.class})
public interface AppComponent {
    void inject(HeyowApp heyowApp);

    @AppContext Context context();
    Application application();
    DataManager dataManager();
}
