package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PAST_QUESTIONS {
    @Expose
    @SerializedName("PQ_ID")
    private int PQ_ID;

    @Expose
    @SerializedName("COMP_ID")
    private int COMP_ID;

    @Expose
    @SerializedName("QCM_ID")
    private int QCM_ID;

    @Expose
    @SerializedName("PQ_TITLE")
    private String PQ_TITLE;

    @Expose
    @SerializedName("PQ_SESSION")
    private int PQ_SESSION;

    @Expose
    @SerializedName("PQ_TIMING")
    private String PQ_TIMING;

    @Expose
    @SerializedName("PQ_DATE")
    private String PQ_DATE;

    public int getPQ_ID() {
        return PQ_ID;
    }

    public void setPQ_ID(int PQ_ID) {
        this.PQ_ID = PQ_ID;
    }

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public int getQCM_ID() {
        return QCM_ID;
    }

    public void setQCM_ID(int QCM_ID) {
        this.QCM_ID = QCM_ID;
    }

    public String getSJ_ID() {
        return PQ_TITLE;
    }

    public void setSJ_ID(String PQ_TITLE) {
        this.PQ_TITLE = PQ_TITLE;
    }

    public int getPQ_SESSION() {
        return PQ_SESSION;
    }

    public void setPQ_SESSION(int PQ_SESSION) {
        this.PQ_SESSION = PQ_SESSION;
    }

    public String getPQ_TIMING() {
        return PQ_TIMING;
    }

    public void setPQ_TIMING(String PQ_TIMING) {
        this.PQ_TIMING = PQ_TIMING;
    }

    public String getPQ_DATE() {
        return PQ_DATE;
    }

    public void setPQ_DATE(String PQ_DATE) {
        this.PQ_DATE = PQ_DATE;
    }

    public String getPQ_TITLE() {
        return PQ_TITLE;
    }

    public void setPQ_TITLE(String PQ_TITLE) {
        this.PQ_TITLE = PQ_TITLE;
    }
}
