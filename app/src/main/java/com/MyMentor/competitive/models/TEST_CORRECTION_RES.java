package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TEST_CORRECTION_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<TEST_CORRECTION> test_corrections;

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

    public List<TEST_CORRECTION> getTest_corrections() {
        return test_corrections;
    }

    public void setTest_corrections(List<TEST_CORRECTION> test_corrections) {
        this.test_corrections = test_corrections;
    }
}
