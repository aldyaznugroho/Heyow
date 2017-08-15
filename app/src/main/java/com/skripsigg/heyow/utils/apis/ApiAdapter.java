package com.skripsigg.heyow.utils.apis;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aldyaz on 11/4/2016.
 */

public class ApiAdapter {
    private static Retrofit retrofit;

    public static ApiAdapter getInstance() {
        return new ApiAdapter();
    }

    public Retrofit getRetrofit(String urlEndpoint) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlEndpoint)
                .build();
        return retrofit;
    }

    public Retrofit getRetrofitWithRx(String urlEndpoint) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(urlEndpoint)
                .build();
        return retrofit;
    }
}
