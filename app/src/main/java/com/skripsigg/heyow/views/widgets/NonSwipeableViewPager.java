package com.skripsigg.heyow.views.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Aldyaz on 5/27/2017.
 */

public class NonSwipeableViewPager extends ViewPager {

    private boolean swipeEnabled;

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.swipeEnabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.swipeEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.swipeEnabled && onTouchEvent(ev);
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }
}
