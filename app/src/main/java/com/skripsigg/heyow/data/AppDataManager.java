package com.skripsigg.heyow.data;

import com.skripsigg.heyow.data.helper.heyow.HeyowApiService;
import com.skripsigg.heyow.data.helper.preferences.PreferencesHelper;
import com.skripsigg.heyow.di.scope.AppScope;

import javax.inject.Inject;

/**
 * Created by Aldyaz on 5/9/2017.
 */

@AppScope
public class AppDataManager implements DataManager {
    private final HeyowApiService heyowApiService;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(
            HeyowApiService heyowApiService,
            PreferencesHelper preferencesHelper) {
        this.heyowApiService = heyowApiService;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public HeyowApiService getHeyowApiService() {
        return heyowApiService;
    }

    @Override
    public PreferencesHelper getPreferenceHelper() {
        return preferencesHelper;
    }
}
