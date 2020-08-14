package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COMPETITIVE {

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    @Expose
    @SerializedName("COMP_NAME")
    private String COMP_NAME;

    @Expose
    @SerializedName("COMP_DATE ")
    private String COMP_DATE ;

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    public void setCOMP_NAME(String COMP_NAME) {
        this.COMP_NAME = COMP_NAME;
    }

    public String getCOMP_DATE() {
        return COMP_DATE;
    }

    public void setCOMP_DATE(String COMP_DATE) {
        this.COMP_DATE = COMP_DATE;
    }
}
