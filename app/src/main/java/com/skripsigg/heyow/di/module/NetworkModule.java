package com.skripsigg.heyow.di.module;

import android.content.Context;

import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.AppScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Aldyaz on 5/8/2017.
 */

@Module
public class NetworkModule {
    @AppScope
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
    }

    @AppScope
    @Provides
    Cache provideOkHttpCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @AppScope
    @Provides
    File provideCacheFile(@AppContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @AppScope
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
