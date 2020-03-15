package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COMPETITIVE {

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    @Expose
    @SerializedName("COMP_NAME")
    private String COMP_NAME;

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
}
