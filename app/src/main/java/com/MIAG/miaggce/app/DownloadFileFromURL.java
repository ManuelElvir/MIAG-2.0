package com.MIAG.miaggce.app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.MIAG.miaggce.ui.competitive.CompetitiveView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {
    private ProgressDialog pd;
    private String pathFile = "";
    private String req_name;
    private String fileName;
    @SuppressLint("StaticFieldLeak")
    private
    Context context;
    private CompetitiveView view;

    public DownloadFileFromURL(String req_name, String fileName, Context context, CompetitiveView view) {
        this.req_name = req_name;
        this.fileName = fileName;
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(true);
        pd.show();
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;

        try {
            String pathFolder = Environment.getExternalStorageDirectory() + "/Files";
            pathFile = pathFolder + "/"+fileName+".pdf";
            File futureStudioIconFile = new File(pathFolder);
            if(!futureStudioIconFile.exists()){
                futureStudioIconFile.mkdirs();
            }

            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lengthOfFile = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(pathFile);

            byte[] data = new byte[1024]; //anybody know what 1024 means ?
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();


        } catch (Exception e) {
            Log.e("Error: ", ""+e.getMessage());
            view.onErrorLoadind("Please check tou internet conection");
        }

        return pathFile;
    }

    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        pd.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
        if (pd!=null) {
            pd.dismiss();
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        view.openRequierement(pathFile,req_name);
        //Intent i = new Intent(Intent.ACTION_VIEW);

        //i.setDataAndType(Uri.fromFile(new File(file_url)), "application/vnd.android.package-archive" );
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //context.startActivity(i);
    }

}