package com.skripsigg.heyow.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aldyaz on 12/20/2016.
 */

public class MatchQuota implements Parcelable {
    @SerializedName("qouta_id")
    @Expose
    private String qoutaId;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("quota_max")
    @Expose
    private String quotaMax;
    @SerializedName("quota_now")
    @Expose
    private String quotaNow;

    public MatchQuota() {
    }

    protected MatchQuota(Parcel in) {
        qoutaId = in.readString();
        matchId = in.readString();
        quotaMax = in.readString();
        quotaNow = in.readString();
    }

    public static final Creator<MatchQuota> CREATOR = new Creator<MatchQuota>() {
        @Override
        public MatchQuota createFromParcel(Parcel in) {
            return new MatchQuota(in);
        }

        @Override
        public MatchQuota[] newArray(int size) {
            return new MatchQuota[size];
        }
    };

    public String getQoutaId() {
        return qoutaId;
    }

    public void setQoutaId(String qoutaId) {
        this.qoutaId = qoutaId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(String quotaMax) {
        this.quotaMax = quotaMax;
    }

    public String getQuotaNow() {
        return quotaNow;
    }

    public void setQuotaNow(String quotaNow) {
        this.quotaNow = quotaNow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qoutaId);
        parcel.writeString(matchId);
        parcel.writeString(quotaMax);
        parcel.writeString(quotaNow);
    }
}
