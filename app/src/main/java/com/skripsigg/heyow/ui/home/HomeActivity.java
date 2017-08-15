package com.skripsigg.heyow.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.addmatch.AddNewMatchActivity;
import com.skripsigg.heyow.ui.auth.AuthActivity;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchActivity;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchActivity;
import com.skripsigg.heyow.ui.searchmatch.SearchMatchActivity;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestActivity;
import com.skripsigg.heyow.ui.selectradius.SelectRadiusActivity;
import com.skripsigg.heyow.ui.userprofile.UserProfileActivity;
import com.skripsigg.heyow.views.adapters.CustomTabPagerAdapter;
import com.skripsigg.heyow.ui.nearme.NearMeTabFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.skripsigg.heyow.ui.interest.InterestTabFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends BaseActivity implements HomeMvpView,
        NavigationView.OnNavigationItemSelectedListener {

    private static final int SELECT_INTEREST_LOCATION_FLAG = 46;

    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;

    @Inject
    GoogleApiClient googleApiClient;

    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.main_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.main_navigation_view) NavigationView navigationView;
    @BindView(R.id.main_tab_layout) TabLayout tabLayout;
    @BindView(R.id.main_tab_view_pager) ViewPager viewPager;
    @BindView(R.id.post_match_fab) FloatingActionButton addMatchFab;
    @BindView(R.id.main_toolbar_title_text) TextView toolbarTitleText;

    ActionBarDrawerToggle drawerToggle;
    CustomTabPagerAdapter tabPagerAdapter;

    CircleImageView drawerProfile;
    TextView drawerUserName, drawerSelectedRadius;
    LinearLayout drawerInterestLayout, drawerRadiusLayout, drawerRadiusSelectedLayout, drawerRadiusUnselectedLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(mainToolbar);
        setTitle(null);
        init();
    }

    private void init() {
        initFirebase();
        initToolbar();
        initSplashTitle();
        initNavigationDrawer();
        initTab();
        initFloatingActionButton();
    }

    private void initToolbar() {
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Drawable navigationIcon = mainToolbar.getNavigationIcon();
        Drawable overflowIcon = mainToolbar.getOverflowIcon();
        if (overflowIcon != null && navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);

            overflowIcon.mutate();
            overflowIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initSplashTitle() {
        Typeface righteousFont = Typeface.createFromAsset(this.getAssets(), "font/righteous-regular.ttf");
        toolbarTitleText.setTypeface(righteousFont);
    }

    private void initTab() {
        tabPagerAdapter = new CustomTabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.addTab(NearMeTabFragment.getInstance(), getString(R.string.near_me));
        tabPagerAdapter.addTab(InterestTabFragment.getInstance(), getString(R.string.interest));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFloatingActionButton() {
        Drawable fabPlusIcon = addMatchFab.getDrawable();
        if (fabPlusIcon != null) {
            fabPlusIcon.mutate();
            fabPlusIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
        addMatchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMatchIntent = new Intent(HomeActivity.this, AddNewMatchActivity.class);
                startActivity(addMatchIntent);
            }
        });
    }

    private void initNavigationDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationHeaderView = navigationView.getHeaderView(0);
        drawerProfile = (CircleImageView) navigationHeaderView.findViewById(R.id.drawer_profile_image);
        drawerUserName = (TextView) navigationHeaderView.findViewById(R.id.drawer_user_name);
        drawerRadiusLayout = (LinearLayout) navigationHeaderView.findViewById(R.id.drawer_radius_layout);
        drawerRadiusSelectedLayout = (LinearLayout) navigationHeaderView.findViewById(R.id.drawer_location_selected_layout);
        drawerRadiusUnselectedLayout = (LinearLayout) navigationHeaderView.findViewById(R.id.drawer_location_unselected_layout);
        drawerInterestLayout = (LinearLayout) navigationHeaderView.findViewById(R.id.drawer_interest_layout);
        drawerSelectedRadius = (TextView) navigationHeaderView.findViewById(R.id.drawer_selected_radius_text);

        UserModel user = presenter.getUserPref();
        int tempDistance = presenter.getDistancePreferences();

        Uri profileUri = Uri.parse(user.getUserProfileImage());

        drawerUserName.setText(user.getUserName());
        Glide.with(this)
                .fromUri()
                .load(profileUri)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .placeholder(R.drawable.dummy_profile_1)
                .error(R.drawable.dummy_profile_1)
                .into(drawerProfile);

        if (tempDistance == 0) {
            drawerRadiusSelectedLayout.setVisibility(View.GONE);
            drawerRadiusUnselectedLayout.setVisibility(View.VISIBLE);
        } else {
            drawerRadiusSelectedLayout.setVisibility(View.VISIBLE);
            drawerRadiusUnselectedLayout.setVisibility(View.GONE);
            drawerSelectedRadius.setText(String.valueOf("Radius " + tempDistance + " km"));
        }

        drawerRadiusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);

                Intent selectRadiusIntent = new Intent(getApplicationContext(), SelectRadiusActivity.class);
                startActivityForResult(selectRadiusIntent, SELECT_INTEREST_LOCATION_FLAG);
            }
        });

        drawerInterestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);

                Intent selectInterestIntent = new Intent(getApplicationContext(), SelectInterestActivity.class);
                startActivityForResult(selectInterestIntent, SELECT_INTEREST_LOCATION_FLAG);
            }
        });
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    openAuthActivity();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable menuIcon = menu.getItem(i).getIcon();
            if (menuIcon != null) {
                menuIcon.mutate();
                menuIcon.setColorFilter(
                        ContextCompat.getColor(this, R.color.color_white),
                        PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_match:
                Intent searchIntent = new Intent(getApplicationContext(), SearchMatchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_INTEREST_LOCATION_FLAG) {
            if (resultCode == RESULT_OK) {
                int distanceResult = data.getIntExtra("DistanceResult", 0);

                drawerRadiusSelectedLayout.setVisibility(View.VISIBLE);
                drawerRadiusUnselectedLayout.setVisibility(View.GONE);
                drawerSelectedRadius.setText(String.valueOf("Radius " + distanceResult + " km"));

                final Fragment tabFragment = tabPagerAdapter.getFragment(
                        tabLayout.getSelectedTabPosition());

                if (tabFragment != null) {
                    switch (tabLayout.getSelectedTabPosition()) {
                        case 0:
                            ((NearMeTabFragment) tabFragment).setSwipeRefreshActive();
                            break;
                        case 1:
                            ((InterestTabFragment) tabFragment).setSwipeRefreshActive();
                            break;
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_created_match:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent createdMatchIntent = new Intent(this, CreatedMatchActivity.class);
                startActivity(createdMatchIntent);
                break;

            case R.id.nav_joined_match:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent joinedMatchIntent = new Intent(this, JoinedMatchActivity.class);
                startActivity(joinedMatchIntent);
                break;

            case R.id.nav_account:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent accountIntent = new Intent(this, UserProfileActivity.class);
                startActivity(accountIntent);
                break;

            case R.id.nav_logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(this);
                AlertDialog logoutDialog;

                logoutBuilder.setMessage("Logout of Heyow?");
                logoutBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.loadLogoutApiService();
                    }
                });
                logoutBuilder.setNegativeButton("Cancel", null);
                logoutDialog = logoutBuilder.create();
                logoutDialog.show();
                break;
        }

        return false;
    }

    @Override
    public void clearSession() {
        for (UserInfo userInfo : firebaseUser.getProviderData()) {
            if (userInfo.getProviderId().equals(GoogleAuthProvider.PROVIDER_ID)) {
                firebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                presenter.removeUserPref();
                                presenter.removeCategoryMapPref();
                            }
                        }
                );
            }
        }
    }

    @Override
    public void openAuthActivity() {
        Intent authIntent = new Intent(this, AuthActivity.class);
        authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(authIntent);
        finish();
    }
}
