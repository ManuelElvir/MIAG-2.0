package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXAM {
    @Expose
    @SerializedName("EXAM_ID")
    private int EXAM_ID;

    @Expose
    @SerializedName("EXAM_NAME")
    private String EXAM_NAME;

    @Expose
    @SerializedName("EXAM_DATE_START")
    private String EXAM_DATE_START;

    @Expose
    @SerializedName("EXAM_DATE_END")
    private String EXAM_DATE_END;

    public int getEXAM_ID() {
        return EXAM_ID;
    }

    public void setEXAM_ID(int EXAM_ID) {
        this.EXAM_ID = EXAM_ID;
    }

    public String getEXAM_NAME() {
        return EXAM_NAME;
    }

    public void setEXAM_NAME(String EXAM_NAME) {
        this.EXAM_NAME = EXAM_NAME;
    }

    public String getEXAM_DATE_START() {
        return EXAM_DATE_START;
    }

    public void setEXAM_DATE_START(String EXAM_DATE_START) {
        this.EXAM_DATE_START = EXAM_DATE_START;
    }

    public String getEXAM_DATE_END() {
        return EXAM_DATE_END;
    }

    public void setEXAM_DATE_END(String EXAM_DATE_END) {
        this.EXAM_DATE_END = EXAM_DATE_END;
    }
}