package com.skripsigg.heyow.ui.selectradius;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.jakewharton.rxbinding2.widget.RxSeekBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.SeekBarChangeEvent;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.LocationRadiusModel;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.utils.others.SharedPreferenceCustom;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class SelectRadiusActivity extends BaseActivity implements SelectRadiusMvpView {

    @Inject SelectRadiusMvpPresenter<SelectRadiusMvpView> presenter;

    @BindView(R.id.location_radius_toolbar) Toolbar toolbar;
    @BindView(R.id.distance_listener_text) TextView distanceListenerText;
    @BindView(R.id.distance_seek_bar) SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_radius);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(toolbar);
        setTitle("Select Radius");
        init();
    }

    private void init() {
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_radius, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.action_done_radius);
        SpannableString spanString = new SpannableString(doneItem.getTitle());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, spanString.length(), 0);
        doneItem.setTitle(spanString);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done_radius:
                presenter.saveDistanceValueToPreferences();
                openIntentWithResultOk();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void setSeekbarProgress(int value) {
        seekBar.setProgress(value);
    }

    @Override
    public void setDistanceText(int value) {
        distanceListenerText.setText(value + " " + getString(R.string.radius_km_text));
    }

    @Override
    public int getSeekbarValue() {
        return seekBar.getProgress();
    }

    @Override
    public Observable<Integer> userSeekbarDebounce() {
        return RxSeekBar.userChanges(seekBar);
    }

    @Override
    public Observable<Integer> seekBarChange() {
        return RxSeekBar.changes(seekBar);
    }

    @Override
    public void openIntentWithResultOk() {
        Intent homeIntent = new Intent();
        homeIntent.putExtra("DistanceResult", presenter.getDistancePreferences());
        setResult(RESULT_OK, homeIntent);
        finish();
    }
}