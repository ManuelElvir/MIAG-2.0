package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SUBJECT_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<SUBJECT> subjects;

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

    public List<SUBJECT> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SUBJECT> subjects) {
        this.subjects = subjects;
    }
}
