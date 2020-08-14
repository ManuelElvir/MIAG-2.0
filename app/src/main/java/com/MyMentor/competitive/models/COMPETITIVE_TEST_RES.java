package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class COMPETITIVE_TEST_RES {@Expose
@SerializedName("Success")
private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<COMPETITIVE_TEST> competitive_tests;

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

    public List<COMPETITIVE_TEST> getCompetitive_tests() {
        return competitive_tests;
    }

    public void setCompetitive_tests(List<COMPETITIVE_TEST> competitive_tests) {
        this.competitive_tests = competitive_tests;
    }
}
