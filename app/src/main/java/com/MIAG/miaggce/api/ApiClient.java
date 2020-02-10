package com.MIAG.miaggce.api;

import com.MIAG.miaggce.app.appConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getApiClient(String userKey){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(appConfig.SITE_URL+userKey+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
