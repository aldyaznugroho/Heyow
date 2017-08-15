package com.skripsigg.heyow.views.widgets;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skripsigg.heyow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aldyaz on 5/31/2017.
 */

public class MatchFailRetryLayout extends LinearLayout {

//    @BindView(R.id.fail_retry_button) Button retryButton;

    public MatchFailRetryLayout(Context context) {
        super(context);
    }

    public MatchFailRetryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchFailRetryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.match_fail_retry_layout, this);
//        ButterKnife.bind(this);
    }
}
