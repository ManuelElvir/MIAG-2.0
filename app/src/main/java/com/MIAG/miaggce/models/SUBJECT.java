package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SUBJECT {

    @Expose
    @SerializedName("SJ_ID")
    private int SJ_ID;

    @Expose
    @SerializedName("SJ_NAME")
    private String SJ_NAME;

    public int getSJ_ID() {
        return SJ_ID;
    }

    public void setSJ_ID(int SJ_ID) {
        this.SJ_ID = SJ_ID;
    }

    public String getSJ_NAME() {
        return SJ_NAME;
    }

    public void setSJ_NAME(String SJ_NAME) {
        this.SJ_NAME = SJ_NAME;
    }
}
