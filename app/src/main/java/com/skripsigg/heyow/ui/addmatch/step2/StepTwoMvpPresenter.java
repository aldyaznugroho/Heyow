package com.skripsigg.heyow.ui.addmatch.step2;

import com.skripsigg.heyow.ui.base.MvpPresenter;

import io.reactivex.functions.Consumer;

/**
 * Created by Aldyaz on 6/1/2017.
 */

public interface StepTwoMvpPresenter<V extends StepTwoMvpView> extends MvpPresenter<V> {
    void saveFormToPreferences(String matchVenueName,
                               String matchVenueAddress,
                               String tempLat,
                               String tempLng,
                               String matchDate,
                               String matchTime,
                               String matchMaxPeople);
    void saveFormDelayProcessing(Consumer<Long> consumer);
}
