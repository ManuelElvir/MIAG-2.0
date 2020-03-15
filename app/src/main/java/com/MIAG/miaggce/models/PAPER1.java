package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PAPER1 {

    @Expose
    @SerializedName("PAPER1_ID")
    private int PAPER1_ID;
    @Expose
    @SerializedName("TEST_NAME")
    private String TEST_NAME;

    @Expose
    @SerializedName("TEST_CHRONO")
    private String TEST_CHRONO;

    @Expose
    @SerializedName("SJ_ID")
    private int SJ_ID;

    @Expose
    @SerializedName("EXAM_ID")
    private int EXAM_ID;

    public int getPAPER1_ID() {
        return PAPER1_ID;
    }

    public void setPAPER1_ID(int PAPER1_ID) {
        this.PAPER1_ID = PAPER1_ID;
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

    public int getSJ_ID() {
        return SJ_ID;
    }

    public void setSJ_ID(int SJ_ID) {
        this.SJ_ID = SJ_ID;
    }

    public int getEXAM_ID() {
        return EXAM_ID;
    }

    public void setEXAM_ID(int EXAM_ID) {
        this.EXAM_ID = EXAM_ID;
    }
}
