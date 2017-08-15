package com.skripsigg.heyow.utils.others;

import android.content.Context;
        import android.content.SharedPreferences;

import com.anton46.collectionitempicker.Item;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.models.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SharedPreferenceCustom {

    public static final String PREFS_NAME = "SHARED_PREF";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedPreferenceCustom instance = null;

    private SharedPreferenceCustom(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceCustom getInstance(Context context) {
        if(instance == null)
            instance = new SharedPreferenceCustom(context);
        return instance;
    }

    public void putSharedPref(String key, String value) {
        this.sharedPreferences.edit().putString(key, value).apply();
    }

    public void putSharedPrefInt(String key, int value) {
        this.sharedPreferences.edit().putInt(key, value).apply();
    }


    public void putSharedPrefLong(String key, long value) {
        this.sharedPreferences.edit().putLong(key, value).apply();
    }


    public void putSharedPrefBoolean(String key, Boolean value) {
        this.sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putSharedPrefStringArray(String key, ArrayList<String> values) {
        for(int i  = 0; i < values.size(); i++){
            putSharedPref(key + "_" + Integer.toString(i), values.get(i));
        }
        putSharedPrefInt(key + "_size", values.size());
    }

    public String getSharedPref(String key) {
        return this.sharedPreferences.getString(key, "");
    }

    public void removeSharedPref(String key) {
        this.sharedPreferences.edit().remove(key).apply();
    }

    public int getSharedPrefInt(String key) {
        return this.sharedPreferences.getInt(key, 0);
    }

    public Long getSharedPrefLong(String key) {
        return this.sharedPreferences.getLong(key, 0);
    }

    public Boolean getSharedPrefBoolean(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public Boolean getSharedPrefBoolean(String key, boolean defaultBoolean) {
        return this.sharedPreferences.getBoolean(key, defaultBoolean);
    }

    public ArrayList<String> getSharedPrefStringArray(String key) {
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i < getSharedPrefInt(key + "_size"); i++){
            values.add(getSharedPref(key + "_" + Integer.toString(i)));
        }
        return values;
    }

    public void clearSharedPrefs(){
        this.sharedPreferences.edit().clear().apply();
    }

    public void removeSharedPrefByKey(String key) {
        this.sharedPreferences.edit().remove(key).apply();
    }

    public boolean containsKey(String key){
        return this.sharedPreferences.contains(key);
    }

    public void putSharedPrefUser(UserModel user) {
        editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_USER_ID, user.getUserId());
        editor.putString(Constants.KEY_USER_TOKEN, user.getUserToken());
        editor.putString(Constants.KEY_USER_NAME, user.getUserName());
        editor.putString(Constants.KEY_USER_EMAIL, user.getUserEmail());
        editor.putString(Constants.KEY_USER_PHONE_NUMBER, user.getUserPhoneNumber());
        editor.putString(Constants.KEY_USER_PROFILE_IMAGE, user.getUserProfileImage());
        editor.apply();
    }

    public UserModel getSharedPrefUser() {
        String userId = sharedPreferences.getString(Constants.KEY_USER_ID, "");
        String userToken = sharedPreferences.getString(Constants.KEY_USER_TOKEN, "");
        String userName = sharedPreferences.getString(Constants.KEY_USER_NAME, "");
        String userEmail = sharedPreferences.getString(Constants.KEY_USER_EMAIL, "");
        String userPhoneNumber = sharedPreferences.getString(Constants.KEY_USER_PHONE_NUMBER, "");
        String userProfileImage = sharedPreferences.getString(Constants.KEY_USER_PROFILE_IMAGE, "");
        return new UserModel(userId, userToken, userName, userEmail, userPhoneNumber, userProfileImage);
    }

    public void removeSharedPrefUser() {
        editor = sharedPreferences.edit();
        editor.remove(Constants.KEY_USER_ID).apply();
        editor.remove(Constants.KEY_USER_TOKEN).apply();
        editor.remove(Constants.KEY_USER_NAME).apply();
        editor.remove(Constants.KEY_USER_EMAIL).apply();
        editor.remove(Constants.KEY_USER_PHONE_NUMBER).apply();
        editor.remove(Constants.KEY_USER_PROFILE_IMAGE).apply();
        editor.apply();
    }

    public void putSharedPrefCategoryMap(HashMap<String, Object> categoryMap) {
        editor = sharedPreferences.edit();
        editor.putStringSet(Constants.KEY_TEMP_CATEGORY_SET, categoryMap.keySet());
        editor.apply();
    }

    public HashMap<String, Object> getSharedPrefCategoryMap() {
        Set<String> categorySet = sharedPreferences.getStringSet(Constants.KEY_TEMP_CATEGORY_SET, null);
        HashMap<String, Object> objectHashMap = new HashMap<>();
        if (categorySet != null) {
            for (String key : categorySet) {
                Item item = new Item(key, key);
                objectHashMap.put(key, item.text);
            }
        }
        return objectHashMap;
    }

    public void removeSharedPrefCategoryMap() {
        editor = sharedPreferences.edit();
        editor.remove(Constants.KEY_TEMP_CATEGORY_SET).apply();
        editor.apply();
    }

    public void putSharedPrefRadius(LocationRadiusModel locationRadiusModel) {
        editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_TEMP_ADDRESS, locationRadiusModel.getAddressName());
        editor.putString(Constants.KEY_TEMP_LATITUDE, locationRadiusModel.getAddressLatitude());
        editor.putString(Constants.KEY_TEMP_LONGITUDE, locationRadiusModel.getAddressLongitude());
        editor.putInt(Constants.KEY_TEMP_DISTANCE, locationRadiusModel.getDistance());
        editor.apply();
    }

    public LocationRadiusModel getSharedPrefRadius() {
        String addressName = sharedPreferences.getString(Constants.KEY_TEMP_ADDRESS, "");
        String addressLatitude = sharedPreferences.getString(Constants.KEY_TEMP_LATITUDE, "");
        String addressLongitude = sharedPreferences.getString(Constants.KEY_TEMP_LONGITUDE, "");
        int distance = sharedPreferences.getInt(Constants.KEY_TEMP_DISTANCE, 0);
        return new LocationRadiusModel(addressName, addressLatitude, addressLongitude, distance);
    }
}