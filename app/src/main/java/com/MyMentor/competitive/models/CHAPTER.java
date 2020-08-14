package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CHAPTER {

    @Expose
    @SerializedName("CHAP_ID")
    private int CHAP_ID;

    @Expose
    @SerializedName("SJ_ID")
    private int SJ_ID;

    @Expose
    @SerializedName("CHAP_NAME")
    private String CHAP_NAME;

    @Expose
    @SerializedName("CHAP_DATE")
    private String CHAP_DATE;

    public String getCHAP_DATE() {
        return CHAP_DATE;
    }

    public void setCHAP_DATE(String CHAP_DATE) {
        this.CHAP_DATE = CHAP_DATE;
    }

    public int getCHAP_ID() {
        return CHAP_ID;
    }

    public void setCHAP_ID(int CHAP_ID) {
        this.CHAP_ID = CHAP_ID;
    }

    public int getSJ_ID() {
        return SJ_ID;
    }

    public void setSJ_ID(int SJ_ID) {
        this.SJ_ID = SJ_ID;
    }

    public String getCHAP_NAME() {
        return CHAP_NAME;
    }

    public void setCHAP_NAME(String CHAP_NAME) {
        this.CHAP_NAME = CHAP_NAME;
    }
}
