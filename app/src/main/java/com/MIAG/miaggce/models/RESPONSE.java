package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESPONSE {
    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Cause")
    private String Cause;

    @Expose
    @SerializedName("api_key")
    private String User_Key;

    @Expose
    @SerializedName("student")
    private STUDENT student;

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getCause() {
        return Cause;
    }

    public void setCause(String cause) {
        Cause = cause;
    }

    public String getUser_Key() {
        return User_Key;
    }

    public void setUser_Key(String user_Key) {
        User_Key = user_Key;
    }

    public STUDENT getStudent() {
        return student;
    }

    public void setStudent(STUDENT student) {
        this.student = student;
    }
}
