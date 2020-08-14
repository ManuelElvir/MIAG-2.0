package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXAM_TEST {
    @Expose
    @SerializedName("TEST_ID")
    private int TEST_ID;

    @Expose
    @SerializedName("EXAM_ID")
    private int EXAM_ID;

    @Expose
    @SerializedName("EXAM_TEST_DATE")
    private String EXAM_TEST_DATE;

    public int getTEST_ID() {
        return TEST_ID;
    }

    public void setTEST_ID(int TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    public int getEXAM_ID() {
        return EXAM_ID;
    }

    public void setEXAM_ID(int EXAM_ID) {
        this.EXAM_ID = EXAM_ID;
    }

    public String getEXAM_TEST_DATE() {
        return EXAM_TEST_DATE;
    }

    public void setEXAM_TEST_DATE(String EXAM_TEST_DATE) {
        this.EXAM_TEST_DATE = EXAM_TEST_DATE;
    }
}
