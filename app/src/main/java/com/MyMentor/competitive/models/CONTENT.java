package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CONTENT {

    @Expose
    @SerializedName("QCM_ID")
    private int QCM_ID;

    @Expose
    @SerializedName("QUEST_ID")
    private int QUEST_ID;

    public int getQCM_ID() {
        return QCM_ID;
    }

    public void setQCM_ID(int QCM_ID) {
        this.QCM_ID = QCM_ID;
    }

    public int getQUEST_ID() {
        return QUEST_ID;
    }

    public void setQUEST_ID(int QUEST_ID) {
        this.QUEST_ID = QUEST_ID;
    }
}
