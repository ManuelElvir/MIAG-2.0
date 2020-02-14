package com.MIAG.miaggce.ui.paper2_correction;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Paper2CorrectionActivity extends AppCompatActivity{

    String message;
    WebView webView;
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper2_correction);

        String htmlTitle = "<p style=\"font-size:10px\">"+getIntent().getStringExtra("title")+"</p>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);


        getPaperText();

        webView.loadDataWithBaseURL(null,message,"text/html","utf-8",null);

        Button correction = findViewById(R.id.correction);
        correction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getPaperText() {
        dbManager = new DBManager(this);
        dbManager.open();
        List<SUBJECT_CORRECTION> corrections = dbManager.getSubjectCorrectionByPaper2Id(getIntent().getIntExtra("paper",0));
        if (corrections!=null)
            if (corrections.size()>0)
                message = corrections.get(0).getSC_CONTENT();

        if (message.isEmpty()){
            InputStream is = this.getResources().openRawResource(R.raw.epreuve);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String readLine;

            try {
                while ((readLine = br.readLine()) != null) {
                    message += readLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar
                .make(webView, getString(R.string.close_correction), Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Paper2CorrectionActivity.super.onBackPressed();
                    }
                });
        snackbar.show();
    }
}
