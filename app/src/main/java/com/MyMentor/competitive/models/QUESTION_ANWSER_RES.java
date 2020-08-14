package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QUESTION_ANWSER_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("last")
    private int last;

    @Expose
    @SerializedName("data")
    private List<QUESTION_ANWSER> question_anwsers;

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

    public List<QUESTION_ANWSER> getQuestion_anwsers() {
        return question_anwsers;
    }

    public void setQuestion_anwsers(List<QUESTION_ANWSER> question_anwsers) {
        this.question_anwsers = question_anwsers;
    }
}
