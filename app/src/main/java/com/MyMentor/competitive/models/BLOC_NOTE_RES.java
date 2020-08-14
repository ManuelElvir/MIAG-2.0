package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BLOC_NOTE_RES {

    @Expose
    @SerializedName("Success")
    private boolean Success;


    @Expose
    @SerializedName("last")
    private int last;


    @Expose
    @SerializedName("data")
    private List<BLOC_NOTE> bloc_notes;

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

    public List<BLOC_NOTE> getBloc_notes() {
        return bloc_notes;
    }

    public void setBloc_notes(List<BLOC_NOTE> bloc_notes) {
        this.bloc_notes = bloc_notes;
    }
}
