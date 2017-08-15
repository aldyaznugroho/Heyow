package com.skripsigg.heyow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldyaz on 12/26/2016.
 */

public class HistoryModel extends UserModel {
    @SerializedName("user_created_match")
    @Expose
    private List<MatchDetailResponse> userCreatedMatch = new ArrayList<MatchDetailResponse>();
    @SerializedName("user_joined_match")
    @Expose
    private List<MatchDetailResponse> userJoinedMatch = new ArrayList<MatchDetailResponse>();

    public HistoryModel(String userId, String userToken, String userName, String userEmail, String userPhoneNumber, String userProfileImage) {
        super(userId, userToken, userName, userEmail, userPhoneNumber, userProfileImage);
    }

    public List<MatchDetailResponse> getUserCreatedMatch() {
        return userCreatedMatch;
    }

    public void setUserCreatedMatch(List<MatchDetailResponse> userCreatedMatch) {
        this.userCreatedMatch = userCreatedMatch;
    }

    public List<MatchDetailResponse> getUserJoinedMatch() {
        return userJoinedMatch;
    }

    public void setUserJoinedMatch(List<MatchDetailResponse> userJoinedMatch) {
        this.userJoinedMatch = userJoinedMatch;
    }
}
