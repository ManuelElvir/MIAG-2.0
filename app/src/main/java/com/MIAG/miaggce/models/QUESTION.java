package com.MIAG.miaggce.models;

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
    @SerializedName("PAPER1_ID")
    private int PAPER1_ID = 0;

    @Expose
    @SerializedName("CHAP_ID")
    private int CHAP_ID = 0;

    public int getCHAP_ID() {
        return CHAP_ID;
    }

    public void setCHAP_ID(int CHAP_ID) {
        this.CHAP_ID = CHAP_ID;
    }

    public int getPAPER1_ID() {
        return PAPER1_ID;
    }

    public void setPAPER1_ID(int PAPER1_ID) {
        this.PAPER1_ID = PAPER1_ID;
    }

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
}
