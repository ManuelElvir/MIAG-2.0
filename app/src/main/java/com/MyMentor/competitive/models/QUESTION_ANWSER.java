package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QUESTION_ANWSER {

    @Expose
    @SerializedName("ANWS_ID")
    private int QUEST_ANWS_ID;

    @Expose
    @SerializedName("ANWS_ID")
    private int ANWS_ID;

    @Expose
    @SerializedName("ANWSER_STATE")
    private int ANWS_STATE;

    @Expose
    @SerializedName("QUEST_ID")
    private int QUEST_ID;

    public int getANWS_ID() {
        return ANWS_ID;
    }

    public void setANWS_ID(int ANWS_ID) {
        this.ANWS_ID = ANWS_ID;
    }

    public int getANWS_STATE() {
        return ANWS_STATE;
    }

    public void setANWS_STATE(int ANWS_STATE) {
        this.ANWS_STATE = ANWS_STATE;
    }

    public int getQUEST_ID() {
        return QUEST_ID;
    }

    public void setQUEST_ID(int QUEST_ID) {
        this.QUEST_ID = QUEST_ID;
    }
}
