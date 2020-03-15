package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STAFFMEMBER {
    @Expose
    @SerializedName("SM_NAME")
    private String SM_NAME;

    @Expose
    @SerializedName("SM_FUNCTION")
    private String SM_FUNCTION;

    @Expose
    @SerializedName("SM_NUMBER")
    private String SM_NUMBER;

    @Expose
    @SerializedName("SM_IMAGE")
    private String SM_IMAGE;

    public String getSM_NAME() {
        return SM_NAME;
    }

    public void setSM_NAME(String SM_NAME) {
        this.SM_NAME = SM_NAME;
    }

    public String getSM_FUNCTION() {
        return SM_FUNCTION;
    }

    public void setSM_FUNCTION(String SM_FUNCTION) {
        this.SM_FUNCTION = SM_FUNCTION;
    }

    public String getSM_NUMBER() {
        return SM_NUMBER;
    }

    public void setSM_NUMBER(String SM_NUMBER) {
        this.SM_NUMBER = SM_NUMBER;
    }

    public String getSM_IMAGE() {
        return SM_IMAGE;
    }

    public void setSM_IMAGE(String SM_IMAGE) {
        this.SM_IMAGE = SM_IMAGE;
    }
}
