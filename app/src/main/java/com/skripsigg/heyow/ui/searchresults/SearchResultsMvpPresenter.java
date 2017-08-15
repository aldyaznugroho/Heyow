package com.skripsigg.heyow.ui.searchresults;

import com.skripsigg.heyow.ui.base.MvpPresenter;

import java.util.Map;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface SearchResultsMvpPresenter<V extends SearchResultsMvpView> extends MvpPresenter<V> {
    void loadSearchMatchResults();
}
