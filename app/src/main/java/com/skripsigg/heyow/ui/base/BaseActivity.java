package com.skripsigg.heyow.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.application.HeyowApp;
import com.skripsigg.heyow.di.component.ActivityComponent;
import com.skripsigg.heyow.di.component.DaggerActivityComponent;
import com.skripsigg.heyow.di.module.ActivityModule;
import com.skripsigg.heyow.utils.FeedbackUtils;
import com.skripsigg.heyow.utils.others.NetworkUtils;

import butterknife.Unbinder;

/**
 * Created by Aldyaz on 5/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

    private ActivityComponent activityComponent;
    private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(HeyowApp.get(this).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        progressDialog = FeedbackUtils.showProgressDialog(this, getString(R.string.dialog_please_wait));
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showOnSuccessSnackbar(String message) {
        FeedbackUtils.showSnackbar(getWindow().getDecorView(), this, message);
    }

    @Override
    public void showOnSuccessToast(String message) {
        FeedbackUtils.showToast(this, message);
    }

    @Override
    public void showOnErrorSnackbar(String message) {
        FeedbackUtils.showSnackbar(getWindow().getDecorView(), this, message);
    }

    @Override
    public void showOnErrorToast(String message) {
        FeedbackUtils.showToast(this, message);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.getInstance().isConnectingToInternet(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }
}
