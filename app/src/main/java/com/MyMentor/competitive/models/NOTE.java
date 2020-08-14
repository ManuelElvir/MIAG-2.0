package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NOTE {
    @Expose
    @SerializedName("NOTE_ID")
    private int NOTE_ID;

    @Expose
    @SerializedName("NOTE_CONTENT")
    private String NOTE_CONTENT;

    @Expose
    @SerializedName("NOTE_DATE")
    private String NOTE_DATE;

    @Expose
    @SerializedName("CHAP_ID")
    private int CHAP_ID;

    public int getNOTE_ID() {
        return NOTE_ID;
    }

    public void setNOTE_ID(int NOTE_ID) {
        this.NOTE_ID = NOTE_ID;
    }

    public String getNOTE_CONTENT() {
        return NOTE_CONTENT;
    }

    public void setNOTE_CONTENT(String NOTE_CONTENT) {
        this.NOTE_CONTENT = NOTE_CONTENT;
    }

    public String getNOTE_DATE() {
        return NOTE_DATE;
    }

    public void setNOTE_DATE(String NOTE_DATE) {
        this.NOTE_DATE = NOTE_DATE;
    }

    public int getCHAP_ID() {
        return CHAP_ID;
    }

    public void setCHAP_ID(int CHAP_ID) {
        this.CHAP_ID = CHAP_ID;
    }
}
