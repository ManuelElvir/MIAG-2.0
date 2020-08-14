package com.MyMentor.competitive.app;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class appConfig {
    public static String SITE_URL = "https://mymentor.vitteck.com/app/api/";
    public static String DEFAULT_KEY = "5HSMEvWnCdc5EZDCKZpbwbAHGiIlwe";
    public static  final String PREFERENCE = "MY_MENTOR_CONCOURS", ID = "id", NUMBER = "number", NAME = "name", EMAIL = "email", PASSWORD = "password", PARENT1 = "parent1", PARENT2 = "parent2", ENABLE = "enable", USERKEY = "userkey", REGISTER_KEY = "all_func_key";

    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
            urlc.setRequestProperty("User-Agent","test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setReadTimeout(1000);
            urlc.connect();
            return urlc.getResponseCode() == 200;

        } catch (IOException e) {
            return false;
        }
    }



    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
