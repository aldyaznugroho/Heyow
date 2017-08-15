package com.skripsigg.heyow.ui.matchdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoButton;
import com.devspark.robototextview.widget.RobotoTextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.ui.editmatch.EditMatchActivity;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchActivity;
import com.skripsigg.heyow.ui.matchchat.MatchChatActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsigg.heyow.ui.mapdetails.MapDetailsActivity;
import com.skripsigg.heyow.ui.otheruser.OtherUserActivity;
import com.skripsigg.heyow.views.adapters.CustomRecyclerAdapter;
import com.skripsigg.heyow.views.widgets.MainStateLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

public class MatchDetailActivity extends BaseActivity implements MatchDetailMvpView,
        AppBarLayout.OnOffsetChangedListener {
    private static final int EDIT_MATCH_FLAG = 25;

    @Inject
    MatchDetailMvpPresenter<MatchDetailMvpView> presenter;

    @BindView(R.id.state_layout) MainStateLayout stateLayout;
    @BindView(R.id.match_detail_toolbar) Toolbar toolbar;
    @BindView(R.id.user_list_recycler_view) RecyclerView userListRecyclerView;
    @BindView(R.id.match_detail_backdrop_image) ImageView matchBackdropImage;
    @BindView(R.id.match_detail_map_image) ImageView mapPreviewImage;

    @BindView(R.id.match_detail_main_app_bar) AppBarLayout appBar;
    @BindView(R.id.match_detail_main_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.match_detail_title_text) TextView matchTitleText;
    @BindView(R.id.match_detail_category_text) TextView matchCategoryText;
    @BindView(R.id.match_detail_date_time_text) TextView matchDateTimeText;
    @BindView(R.id.match_detail_quota_now_text) TextView matchQuotaNowText;
    @BindView(R.id.match_detail_quota_max_text) TextView matchQuotaMaxText;
    @BindView(R.id.match_detail_venue_text) TextView matchVenueText;
    @BindView(R.id.match_detail_address_text) TextView matchAddressText;
    @BindView(R.id.match_detail_description_text) TextView matchDetailDescriptionText;
    @BindView(R.id.match_detail_player_section_text) TextView matchDetailPlayerSectionText;

    @BindView(R.id.match_detail_join_button) Button matchJoinButton;
    @BindView(R.id.match_detail_leave_button) Button matchLeaveButton;

    @BindView(R.id.match_detail_leave_layout) View matchLeaveLayout;
    @BindView(R.id.match_detail_join_layout) View matchJoinLayout;
    @BindView(R.id.match_detail_not_join_layout) View matchNotJoinLayout;
    @BindView(R.id.match_detail_chat_button) FloatingActionButton matchChatFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        initMainToolbar();
        initMainAppBar();
        initChatFloatingButton();
        initUserListRecyclerView();
        initStateLayout();
    }

    private void initMainToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Drawable navigationIcon = toolbar.getNavigationIcon();
        Drawable overflowIcon = toolbar.getOverflowIcon();
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

    private void initMainAppBar() {
        appBar.addOnOffsetChangedListener(this);
    }

    private void initChatFloatingButton() {
        Drawable fabPlusIcon = matchChatFab.getDrawable();
        if (fabPlusIcon != null) {
            fabPlusIcon.mutate();
            fabPlusIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initUserListRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        userListRecyclerView.setLayoutManager(linearLayoutManager);
        userListRecyclerView.setHasFixedSize(true);
        userListRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initStateLayout() {
        stateLayout.setTransitionsEnabled(false);
        stateLayout.setOfflineRetryOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadMatchDetailApiService();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String fetchCategoryName(int position) {
        String[] catList = getResources().getStringArray(R.array.match_category);
        return catList[position];
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public String getMatchIdStringExtra() {
        return getIntent().getExtras().getString(Constants.KEY_MATCH_ID);
    }

    @Override
    public void openMapsDetailActivity(String latitude, String longitude) {
        Bundle mapsDetailBundle = new Bundle();
        mapsDetailBundle.putString(Constants.KEY_LATITUDE_DETAIL, latitude);
        mapsDetailBundle.putString(Constants.KEY_LONGITUDE_DETAIL, longitude);
        Intent mapsDetailIntent = new Intent(this, MapDetailsActivity.class);
        mapsDetailIntent.putExtras(mapsDetailBundle);
        startActivity(mapsDetailIntent);
    }

    @Override
    public void openEditMatchActivity() {
        Bundle editMatchBundle = new Bundle();
        editMatchBundle.putParcelable(
                Constants.KEY_MATCH_RESPONSE_PARCELABLE, presenter.getMatchDetailResponseResult());
        Intent editMatchIntent = new Intent(this, EditMatchActivity.class);
        editMatchIntent.putExtras(editMatchBundle);
        startActivityForResult(editMatchIntent, EDIT_MATCH_FLAG);
    }

    @Override
    public void openJoinedMatchActivity() {
        Intent joinedMatchIntent = new Intent(this, JoinedMatchActivity.class);
        startActivity(joinedMatchIntent);
        finish();
    }

    @Override
    public void openMatchChatActivity() {
        Bundle matchDetailBundle = new Bundle();
        matchDetailBundle.putParcelable(
                Constants.KEY_MATCH_RESPONSE_PARCELABLE, presenter.getMatchDetailResponseResult());
        Intent chatIntent = new Intent(this, MatchChatActivity.class);
        chatIntent.putExtras(matchDetailBundle);
        startActivity(chatIntent);
    }

    @Override
    public void bindDataToMainMatchDetail(MatchDetailResponse responseResult) {
        String matchImage = responseResult.getMatchImage();
        String matchTitle = responseResult.getMatchTitle();
        String matchCategory = responseResult.getMatchCategory();
        String matchDate = responseResult.getMatchDate();
        String matchTime = responseResult.getMatchTime();
        String matchQuotaNow = responseResult.getMatchQuota().getQuotaNow();
        String matchQuotaMax = responseResult.getMatchQuota().getQuotaMax();
        String matchAddress = responseResult.getMatchLocation();
        String matchVenue = responseResult.getMatchVenue();
        String matchDescription = responseResult.getMatchDetail();
        String staticMapURL = "https://maps.googleapis.com/maps/api/staticmap?center="
                + responseResult.getMatchLatitude() + "," + responseResult.getMatchLongitude()
                + "&markers=color:red%7C"
                + responseResult.getMatchLatitude() + "," + responseResult.getMatchLongitude()
                + "&zoom=15&size=600x400";

        Uri matchImageURI = Uri.parse(matchImage);
        Uri staticMapURI = Uri.parse(staticMapURL);

        Glide.with(this)
                .fromUri()
                .load(matchImageURI)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(matchBackdropImage);

        Glide.with(this)
                .fromUri()
                .load(staticMapURI)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(mapPreviewImage);

        matchTitleText.setText(matchTitle);
        matchCategoryText.setText(fetchCategoryName(Integer.parseInt(matchCategory)));
        matchDateTimeText.setText(matchDate + " @ " + matchTime);
        matchQuotaNowText.setText(matchQuotaNow + " " + getString(R.string.participants));
        matchQuotaMaxText.setText(matchQuotaMax + " " + getString(R.string.player_quota));
        matchDetailPlayerSectionText.append(" ("
                + String.valueOf(Integer.parseInt(matchQuotaMax) - Integer.parseInt(matchQuotaNow))
                + " "
                + getString(R.string.remaining)
                + ")");
        matchVenueText.setText(matchVenue);
        matchAddressText.setText(matchAddress);
        matchDetailDescriptionText.setText(matchDescription);

        CustomRecyclerAdapter<UserModel> userJoinedListAdapter = new CustomRecyclerAdapter<>(
                this,
                R.layout.user_view_holder,
                responseResult.getJoinedUser(),
                new CustomRecyclerAdapter.ViewHolderBinder<UserModel>() {
                    @Override
                    public void bind(CustomRecyclerAdapter.CustomViewHolder<UserModel> holder, final UserModel item) {
                        LinearLayout itemListLayout = (LinearLayout) holder.itemView.findViewById(R.id.match_user_list_parent_layout);
                        CircleImageView profileImage = (CircleImageView) holder.itemView.findViewById(R.id.match_list_user_profile_image);
                        RobotoTextView profileNameText = (RobotoTextView) holder.itemView.findViewById(R.id.match_list_user_profile_name);

                        String imagePath = item.getUserProfileImage();
                        String profileName = item.getUserName();

                        final String firstName;
                        if (profileName.contains(" ")) {
                            firstName = profileName.substring(0, profileName.indexOf(" "));
                        } else {
                            firstName = profileName;
                        }

                        Glide.with(MatchDetailActivity.this)
                                .load(imagePath)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .placeholder(R.drawable.dummy_profile_1)
                                .error(R.drawable.dummy_profile_1)
                                .into(profileImage);

                        profileNameText.setText(firstName);
                        itemListLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle userJoinedBundle = new Bundle();
                                userJoinedBundle.putParcelable(Constants.KEY_MATCH_RESPONSE_PARCELABLE, item);
                                Intent otherUserIntent = new Intent(getApplicationContext(), OtherUserActivity.class);
                                otherUserIntent.putExtras(userJoinedBundle);
                                startActivity(otherUserIntent);
                            }
                        });
                    }
                });
        userListRecyclerView.setAdapter(userJoinedListAdapter);
    }

    @Override
    public void showLeaveMatchAlertDialog() {
        AlertDialog.Builder leaveBuilder = new AlertDialog.Builder(this);
        AlertDialog leaveDialog;

        leaveBuilder.setMessage("Leave this match?");
        leaveBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showLoading();
                presenter.loadLeaveMatchApiService();
            }
        });
        leaveBuilder.setNegativeButton("Cancel", null);
        leaveDialog = leaveBuilder.create();
        leaveDialog.show();
    }

    @Override
    public void showDeleteMatchAlertDialog() {
        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
        AlertDialog deleteDialog;

        deleteBuilder.setMessage("Are you sure to delete this match?");
        deleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showLoading();
                presenter.loadDeleteMatchApiService();
            }
        });
        deleteBuilder.setNegativeButton("Cancel", null);
        deleteDialog = deleteBuilder.create();
        deleteDialog.show();
    }

    @Override
    public Observable<Object> mapPreviewClick() {
        return RxView.clicks(mapPreviewImage);
    }

    @Override
    public Observable<Object> chatFloatingButtonClick() {
        return RxView.clicks(matchChatFab);
    }

    @Override
    public Observable<Object> matchJoinButtonClick() {
        return RxView.clicks(matchJoinButton);
    }

    @Override
    public Observable<Object> matchLeaveButtonClick() {
        return RxView.clicks(matchLeaveButton);
    }

    @Override
    public Observable<Object> matchEditButtonClick() {
        return null;
    }

    @Override
    public Observable<Object> matchDeleteButtonClick() {
        return null;
    }

    @Override
    public void hideDeleteLayout() {
    }

    @Override
    public void showUserJoinedMainLayout() {
        matchJoinLayout.setVisibility(View.VISIBLE);
        matchNotJoinLayout.setVisibility(View.GONE);
        matchLeaveLayout.setVisibility(View.VISIBLE);
        matchChatFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserNotJoinedMainLayout() {
        matchNotJoinLayout.setVisibility(View.VISIBLE);
        matchLeaveLayout.setVisibility(View.GONE);
        matchChatFab.setVisibility(View.GONE);
    }

    @Override
    public void showMatchDetailLayout() {
        stateLayout.showContent();
    }

    @Override
    public void showRetryLayout() {
        stateLayout.showOffline();
    }

    @Override
    public void showProgressBar() {
        stateLayout.showProgress();
    }

    @Override
    public void setOfflineText(String offlineText) {
        stateLayout.setOfflineText(offlineText);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
            toolbar.setTitle("Match Detail");
        } else {
            toolbar.setTitle(null);
        }
    }
}
