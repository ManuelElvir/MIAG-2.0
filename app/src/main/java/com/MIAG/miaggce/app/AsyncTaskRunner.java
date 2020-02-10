package com.MIAG.miaggce.app;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {

    private AsyncTaskListener asyncTaskListener;


    public AsyncTaskRunner(AsyncTaskListener asyncTaskListener) {
        this.asyncTaskListener = asyncTaskListener;
    }

    @Override
    protected String doInBackground(String... params) {
        while(!appConfig.isInternetAvailable()){
            //no action
        }
        return "Find new update";
    }


    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        asyncTaskListener.startDownload();
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onProgressUpdate(String... text) {

    }

    public interface AsyncTaskListener{
        void startDownload();
    }
}