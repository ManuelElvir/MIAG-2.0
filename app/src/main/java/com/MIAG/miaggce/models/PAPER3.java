package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PAPER3 {

    @Expose
    @SerializedName("PAPER3_ID")
    private int PAPER3_ID =0;

    @Expose
    @SerializedName("SJ_ID")
    private int SJ_ID;

    @Expose
    @SerializedName("TEST_NAME")
    private String TEST_NAME;
    @Expose
    @SerializedName("TEST_CONTENT")
    private String TEST_CONTENT;

    @Expose
    @SerializedName("EXAM_ID")
    private int EXAM_ID;

    public int getPAPER3_ID() {
        return PAPER3_ID;
    }

    public void setPAPER3_ID(int PAPER3_ID) {
        this.PAPER3_ID = PAPER3_ID;
    }

    public int getSJ_ID() {
        return SJ_ID;
    }

    public void setSJ_ID(int SJ_ID) {
        this.SJ_ID = SJ_ID;
    }

    public String getTEST_NAME() {
        return TEST_NAME;
    }

    public void setTEST_NAME(String TEST_NAME) {
        this.TEST_NAME = TEST_NAME;
    }

    public String getTEST_CONTENT() {
        return TEST_CONTENT;
    }

    public void setTEST_CONTENT(String TEST_CONTENT) {
        this.TEST_CONTENT = TEST_CONTENT;
    }

    public int getEXAM_ID() {
        return EXAM_ID;
    }

    public void setEXAM_ID(int EXAM_ID) {
        this.EXAM_ID = EXAM_ID;
    }
}
