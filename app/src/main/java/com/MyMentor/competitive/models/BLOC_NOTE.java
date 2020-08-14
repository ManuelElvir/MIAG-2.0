package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BLOC_NOTE {
    @Expose
    @SerializedName("NOTE_ID")
    private int NOTE_ID;

    @Expose
    @SerializedName("USER_ID")
    private int USER_ID;

    @Expose
    @SerializedName("NOTE_CONTENT")
    private String NOTE_CONTENT;

    @Expose
    @SerializedName("NOTE_DATE")
    private String NOTE_DATE;

    @Expose
    @SerializedName("NOTE_COLOR")
    private int NOTE_COLOR;

    public int getNOTE_COLOR() {
        return NOTE_COLOR;
    }

    public void setNOTE_COLOR(int NOTE_COLOR) {
        this.NOTE_COLOR = NOTE_COLOR;
    }

    public int getNOTE_ID() {
        return NOTE_ID;
    }

    public void setNOTE_ID(int NOTE_ID) {
        this.NOTE_ID = NOTE_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
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
}
