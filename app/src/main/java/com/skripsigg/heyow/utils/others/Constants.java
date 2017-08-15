package com.skripsigg.heyow.utils.others;

/**
 * Created by Aldyaz on 10/21/2016.
 */

public class Constants {
    public static final String PACKAGE_NAME = "com.aldyaz.carivent";

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;

    // Permission
    public static final int LOCATION_PERMISSION = 1001;
    public static final int PICK_PHOTO_PERMISSION = 1002;

    // Google Login
    public static final int RC_SIGN_IN = 1;

    // Server Key
    public static final String SERVER_KEY = "AIzaSyA9idx9iKxceJ-LJ9Tw_qthv4THYLfQ-n4";

    // Notification
    public static final int NOTIFICATION_ID = 100;
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // HEYOW Network calls
    public static final String HEYOW_BASE_URL = "http://desaingaul.web.id/newheyow/";
    public static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String FCM_API_BASE_URL = "https://fcm.googleapis.com/fcm/";
    public static final String LOGIN_ENDPOINT = "POST/user/";
    public static final String ADD_MATCH_ENDPOINT = "POST/match/";
    public static final String EDIT_MATCH_ENDPOINT = "POST/update_match/";
    public static final String SEARCH_MATCH_ENDPOINT = "POST/search_match/";
    public static final String NEAR_ME_ENDPOINT = "GET/near_me/index.php/";
    public static final String BY_CATEGORY_ENDPOINT = "GET/interested/";
    public static final String MATCH_BY_ID_ENDPOINT = "GET/match/";
    public static final String USER_CREATED_JOINED_MATCH_ENDPOINT = "GET/user/";
    public static final String JOIN_MATCH_ENDPOINT = "POST/join_match/";
    public static final String LEAVE_MATCH_ENDPOINT = "POST/leave_match/";
    public static final String DELETE_MATCH_ENDPOINT = "POST/remove_match/";
    public static final String LOGOUT_ENDPOINT = "POST/logout/";

    // Test Network calls
    public static final String CITY_BASE_URL = "http://private-b8cf44-androidcleancode.apiary-mock.com/";
    public static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String MOVIES_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w533_and_h300_bestv2";
    public static final String TMDB_API_KEY = "10641f7df8054e731d52486f0b893bde";

    // SharedPreference User key
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_TOKEN = "user_token";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_PHONE_NUMBER = "user_phone_number";
    public static final String KEY_USER_PROFILE_IMAGE = "user_profile_image";

    // SharedPreference Maps choose temp key
    public static final String KEY_TEMP_ADDRESS = "temp_address";
    public static final String KEY_TEMP_LATITUDE = "temp_latitude";
    public static final String KEY_TEMP_LONGITUDE = "temp_longitude";
    public static final String KEY_TEMP_DISTANCE = "temp_distance";
    public static final String KEY_TEMP_CATEGORY_SET = "category_set";

    // Location Geocoder
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final String LOCATION_DATA_EXTRA_LATITUDE = PACKAGE_NAME + ".LOCATION_DATA_EXTRA_LATITUDE";
    public static final String LOCATION_DATA_EXTRA_LONGITUDE = PACKAGE_NAME + ".LOCATION_DATA_EXTRA_LONGITUDE";

    // Maps Details
    public static final String KEY_LATITUDE_DETAIL = "detail_latitude";
    public static final String KEY_LONGITUDE_DETAIL = "detail_longitude";

    // Match Parcelable Key
    public static final String KEY_MATCH_RESPONSE_PARCELABLE = "KEY_MATCH_RESPONSE_PARCELABLE";
    public static final String KEY_SEARCH_QUERY_PARCELABLE = "KEY_SEARCH_QUERY_PARCELABLE";
    public static final String KEY_MATCH_CHANNEL_URL = "KEY_MATCH_CHANNEL_URL";

    // Match Key
    public static final String KEY_MATCH_ID = "match_id";
    public static final String KEY_MATCH_NAME = "match_name";

    // Firebase chat reference
    public static final String DB_CHAT_REF = "heyow_chat";
    public static final String DB_CHAT_ROOM = "chat_room";
    public static final String DB_USER_REGISTERED = "user_registered";
    public static final String DB_CHAT_ID = "chatId";
    public static final String DB_CHAT_MATCH_ID = "matchId";
    public static final String DB_CHAT_MESSAGE = "message";
    public static final String DB_CHAT_MESSAGE_USER_ID = "userId";
    public static final String DB_CHAT_MESSAGE_USER_NAME = "userName";
    public static final String DB_CHAT_MESSAGE_CONTENT = "messageContent";
    public static final String DB_CHAT_MESSAGE_DATE = "messageDate";
    public static final String DB_CHAT_MESSAGE_TIME = "messageTime";

    // Match Detail Type
    public static final String MATCH_DETAIL_TYPE = "MATCH_DETAIL_TYPE";
    public static final String TYPE_MATCH_GENERAL = "TYPE_MATCH_GENERAL";
    public static final String TYPE_MATCH_JOINED = "TYPE_MATCH_JOINED";

    // Match chat status
    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;

    public static final String POST_MATCH_USER_ID = "user_id";
    public static final String PUT_MATCH_ID = "match_id";
    public static final String POST_MATCH_CATEGORY = "match_category";
    public static final String POST_MATCH_TITLE = "match_title";
    public static final String POST_MATCH_DETAIL = "match_detail";
    public static final String POST_MATCH_LOCATION = "match_location";
    public static final String POST_MATCH_VENUE = "match_venue";
    public static final String POST_MATCH_DATE = "match_date";
    public static final String POST_MATCH_TIME = "match_time";
    public static final String POST_MATCH_LATITUDE = "match_latitude";
    public static final String POST_MATCH_LONGITUDE = "match_longitude";
    public static final String POST_MATCH_QUOTA_MAX = "quota_max";
    public static final String POST_MATCH_IMAGE = "encoded_match_image";

    // Match Radius Query Key
    public static final String NEAR_ME_LATITUDE = "lat";
    public static final String NEAR_ME_LONGITUDE = "lng";
    public static final String NEAR_ME_DISTANCE = "km";

    public static final String INTEREST_ACTIVITY_FLAG = "calling-activity";

    // Match search key
    public static final String SEARCH_BY_VENUE = "match_venue";
    public static final String SEARCH_BY_LOCATION = "match_location";
    public static final String SEARCH_BY_CATEGORY = "match_category";

    // Notif click action
    public static final String OPEN_MATCH_DETAIL_ACTIVITY = "OPEN_MATCH_DETAIL_ACTIVITY";
}
