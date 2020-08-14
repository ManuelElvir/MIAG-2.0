package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TEST_CORRECTION {


    @Expose
    @SerializedName("TC_ID")
    private int TC_ID;

    @Expose
    @SerializedName("TC_TEST_ID")
    private int TC_TEST_ID;

    @Expose
    @SerializedName("TC_CONTENT")
    private int TC_CONTENT;

    @Expose
    @SerializedName("TC_DATE ")
    private String TC_DATE ;

    public int getTC_ID() {
        return TC_ID;
    }

    public void setTC_ID(int TC_ID) {
        this.TC_ID = TC_ID;
    }

    public int getTC_TEST_ID() {
        return TC_TEST_ID;
    }

    public void setTC_TEST_ID(int TC_TEST_ID) {
        this.TC_TEST_ID = TC_TEST_ID;
    }

    public int getTC_CONTENT() {
        return TC_CONTENT;
    }

    public void setTC_CONTENT(int TC_CONTENT) {
        this.TC_CONTENT = TC_CONTENT;
    }

    public String getTC_DATE() {
        return TC_DATE;
    }

    public void setTC_DATE(String TC_DATE) {
        this.TC_DATE = TC_DATE;
    }
}
