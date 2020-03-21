package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TUTORIAL {

    @Expose
    @SerializedName("TUT_ID")
    private int TUTO_ID;

    @Expose
    @SerializedName("TUT_NAME")
    private String TUTO_NAME;

    @Expose
    @SerializedName("CHAP_ID")
    private int CHAP_ID;

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    public TUTORIAL() {
        this.TUTO_ID = 0;
    }

    public int getTUTO_ID() {
        return TUTO_ID;
    }

    public void setTUTO_ID(int TUTO_ID) {
        this.TUTO_ID = TUTO_ID;
    }

    public String getTUTO_NAME() {
        return TUTO_NAME;
    }

    public void setTUTO_NAME(String TUTO_NAME) {
        this.TUTO_NAME = TUTO_NAME;
    }

    public int getCHAP_ID() {
        return CHAP_ID;
    }

    public void setCHAP_ID(int CHAP_ID) {
        this.CHAP_ID = CHAP_ID;
    }

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }
}
