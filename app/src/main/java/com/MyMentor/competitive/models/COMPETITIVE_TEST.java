package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COMPETITIVE_TEST {

    @Expose
    @SerializedName("TEST_ID")
    private int TEST_ID;

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    public int getTEST_ID() {
        return TEST_ID;
    }

    public void setTEST_ID(int TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }
}
