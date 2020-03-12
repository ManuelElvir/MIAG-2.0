package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PAPER2 {
    @Expose
    @SerializedName("PAPER2_ID")
    private int PAPER2_ID;

    @Expose
    @SerializedName("TEST_ID")
    private int TEST_ID;

    @Expose
    @SerializedName("TEST_NAME")
    private String TEST_NAME;

    @Expose
    @SerializedName("TEST_CHRONO")
    private String TEST_CHRONO;

    @Expose
    @SerializedName("TEST_DATE")
    private String TEST_DATE;

    @Expose
    @SerializedName("TEST_CONTENT")
    private String TEST_CONTENT;

    @Expose
    @SerializedName("SJ_ID")
    private int SJ_ID;

    public int getSJ_ID() {
        return SJ_ID;
    }

    public void setSJ_ID(int SJ_ID) {
        this.SJ_ID = SJ_ID;
    }

    public int getPAPER2_ID() {
        return PAPER2_ID;
    }

    public void setPAPER2_ID(int PAPER2_ID) {
        this.PAPER2_ID = PAPER2_ID;
    }

    public int getTEST_ID() {
        return TEST_ID;
    }

    public void setTEST_ID(int TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    public String getTEST_NAME() {
        return TEST_NAME;
    }

    public void setTEST_NAME(String TEST_NAME) {
        this.TEST_NAME = TEST_NAME;
    }

    public String getTEST_CHRONO() {
        return TEST_CHRONO;
    }

    public void setTEST_CHRONO(String TEST_CHRONO) {
        this.TEST_CHRONO = TEST_CHRONO;
    }

    public String getTEST_DATE() {
        return TEST_DATE;
    }

    public void setTEST_DATE(String TEST_DATE) {
        this.TEST_DATE = TEST_DATE;
    }

    public String getTEST_CONTENT() {
        return TEST_CONTENT;
    }

    public void setTEST_CONTENT(String TEST_CONTENT) {
        this.TEST_CONTENT = TEST_CONTENT;
    }
}
