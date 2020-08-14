package com.MyMentor.competitive.api;

import com.MyMentor.competitive.app.appConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getApiClient(String userKey){

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    //.client(okHttpClient)
                    .baseUrl(appConfig.SITE_URL+userKey+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
