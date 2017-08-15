package com.skripsigg.heyow.ui.otheruser;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.views.adapters.CustomTabPagerAdapter;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchFragment;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserActivity extends BaseActivity implements OtherUserMvpView,
        AppBarLayout.OnOffsetChangedListener {

    @Inject OtherUserMvpPresenter<OtherUserMvpView> presenter;

    @BindView(R.id.other_user_app_bar) AppBarLayout appBar;
    @BindView(R.id.other_user_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.other_user_toolbar) Toolbar otherUserToolbar;
    @BindView(R.id.other_user_tab_layout) TabLayout tabLayout;
    @BindView(R.id.other_user_view_pager) ViewPager viewPager;
    @BindView(R.id.other_user_profile_image) CircleImageView otherUserProfileImage;
    @BindView(R.id.other_user_name_appbar_text) TextView otherUserProfileNameAppbarText;

    CustomTabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(otherUserToolbar);
        init();
    }

    private void init() {
        initAppBarLayout();
        initToolbar();
        initTabLayout();
        initProfileDetail();
    }

    private void initAppBarLayout() {
        appBar.addOnOffsetChangedListener(this);
    }

    private void initToolbar() {
        otherUserToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Drawable navigationIcon = otherUserToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initTabLayout() {
        tabPagerAdapter = new CustomTabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.addTab(CreatedMatchFragment.getInstance(), getString(R.string.created_match));
        tabPagerAdapter.addTab(JoinedMatchFragment.getInstance(), getString(R.string.joined_match));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initProfileDetail() {
        Glide.with(this)
                .load(getJoinedUserParcelable().getUserProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .placeholder(R.drawable.dummy_profile_1)
                .error(R.drawable.dummy_profile_1)
                .into(otherUserProfileImage);
        otherUserProfileNameAppbarText.setText(getJoinedUserParcelable().getUserName());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
            otherUserToolbar.setTitle(getJoinedUserParcelable().getUserName());
        } else {
            otherUserToolbar.setTitle(null);
        }
    }

    @Override
    public UserModel getJoinedUserParcelable() {
        return getIntent().getExtras().getParcelable(Constants.KEY_MATCH_RESPONSE_PARCELABLE);
    }
}
