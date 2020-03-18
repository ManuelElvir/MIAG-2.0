package com.MIAG.miaggce.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class appConfig {
    public static String SITE_URL = "https://miag.vitteck.com/api/";
    public static String DEFAULT_KEY = "AVZakpq5CNWqCx2Z3LINeGefwuBqbP";

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
