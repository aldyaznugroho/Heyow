package com.skripsigg.heyow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aldyaz on 12/21/2016.
 */

public class LocationRadiusModel {
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("address_latitude")
    @Expose
    private String addressLatitude;
    @SerializedName("address_longitude")
    @Expose
    private String addressLongitude;
    @SerializedName("distance")
    @Expose
    private int distance;

    public LocationRadiusModel() {
    }

    public LocationRadiusModel(String addressName, String addressLatitude, String addressLongitude, int distance) {
        this.addressName = addressName;
        this.addressLatitude = addressLatitude;
        this.addressLongitude = addressLongitude;
        this.distance = distance;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public String getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
