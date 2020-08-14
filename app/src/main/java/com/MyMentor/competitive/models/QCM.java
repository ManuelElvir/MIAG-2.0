package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QCM {
    @Expose
    @SerializedName("QCM_ID")
    private int QCM_ID;

    @Expose
    @SerializedName("QCM_TITLE")
    private String QCM_TITLE;

    @Expose
    @SerializedName("QCM_DATE")
    private String QCM_DATE;

    public int getQCM_ID() {
        return QCM_ID;
    }

    public void setQCM_ID(int QCM_ID) {
        this.QCM_ID = QCM_ID;
    }

    public String getQCM_TITLE() {
        return QCM_TITLE;
    }

    public void setQCM_TITLE(String QCM_TITLE) {
        this.QCM_TITLE = QCM_TITLE;
    }

    public String getQCM_DATE() {
        return QCM_DATE;
    }

    public void setQCM_DATE(String QCM_DATE) {
        this.QCM_DATE = QCM_DATE;
    }
}
