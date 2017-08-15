package com.skripsigg.heyow.data.helper.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.anton46.collectionitempicker.Item;
import com.skripsigg.heyow.di.qualifier.AppContext;
import com.skripsigg.heyow.di.scope.AppScope;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.MatchQuota;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.utils.others.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Aldyaz on 5/9/2017.
 */

@AppScope
public class AppPreferencesHelper implements PreferencesHelper {
    public static final String PREF_NAME = "HeyowPref";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Inject
    public AppPreferencesHelper(@AppContext Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void putSharedPref(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    @Override
    public void putSharedPrefInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    @Override
    public void putSharedPrefLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    @Override
    public void putSharedPrefBoolean(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void putSharedPrefStringArray(String key, ArrayList<String> values) {
        for(int i  = 0; i < values.size(); i++){
            putSharedPref(key + "_" + Integer.toString(i), values.get(i));
        }
        putSharedPrefInt(key + "_size", values.size());
    }

    @Override
    public String getSharedPref(String key) {
        return preferences.getString(key, "");
    }

    @Override
    public void removeSharedPref(String key) {
        preferences.edit().remove(key).apply();
    }

    @Override
    public int getSharedPrefInt(String key) {
        return preferences.getInt(key, 0);
    }

    @Override
    public Long getSharedPrefLong(String key) {
        return preferences.getLong(key, 0);
    }

    @Override
    public Boolean getSharedPrefBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    @Override
    public Boolean getSharedPrefBoolean(String key, boolean defaultBoolean) {
        return preferences.getBoolean(key, defaultBoolean);
    }

    @Override
    public ArrayList<String> getSharedPrefStringArray(String key) {
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i < getSharedPrefInt(key + "_size"); i++){
            values.add(getSharedPref(key + "_" + Integer.toString(i)));
        }
        return values;
    }

    @Override
    public void clearSharedPrefs() {
        preferences.edit().clear().apply();
    }

    @Override
    public void removeSharedPrefByKey(String key) {
        preferences.edit().remove(key).apply();
    }

    @Override
    public boolean containsKey(String key) {
        return preferences.contains(key);
    }

    @Override
    public void putSharedPrefUser(UserModel user) {
        editor = preferences.edit();
        editor.putString(Constants.KEY_USER_ID, user.getUserId());
        editor.putString(Constants.KEY_USER_TOKEN, user.getUserToken());
        editor.putString(Constants.KEY_USER_NAME, user.getUserName());
        editor.putString(Constants.KEY_USER_EMAIL, user.getUserEmail());
        editor.putString(Constants.KEY_USER_PHONE_NUMBER, user.getUserPhoneNumber());
        editor.putString(Constants.KEY_USER_PROFILE_IMAGE, user.getUserProfileImage());
        editor.apply();
    }

    @Override
    public UserModel getSharedPrefUser() {
        String userId = preferences.getString(Constants.KEY_USER_ID, "");
        String userToken = preferences.getString(Constants.KEY_USER_TOKEN, "");
        String userName = preferences.getString(Constants.KEY_USER_NAME, "");
        String userEmail = preferences.getString(Constants.KEY_USER_EMAIL, "");
        String userPhoneNumber = preferences.getString(Constants.KEY_USER_PHONE_NUMBER, "");
        String userProfileImage = preferences.getString(Constants.KEY_USER_PROFILE_IMAGE, "");
        return new UserModel(userId, userToken, userName, userEmail, userPhoneNumber, userProfileImage);
    }

    @Override
    public void removeSharedPrefUser() {
        editor = preferences.edit();
        editor.remove(Constants.KEY_USER_ID);
        editor.remove(Constants.KEY_USER_TOKEN);
        editor.remove(Constants.KEY_USER_NAME);
        editor.remove(Constants.KEY_USER_EMAIL);
        editor.remove(Constants.KEY_USER_PHONE_NUMBER);
        editor.remove(Constants.KEY_USER_PROFILE_IMAGE);
        editor.apply();
    }

    @Override
    public void putSharedPrefCategoryMap(HashMap<String, Object> categoryMap) {
        editor = preferences.edit();
        editor.putStringSet(Constants.KEY_TEMP_CATEGORY_SET, categoryMap.keySet());
        editor.apply();
    }

    @Override
    public HashMap<String, Object> getSharedPrefCategoryMap() {
        Set<String> categorySet = preferences.getStringSet(Constants.KEY_TEMP_CATEGORY_SET, null);
        HashMap<String, Object> objectHashMap = new HashMap<>();
        if (categorySet != null) {
            for (String key : categorySet) {
                Item item = new Item(key, key);
                objectHashMap.put(key, item.text);
            }
        }
        return objectHashMap;
    }

    @Override
    public void removeSharedPrefCategoryMap() {
        editor = preferences.edit();
        editor.remove(Constants.KEY_TEMP_CATEGORY_SET).apply();
        editor.apply();
    }

    @Override
    public void putSharedPrefRadius(LocationRadiusModel locationRadiusModel) {
        editor = preferences.edit();
        editor.putString(Constants.KEY_TEMP_ADDRESS, locationRadiusModel.getAddressName());
        editor.putString(Constants.KEY_TEMP_LATITUDE, locationRadiusModel.getAddressLatitude());
        editor.putString(Constants.KEY_TEMP_LONGITUDE, locationRadiusModel.getAddressLongitude());
        editor.putInt(Constants.KEY_TEMP_DISTANCE, locationRadiusModel.getDistance());
        editor.apply();
    }

    @Override
    public LocationRadiusModel getSharedPrefRadius() {
        String addressName = preferences.getString(Constants.KEY_TEMP_ADDRESS, "");
        String addressLatitude = preferences.getString(Constants.KEY_TEMP_LATITUDE, "");
        String addressLongitude = preferences.getString(Constants.KEY_TEMP_LONGITUDE, "");
        int distance = preferences.getInt(Constants.KEY_TEMP_DISTANCE, 0);
        return new LocationRadiusModel(addressName, addressLatitude, addressLongitude, distance);
    }

    @Override
    public void putSharedPrefMatchDetail(MatchDetailResponse matchDetail) {
        editor = preferences.edit();
        editor.putString(Constants.POST_MATCH_CATEGORY, matchDetail.getMatchCategory());
        editor.putString(Constants.POST_MATCH_TITLE, matchDetail.getMatchTitle());
        editor.putString(Constants.POST_MATCH_DETAIL, matchDetail.getMatchDetail());
        editor.putString(Constants.POST_MATCH_LOCATION, matchDetail.getMatchLocation());
        editor.putString(Constants.POST_MATCH_VENUE, matchDetail.getMatchVenue());
        editor.putString(Constants.POST_MATCH_DATE, matchDetail.getMatchDate());
        editor.putString(Constants.POST_MATCH_TIME, matchDetail.getMatchTime());
        editor.putString(Constants.POST_MATCH_LATITUDE, matchDetail.getMatchLatitude());
        editor.putString(Constants.POST_MATCH_LONGITUDE, matchDetail.getMatchLongitude());
        editor.putString(Constants.POST_MATCH_QUOTA_MAX, matchDetail.getMatchQuota().getQuotaMax());
        editor.putString(Constants.POST_MATCH_IMAGE, matchDetail.getMatchImage());
        editor.apply();
    }

    @Override
    public MatchDetailResponse getSharedPrefMatchDetail() {
        MatchDetailResponse matchDetail = new MatchDetailResponse();
        MatchQuota matchQuota = new MatchQuota();
        matchQuota.setQuotaMax(preferences.getString(Constants.POST_MATCH_QUOTA_MAX, ""));
        matchDetail.setUserId(preferences.getString(Constants.POST_MATCH_USER_ID, ""));
        matchDetail.setMatchCategory(preferences.getString(Constants.POST_MATCH_CATEGORY, ""));
        matchDetail.setMatchTitle(preferences.getString(Constants.POST_MATCH_TITLE, ""));
        matchDetail.setMatchDetail(preferences.getString(Constants.POST_MATCH_DETAIL, ""));
        matchDetail.setMatchLocation(preferences.getString(Constants.POST_MATCH_LOCATION, ""));
        matchDetail.setMatchVenue(preferences.getString(Constants.POST_MATCH_VENUE, ""));
        matchDetail.setMatchDate(preferences.getString(Constants.POST_MATCH_DATE, ""));
        matchDetail.setMatchTime(preferences.getString(Constants.POST_MATCH_TIME, ""));
        matchDetail.setMatchLatitude(preferences.getString(Constants.POST_MATCH_LATITUDE, ""));
        matchDetail.setMatchLongitude(preferences.getString(Constants.POST_MATCH_LONGITUDE, ""));
        matchDetail.setMatchQuota(matchQuota);
        matchDetail.setMatchImage(preferences.getString(Constants.POST_MATCH_IMAGE, ""));
        return matchDetail;
    }

    @Override
    public void removeSharedPrefMatchDetail() {
        editor = preferences.edit();
        editor.remove(Constants.POST_MATCH_QUOTA_MAX);
        editor.remove(Constants.POST_MATCH_CATEGORY);
        editor.remove(Constants.POST_MATCH_TITLE);
        editor.remove(Constants.POST_MATCH_DETAIL);
        editor.remove(Constants.POST_MATCH_LOCATION);
        editor.remove(Constants.POST_MATCH_VENUE);
        editor.remove(Constants.POST_MATCH_DATE);
        editor.remove(Constants.POST_MATCH_TIME);
        editor.remove(Constants.POST_MATCH_LATITUDE);
        editor.remove(Constants.POST_MATCH_LONGITUDE);
        editor.remove(Constants.POST_MATCH_IMAGE);
        editor.apply();
    }
}
