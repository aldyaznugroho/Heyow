package com.skripsigg.heyow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aldyaz on 2/9/2017.
 */

public class NotificationBodyPayload {
    @SerializedName("data")
    @Expose
    private NotificationData data;
    @SerializedName("registration_ids")
    @Expose
    private List<String> registrationIds = null;

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public List<String> getRegistrationIds() {
        return registrationIds;
    }

    public void setRegistrationIds(List<String> registrationIds) {
        this.registrationIds = registrationIds;
    }
}
