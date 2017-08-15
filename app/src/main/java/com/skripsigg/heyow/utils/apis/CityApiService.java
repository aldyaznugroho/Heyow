package com.skripsigg.heyow.utils.apis;

import com.skripsigg.heyow.models.test.CityListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aldyaz on 11/4/2016.
 */

public interface CityApiService {
    @GET("v1/city")
    Call<CityListResponse> getCityList();
}
