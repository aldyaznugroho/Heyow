package com.skripsigg.heyow.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldyaz on 12/22/2016.
 */

public class MatchDetailResponse implements Parcelable {
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("match_category")
    @Expose
    private String matchCategory;
    @SerializedName("match_title")
    @Expose
    private String matchTitle;
    @SerializedName("match_detail")
    @Expose
    private String matchDetail;
    @SerializedName("match_location")
    @Expose
    private String matchLocation;
    @SerializedName("match_venue")
    @Expose
    private String matchVenue;
    @SerializedName("match_date")
    @Expose
    private String matchDate;
    @SerializedName("match_time")
    @Expose
    private String matchTime;
    @SerializedName("match_image")
    @Expose
    private String matchImage;
    @SerializedName("match_latitude")
    @Expose
    private String matchLatitude;
    @SerializedName("match_longitude")
    @Expose
    private String matchLongitude;
    @SerializedName("match_quota")
    @Expose
    private MatchQuota matchQuota;
    @SerializedName("match_join")
    @Expose
    private List<UserModel> joinedUser = new ArrayList<UserModel>();
    @SerializedName("match_status")
    @Expose
    private String matchStatus;

    public MatchDetailResponse() {
    }

    protected MatchDetailResponse(Parcel in) {
        matchId = in.readString();
        userId = in.readString();
        matchCategory = in.readString();
        matchTitle = in.readString();
        matchDetail = in.readString();
        matchLocation = in.readString();
        matchVenue = in.readString();
        matchDate = in.readString();
        matchTime = in.readString();
        matchImage = in.readString();
        matchLatitude = in.readString();
        matchLongitude = in.readString();
        matchQuota = in.readParcelable(MatchQuota.class.getClassLoader());
        joinedUser = in.createTypedArrayList(UserModel.CREATOR);
        matchStatus = in.readString();
    }

    public static final Creator<MatchDetailResponse> CREATOR = new Creator<MatchDetailResponse>() {
        @Override
        public MatchDetailResponse createFromParcel(Parcel in) {
            return new MatchDetailResponse(in);
        }

        @Override
        public MatchDetailResponse[] newArray(int size) {
            return new MatchDetailResponse[size];
        }
    };

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatchCategory() {
        return matchCategory;
    }

    public void setMatchCategory(String matchCategory) {
        this.matchCategory = matchCategory;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchDetail() {
        return matchDetail;
    }

    public void setMatchDetail(String matchDetail) {
        this.matchDetail = matchDetail;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public String getMatchVenue() {
        return matchVenue;
    }

    public void setMatchVenue(String matchVenue) {
        this.matchVenue = matchVenue;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(String matchImage) {
        this.matchImage = matchImage;
    }

    public String getMatchLatitude() {
        return matchLatitude;
    }

    public void setMatchLatitude(String matchLatitude) {
        this.matchLatitude = matchLatitude;
    }

    public String getMatchLongitude() {
        return matchLongitude;
    }

    public void setMatchLongitude(String matchLongitude) {
        this.matchLongitude = matchLongitude;
    }

    public MatchQuota getMatchQuota() {
        return matchQuota;
    }

    public void setMatchQuota(MatchQuota matchQuota) {
        this.matchQuota = matchQuota;
    }

    public List<UserModel> getJoinedUser() {
        return joinedUser;
    }

    public void setJoinedUser(List<UserModel> joinedUser) {
        this.joinedUser = joinedUser;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(matchId);
        parcel.writeString(userId);
        parcel.writeString(matchCategory);
        parcel.writeString(matchTitle);
        parcel.writeString(matchDetail);
        parcel.writeString(matchLocation);
        parcel.writeString(matchVenue);
        parcel.writeString(matchDate);
        parcel.writeString(matchTime);
        parcel.writeString(matchImage);
        parcel.writeString(matchLatitude);
        parcel.writeString(matchLongitude);
        parcel.writeParcelable(matchQuota, i);
        parcel.writeTypedList(joinedUser);
        parcel.writeString(matchStatus);
    }
}
