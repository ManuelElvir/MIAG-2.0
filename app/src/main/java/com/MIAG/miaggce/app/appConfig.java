package com.MIAG.miaggce.app;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class appConfig {
    public static String SITE_URL = "https://miag.vitteck.com/api/";
    public static String DEFAULT_KEY = "SNFT3GSWuwqZOUkqocEqyiFxyOfqpX";

    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
            urlc.setRequestProperty("User-Agent","test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setReadTimeout(1000);
            urlc.connect();
            if (urlc.getResponseCode()==200)
                return true;
            else
                return false;

        } catch (IOException e) {
            return false;
        }
    }

}
