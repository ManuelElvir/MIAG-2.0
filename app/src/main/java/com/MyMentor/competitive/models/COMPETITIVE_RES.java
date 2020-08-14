package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class COMPETITIVE_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;


    @Expose
    @SerializedName("last")
    private int last;


    @Expose
    @SerializedName("data")
    private List<COMPETITIVE> competitives;

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

    public List<COMPETITIVE> getCompetitives() {
        return competitives;
    }

    public void setCompetitives(List<COMPETITIVE> competitives) {
        this.competitives = competitives;
    }
}
