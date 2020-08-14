package com.MyMentor.competitive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FILE {

    @Expose
    @SerializedName("FILE_ID")
    private String FILE_ID;

    @Expose
    @SerializedName("FILE_NAME")
    private String FILE_NAME;

    @Expose
    @SerializedName("FILE_TYPE")
    private String FILE_TYPE;

    @Expose
    @SerializedName("FILE_FORMAT")
    private String FILE_FORMAT;

    public String getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(String FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getFILE_FORMAT() {
        return FILE_FORMAT;
    }

    public void setFILE_FORMAT(String FILE_FORMAT) {
        this.FILE_FORMAT = FILE_FORMAT;
    }
}
