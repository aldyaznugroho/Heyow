package com.skripsigg.heyow.ui.userprofile;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devspark.robototextview.widget.RobotoTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends BaseActivity implements UserProfileMvpView {
    @Inject UserProfileMvpPresenter<UserProfileMvpView> presenter;

    @BindView(R.id.user_profile_toolbar) Toolbar profileToolbar;
    @BindView(R.id.user_profile_image) CircleImageView profileImage;
    @BindView(R.id.user_profile_name) RobotoTextView profileNameText;
    @BindView(R.id.user_profile_email) RobotoTextView profileEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(profileToolbar);
        setTitle(getString(R.string.profile));
        init();
    }

    private void init() {
        initToolbar();
        initUserProfile();
    }

    private void initToolbar() {
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = profileToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initUserProfile() {
        UserModel user = presenter.getUserPreferences();

        Glide.with(this)
                .load(user.getUserProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .placeholder(R.drawable.dummy_profile_1)
                .error(R.drawable.dummy_profile_1)
                .into(profileImage);

        profileNameText.setText(user.getUserName());
        profileEmailText.setText(user.getUserEmail());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
