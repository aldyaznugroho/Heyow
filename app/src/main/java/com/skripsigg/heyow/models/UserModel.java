package com.skripsigg.heyow.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aldyaz on 10/24/2016.
 */

public class UserModel implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_phone_number")
    @Expose
    private String userPhoneNumber;
    @SerializedName("user_profile_image")
    @Expose
    private String userProfileImage;

    public UserModel(String userId, String userToken, String userName, String userEmail, String userPhoneNumber, String userProfileImage) {
        this.userId = userId;
        this.userToken = userToken;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfileImage = userProfileImage;
    }

    protected UserModel(Parcel in) {
        userId = in.readString();
        userToken = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPhoneNumber = in.readString();
        userProfileImage = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     *
     * @param userToken
     * The user_token
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     *
     * @return
     * The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     * The userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     *
     * @param userEmail
     * The user_email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     *
     * @return
     * The userPhoneNumber
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     *
     * @param userPhoneNumber
     * The user_phone_number
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     *
     * @return
     * The userProfileImage
     */
    public String getUserProfileImage() {
        return userProfileImage;
    }

    /**
     *
     * @param userProfileImage
     * The user_profile_image
     */
    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(userToken);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userPhoneNumber);
        parcel.writeString(userProfileImage);
    }
}
