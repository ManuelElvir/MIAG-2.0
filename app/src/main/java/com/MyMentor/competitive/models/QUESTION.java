package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QUESTION {
    @Expose
    @SerializedName("QUEST_ID")
    private int QUEST_ID;

    @Expose
    @SerializedName("QUEST_LABEL")
    private String QUEST_LABEL;

    @Expose
    @SerializedName("QUEST_ANSWER")
    private String QUEST_ANSWER;

    @Expose
    @SerializedName("QUEST_TYPE")
    private String QUEST_TYPE;

    @Expose
    @SerializedName("QUEST_DATE")
    private String QUEST_DATE;

    public int getQUEST_ID() {
        return QUEST_ID;
    }

    public void setQUEST_ID(int QUEST_ID) {
        this.QUEST_ID = QUEST_ID;
    }

    public String getQUEST_LABEL() {
        return QUEST_LABEL;
    }

    public void setQUEST_LABEL(String QUEST_LABEL) {
        this.QUEST_LABEL = QUEST_LABEL;
    }

    public String getQUEST_ANSWER() {
        return QUEST_ANSWER;
    }

    public void setQUEST_ANSWER(String QUEST_ANSWER) {
        this.QUEST_ANSWER = QUEST_ANSWER;
    }

    public String getQUEST_TYPE() {
        return QUEST_TYPE;
    }

    public void setQUEST_TYPE(String QUEST_TYPE) {
        this.QUEST_TYPE = QUEST_TYPE;
    }

    public String getQUEST_DATE() {
        return QUEST_DATE;
    }

    public void setQUEST_DATE(String QUEST_DATE) {
        this.QUEST_DATE = QUEST_DATE;
    }
}
