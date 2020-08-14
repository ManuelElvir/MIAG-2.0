package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TUTORIAL_QCM {

    @Expose
    @SerializedName("TUT_ID")
    private int TUTO_ID;

    @Expose
    @SerializedName("QCM_ID")
    private int QCM_ID;

    public int getTUTO_ID() {
        return TUTO_ID;
    }

    public void setTUTO_ID(int TUTO_ID) {
        this.TUTO_ID = TUTO_ID;
    }

    public int getQCM_ID() {
        return QCM_ID;
    }

    public void setQCM_ID(int QCM_ID) {
        this.QCM_ID = QCM_ID;
    }
}
