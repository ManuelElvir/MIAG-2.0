package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SUBJECT_CORRECTION {

    @Expose
    @SerializedName("TC_ID")
    private int SC_ID;

    @Expose
    @SerializedName("TC_CONTENT")
    private String SC_CONTENT;

    @Expose
    @SerializedName("PAPER2_ID")
    private int SC_PAPER1_ID=0;

    @Expose
    @SerializedName("PAPER3_ID")
    private int SC_PAPER2_ID=0;

    public int getSC_ID() {
        return SC_ID;
    }

    public void setSC_ID(int SC_ID) {
        this.SC_ID = SC_ID;
    }

    public String getSC_CONTENT() {
        return SC_CONTENT;
    }

    public void setSC_CONTENT(String SC_CONTENT) {
        this.SC_CONTENT = SC_CONTENT;
    }

    public int getSC_PAPER1_ID() {
        return SC_PAPER1_ID;
    }

    public void setSC_PAPER1_ID(int SC_PAPER1_ID) {
        this.SC_PAPER1_ID = SC_PAPER1_ID;
    }

    public int getSC_PAPER2_ID() {
        return SC_PAPER2_ID;
    }

    public void setSC_PAPER2_ID(int SC_PAPER2_ID) {
        this.SC_PAPER2_ID = SC_PAPER2_ID;
    }
}
