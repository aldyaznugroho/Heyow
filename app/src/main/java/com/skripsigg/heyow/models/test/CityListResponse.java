package com.skripsigg.heyow.models.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldyaz on 11/4/2016.
 */

public class CityListResponse {
    @SerializedName("data")
    @Expose
    private List<CityListData> data = new ArrayList<CityListData>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     *
     * @return
     * The data
     */
    public List<CityListData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CityListData> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
