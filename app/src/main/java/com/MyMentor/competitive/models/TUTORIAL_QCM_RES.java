package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TUTORIAL_QCM_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<TUTORIAL_QCM> tutorial_qcms;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public List<TUTORIAL_QCM> getTutorial_qcms() {
        return tutorial_qcms;
    }

    public void setTutorial_qcms(List<TUTORIAL_QCM> tutorial_qcms) {
        this.tutorial_qcms = tutorial_qcms;
    }
}
