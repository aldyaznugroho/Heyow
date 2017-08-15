package com.skripsigg.heyow.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.skripsigg.heyow.di.component.ActivityComponent;

import butterknife.Unbinder;

/**
 * Created by Aldyaz on 5/9/2017.
 */

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity activity;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) context;
            this.activity = baseActivity;
            baseActivity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void showLoading() {
        if (activity != null) {
            activity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (activity != null) {
            activity.hideLoading();
        }
    }

    @Override
    public void showOnSuccessSnackbar(String message) {
        if (activity != null) {
            activity.showOnSuccessSnackbar(message);
        }
    }

    @Override
    public void showOnSuccessToast(String message) {
        if (activity != null) {
            activity.showOnSuccessToast(message);
        }
    }

    @Override
    public void showOnErrorSnackbar(String message) {
        if (activity != null) {
            activity.showOnErrorSnackbar(message);
        }
    }

    @Override
    public void showOnErrorToast(String message) {
        if (activity != null) {
            activity.showOnErrorToast(message);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        return false;
    }

    public ActivityComponent getActivityComponent() {
        return activity.getActivityComponent();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public interface Callback {
        void onFragmentAttached();
        void onFragmentDetached(String tag);
    }
}
