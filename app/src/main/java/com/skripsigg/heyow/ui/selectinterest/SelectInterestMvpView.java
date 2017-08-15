package com.skripsigg.heyow.ui.selectinterest;

import com.skripsigg.heyow.ui.base.MvpView;

import java.util.HashMap;

/**
 * Created by Aldyaz on 5/13/2017.
 */

public interface SelectInterestMvpView extends MvpView {
    HashMap<String, Object> getCollectionPickerCheckedItems();
    int getIntentInterestFlag();
    void openHomeActivity();
    void openIntentWithResultOk();
    void finishActivity();
}
