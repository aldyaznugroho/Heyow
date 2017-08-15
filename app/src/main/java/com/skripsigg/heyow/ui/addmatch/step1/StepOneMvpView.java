package com.skripsigg.heyow.ui.addmatch.step1;

import android.graphics.Bitmap;

import com.skripsigg.heyow.ui.base.MvpView;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/31/2017.
 */

public interface StepOneMvpView extends MvpView {
    boolean checkIsHasPermission();
    void requestPermission();
    void showImagePickerDialog();
    Observable<Object> ImageUploadLayoutClick();
}
