package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STUDENT {
    @Expose
    @SerializedName("STD_ID")
    private String STD_ID;

    @Expose
    @SerializedName("STD_NAME")
    private String STD_NAME;

    @Expose
    @SerializedName("STD_NUMBER")
    private String STD_NUMBER;

    @Expose
    @SerializedName("STD_EMAIL")
    private String STD_EMAIL;

    @Expose
    @SerializedName("STD_TEL_PARENT1")
    private String STD_TEL_PARENT1;

    @Expose
    @SerializedName("STD_TEL_PARENT2")
    private String STD_TEL_PARENT2;

    @Expose
    @SerializedName("STD_STATE")
    private String STD_STATE;

    public String getSTD_ID() {
        return STD_ID;
    }

    public void setSTD_ID(String STD_ID) {
        this.STD_ID = STD_ID;
    }

    public String getSTD_NAME() {
        return STD_NAME;
    }

    public void setSTD_NAME(String STD_NAME) {
        this.STD_NAME = STD_NAME;
    }

    public String getSTD_NUMBER() {
        return STD_NUMBER;
    }

    public void setSTD_NUMBER(String STD_NUMBER) {
        this.STD_NUMBER = STD_NUMBER;
    }

    public String getSTD_EMAIL() {
        return STD_EMAIL;
    }

    public void setSTD_EMAIL(String STD_EMAIL) {
        this.STD_EMAIL = STD_EMAIL;
    }

    public String getSTD_TEL_PARENT1() {
        return STD_TEL_PARENT1;
    }

    public void setSTD_TEL_PARENT1(String STD_TEL_PARENT1) {
        this.STD_TEL_PARENT1 = STD_TEL_PARENT1;
    }

    public String getSTD_TEL_PARENT2() {
        return STD_TEL_PARENT2;
    }

    public void setSTD_TEL_PARENT2(String STD_TEL_PARENT2) {
        this.STD_TEL_PARENT2 = STD_TEL_PARENT2;
    }

    public String getSTD_STATE() {
        return STD_STATE;
    }

    public void setSTD_STATE(String STD_STATE) {
        this.STD_STATE = STD_STATE;
    }
}
