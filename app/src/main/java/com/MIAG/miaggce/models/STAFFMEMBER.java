package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STAFFMEMBER {
    @Expose
    @SerializedName("SM_ID")
    private int SM_ID;

    @Expose
    @SerializedName("SM_NAME")
    private String SM_NAME;

    @Expose
    @SerializedName("SM_FUNCTION")
    private String SM_FUNCTION;

    @Expose
    @SerializedName("SM_NUMBER")
    private String SM_NUMBER;

    @Expose
    @SerializedName("SM_DATE")
    private String SM_DATE;

    public int getSM_ID() {
        return SM_ID;
    }

    public void setSM_ID(int SM_ID) {
        this.SM_ID = SM_ID;
    }

    public String getSM_NAME() {
        return SM_NAME;
    }

    public void setSM_NAME(String SM_NAME) {
        this.SM_NAME = SM_NAME;
    }

    public String getSM_FUNCTION() {
        return SM_FUNCTION;
    }

    public void setSM_FUNCTION(String SM_FUNCTION) {
        this.SM_FUNCTION = SM_FUNCTION;
    }

    public String getSM_NUMBER() {
        return SM_NUMBER;
    }

    public void setSM_NUMBER(String SM_NUMBER) {
        this.SM_NUMBER = SM_NUMBER;
    }

    public String getSM_DATE() {
        return SM_DATE;
    }

    public void setSM_DATE(String SM_DATE) {
        this.SM_DATE = SM_DATE;
    }
}
