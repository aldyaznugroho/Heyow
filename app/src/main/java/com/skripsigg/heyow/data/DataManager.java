package com.skripsigg.heyow.data;

import com.skripsigg.heyow.data.helper.heyow.HeyowApiService;
import com.skripsigg.heyow.data.helper.preferences.PreferencesHelper;

/**
 * Created by Aldyaz on 5/9/2017.
 */

public interface DataManager {
    HeyowApiService getHeyowApiService();
    PreferencesHelper getPreferenceHelper();
}
