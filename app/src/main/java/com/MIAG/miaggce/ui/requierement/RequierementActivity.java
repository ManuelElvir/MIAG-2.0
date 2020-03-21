package com.MIAG.miaggce.ui.requierement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.MIAG.miaggce.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Objects;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for requierement, he show pdf file for requierement for somebody competitive
 */
public class RequierementActivity extends AppCompatActivity {

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requierement);
        webView = findViewById(R.id.webview);
        webView.setVisibility(View.GONE);

        String htmlTitle = "<p style=\"font-size:10px\">"+getIntent().getStringExtra("title")+"</p>";
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));


        if(getIntent().getBooleanExtra("isFile",false)){
            String pathFile = getIntent().getStringExtra("pathFile");
            Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            PDFView pdfView = findViewById(R.id.pdfView);
            if (pathFile!=null){
                File file = new File( pathFile );
                pdfView.fromFile(file);
            }
            else
                pdfView.fromAsset(getPdfUriFile()).load();
        }else{
            webView.setVisibility(View.VISIBLE);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);

            webView.loadDataWithBaseURL(null,getIntent().getStringExtra("req_content"),"text/html","utf-8",null);
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private String getPdfUriFile() {
        return "pdf_standard.pdf";
    }
}
