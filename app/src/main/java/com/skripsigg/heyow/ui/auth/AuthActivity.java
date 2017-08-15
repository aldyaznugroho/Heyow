package com.skripsigg.heyow.ui.auth;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jakewharton.rxbinding2.view.RxView;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.helper.DrawableColoringHelper;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.ui.home.HomeActivity;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


public class AuthActivity extends BaseActivity implements AuthMvpView {
    @Inject
    AuthMvpPresenter<AuthMvpView> presenter;

    @Inject
    GoogleApiClient googleApiClient;

    @BindView(R.id.auth_title_text) TextView titleText;
    @BindView(R.id.google_login_button) Button googleLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        presenter.onAttach(this);
        init();
    }

    private void init() {
        initTitleText();
        initGoogleLoginButton();
    }

    private void initTitleText() {
        Typeface righteousFont = Typeface.createFromAsset(this.getAssets(), "font/righteous-regular.ttf");
        titleText.setTypeface(righteousFont);
    }

    private void initGoogleLoginButton() {
        Drawable googleIcon = ContextCompat.getDrawable(this, R.drawable.ic_google);
        DrawableColoringHelper.withContext(getApplicationContext())
                .withColor(R.color.color_white)
                .withDrawable(googleIcon)
                .tint();
        googleLoginButton.setCompoundDrawablesWithIntrinsicBounds(googleIcon, null, null, null);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public Observable<Object> googleLoginButtonClick() {
        return RxView.clicks(googleLoginButton);
    }

    @Override
    public void startGoogleLoginIntent() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override
    public void openInterestActivity() {
        Intent interestIntent = new Intent(this, SelectInterestActivity.class);
        interestIntent.putExtra(Constants.INTEREST_ACTIVITY_FLAG, 1001);
        interestIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(interestIntent);
        finish();
    }

    @Override
    public void openHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }
}
