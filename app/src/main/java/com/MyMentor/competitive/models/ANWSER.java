package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ANWSER {
    @Expose
    @SerializedName("ANWS_ID")
    private int ANWS_ID;

    @Expose
    @SerializedName("ANWS_CONTENT")
    private String ANWS_CONTENT;

    @Expose
    @SerializedName("ANWS_DATE")
    private String ANWS_DATE;

    private int ANWS_STATE;

    public int getANWS_ID() {
        return ANWS_ID;
    }

    public void setANWS_ID(int ANWS_ID) {
        this.ANWS_ID = ANWS_ID;
    }

    public String getANWS_CONTENT() {
        return ANWS_CONTENT;
    }

    public void setANWS_CONTENT(String ANWS_CONTENT) {
        this.ANWS_CONTENT = ANWS_CONTENT;
    }

    public String getANWS_DATE() {
        return ANWS_DATE;
    }

    public void setANWS_DATE(String ANWS_DATE) {
        this.ANWS_DATE = ANWS_DATE;
    }

    public int getANWS_STATE() {
        return ANWS_STATE;
    }

    public void setANWS_STATE(int ANWS_STATE) {
        this.ANWS_STATE = ANWS_STATE;
    }
}
