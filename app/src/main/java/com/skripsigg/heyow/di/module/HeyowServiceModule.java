package com.skripsigg.heyow.di.module;

import com.skripsigg.heyow.di.scope.AppScope;
import com.skripsigg.heyow.data.helper.heyow.HeyowApiService;
import com.skripsigg.heyow.utils.others.Constants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aldyaz on 5/10/2017.
 */

@Module
public class HeyowServiceModule {
    private static final String apiEndpoint = Constants.HEYOW_BASE_URL;

    @AppScope
    @Provides
    HeyowApiService provideHeyowApiService(@Named("heyow_retrofit_service") Retrofit retrofit) {
        return retrofit.create(HeyowApiService.class);
    }

    @AppScope
    @Named("heyow_retrofit_service")
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
