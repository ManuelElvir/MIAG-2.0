package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REQUIEREMENT {

    @Expose
    @SerializedName("REQ_ID")
    private int REQ_ID=0;

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    @Expose
    @SerializedName("REQ_NAME")
    private String REQ_NAME;

    @Expose
    @SerializedName("REQ_FILE")
    private String REQ_FILE;

    @Expose
    @SerializedName("REQ_CONTENT")
    private String REQ_CONTENT;


    public int getREQ_ID() {
        return REQ_ID;
    }

    public void setREQ_ID(int REQ_ID) {
        this.REQ_ID = REQ_ID;
    }

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public String getREQ_NAME() {
        return REQ_NAME;
    }

    public void setREQ_NAME(String REQ_NAME) {
        this.REQ_NAME = REQ_NAME;
    }

    public String getREQ_FILE() {
        return REQ_FILE;
    }

    public void setREQ_FILE(String REQ_FILE) {
        this.REQ_FILE = REQ_FILE;
    }

    public String getREQ_CONTENT() {
        return REQ_CONTENT;
    }

    public void setREQ_CONTENT(String REQ_CONTENT) {
        this.REQ_CONTENT = REQ_CONTENT;
    }
}
