package com.skripsigg.heyow.data.helper.preferences;

import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aldyaz on 5/9/2017.
 */

public interface PreferencesHelper {
    void putSharedPref(String key, String value);
    void putSharedPrefInt(String key, int value);
    void putSharedPrefLong(String key, long value);
    void putSharedPrefBoolean(String key, Boolean value);
    void putSharedPrefStringArray(String key, ArrayList<String> values);
    String getSharedPref(String key);
    void removeSharedPref(String key);
    int getSharedPrefInt(String key);
    Long getSharedPrefLong(String key);
    Boolean getSharedPrefBoolean(String key);
    Boolean getSharedPrefBoolean(String key, boolean defaultBoolean);
    ArrayList<String> getSharedPrefStringArray(String key);
    void clearSharedPrefs();
    void removeSharedPrefByKey(String key);
    boolean containsKey(String key);
    void putSharedPrefUser(UserModel user);
    UserModel getSharedPrefUser();
    void removeSharedPrefUser();
    void putSharedPrefCategoryMap(HashMap<String, Object> categoryMap);
    HashMap<String, Object> getSharedPrefCategoryMap();
    void removeSharedPrefCategoryMap();
    void putSharedPrefRadius(LocationRadiusModel locationRadiusModel);
    LocationRadiusModel getSharedPrefRadius();
    void putSharedPrefMatchDetail(MatchDetailResponse matchDetail);
    MatchDetailResponse getSharedPrefMatchDetail();
    void removeSharedPrefMatchDetail();
}
