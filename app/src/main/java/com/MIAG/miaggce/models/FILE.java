package com.MIAG.miaggce.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FILE {

    //@Expose
    //@SerializedName("FILE_NAME")
    //private String FILE_NAME;

    @Expose
    @SerializedName("FILE_URL")
    private String FILE_URL;

   // public String getFILE_NAME() {
   //     return FILE_NAME;
   // }

   // public void setFILE_NAME(String FILE_NAME) {
   //     this.FILE_NAME = FILE_NAME;
   // }

    public String getFILE_URL() {
        return FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }
}
