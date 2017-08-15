package com.skripsigg.heyow.ui.selectradius;

import com.jakewharton.rxbinding2.widget.SeekBarChangeEvent;
import com.skripsigg.heyow.ui.base.MvpView;

import io.reactivex.Observable;

/**
 * Created by Aldyaz on 5/17/2017.
 */

public interface SelectRadiusMvpView extends MvpView {
    void setSeekbarProgress(int value);
    void setDistanceText(int value);
    int getSeekbarValue();
    Observable<Integer> userSeekbarDebounce();
    Observable<Integer> seekBarChange();
    void openIntentWithResultOk();
}
