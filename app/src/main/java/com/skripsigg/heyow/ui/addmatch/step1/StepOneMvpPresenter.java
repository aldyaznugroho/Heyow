package com.skripsigg.heyow.ui.addmatch.step1;

import com.skripsigg.heyow.ui.base.MvpPresenter;

import io.reactivex.functions.Consumer;

/**
 * Created by Aldyaz on 5/31/2017.
 */

public interface StepOneMvpPresenter<V extends StepOneMvpView> extends MvpPresenter<V> {
    void saveFormToPreferences(String selectedImage, String title, Integer selectedCategory);
    void saveFormDelayProcessing(Consumer<Long> consumer);
}
