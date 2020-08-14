package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class STAFFMEMBER_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<STAFFMEMBER> staffmembers;

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

    public List<STAFFMEMBER> getStaffmembers() {
        return staffmembers;
    }

    public void setStaffmembers(List<STAFFMEMBER> staffmembers) {
        this.staffmembers = staffmembers;
    }
}
