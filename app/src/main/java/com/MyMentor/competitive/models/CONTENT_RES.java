package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CONTENT_RES {
    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<CONTENT> contents;

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

    public List<CONTENT> getContents() {
        return contents;
    }

    public void setContents(List<CONTENT> contents) {
        this.contents = contents;
    }
}
