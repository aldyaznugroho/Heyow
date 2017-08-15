package com.skripsigg.heyow.ui.addmatch.step3;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpPresenter;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by Aldyaz on 6/1/2017.
 */

public interface StepThreeMvpPresenter<V extends StepThreeMvpView> extends MvpPresenter<V> {
    String getUserIdPref();
    MatchDetailResponse getMatchFormPref();
    void loadCreateMatchApiService(Map<String, String> formFields);
}
