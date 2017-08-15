package com.skripsigg.heyow.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aldyaz on 1/7/2017.
 */

public class SearchRequestModel implements Parcelable {
    private String matchVenue;
    private String matchLocation;
    private String matchCategory;

    public SearchRequestModel() {
    }

    protected SearchRequestModel(Parcel in) {
        matchVenue = in.readString();
        matchLocation = in.readString();
        matchCategory = in.readString();
    }

    public static final Creator<SearchRequestModel> CREATOR = new Creator<SearchRequestModel>() {
        @Override
        public SearchRequestModel createFromParcel(Parcel in) {
            return new SearchRequestModel(in);
        }

        @Override
        public SearchRequestModel[] newArray(int size) {
            return new SearchRequestModel[size];
        }
    };

    public String getMatchVenue() {
        return matchVenue;
    }

    public void setMatchVenue(String matchVenue) {
        this.matchVenue = matchVenue;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public String getMatchCategory() {
        return matchCategory;
    }

    public void setMatchCategory(String matchCategory) {
        this.matchCategory = matchCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(matchVenue);
        parcel.writeString(matchLocation);
        parcel.writeString(matchCategory);
    }
}
