package com.skripsigg.heyow.ui.selectinterest;

import com.skripsigg.heyow.ui.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by Aldyaz on 5/13/2017.
 */

public interface SelectInterestMvpPresenter<V extends SelectInterestMvpView> extends MvpPresenter<V> {
    HashMap<String, Object> getSharedPrefCategoryMap();
    void saveSelectedInterest();
}
