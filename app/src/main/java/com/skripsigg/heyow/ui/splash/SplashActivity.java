package com.skripsigg.heyow.ui.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.ui.auth.AuthActivity;
import com.skripsigg.heyow.ui.home.HomeActivity;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @BindView(R.id.splash_title_text) TextView titleText;
    @BindView(R.id.splash_progress_bar) ProgressBar progressBar;

    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        init();
    }

    private void init() {
        initSplashTitle();
    }

    private void initSplashTitle() {
        Typeface righteousFont = Typeface.createFromAsset(this.getAssets(), "font/righteous-regular.ttf");
        titleText.setTypeface(righteousFont);
    }

    @Override
    protected void onStop() {
        presenter.stopLocationService();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.startLocationService();
                } else {
                    finishAffinity();
                }
        }
    }

    @Override
    public Context getSplashContext() {
        return this;
    }

    @Override
    public boolean checkIsHasPermission() {
        for (String permission : permissions) {
            if (hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestPermission() {
        requestPermissionSafely(permissions, Constants.LOCATION_PERMISSION);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void openHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void openAuthActivity() {
        Intent authIntent = new Intent(this, AuthActivity.class);
        startActivity(authIntent);
        finish();
    }

    @Override
    public void openInterestActivity() {
        Intent profileIntent = new Intent(this, SelectInterestActivity.class);
        profileIntent.putExtra(Constants.INTEREST_ACTIVITY_FLAG, 1001);
        startActivity(profileIntent);
        finish();
    }

    /*private void updateUI() {
        if (isLocationUpdate) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }*/

    /*private void showOfflineAlertDialog() {
        AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(this);
        AlertDialog logoutDialog;

        logoutBuilder.setMessage("No internet connection");
        logoutBuilder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startLocationWithPermissionFirst();
            }
        });
        logoutBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        logoutDialog = logoutBuilder.create();
        logoutDialog.show();
    }*/
}
