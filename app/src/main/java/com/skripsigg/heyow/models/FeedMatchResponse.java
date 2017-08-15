package com.skripsigg.heyow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aldyaz on 12/14/2016.
 */

public class FeedMatchResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<MatchDetailResponse> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MatchDetailResponse> getResult() {
        return result;
    }

    public void setResult(List<MatchDetailResponse> result) {
        this.result = result;
    }
}
