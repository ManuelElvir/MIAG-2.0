package com.MIAG.miaggce.ui.requierement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.MIAG.miaggce.R;
import java.util.Objects;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for requierement, he show pdf file for requierement for somebody competitive
 */
public class RequierementActivity extends AppCompatActivity {
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requierement);
        webView = findViewById(R.id.webview);
        webView.setVisibility(View.GONE);

        String htmlTitle = "<p style=\"font-size:10px\">"+getIntent().getStringExtra("title")+"</p>";
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (Objects.requireNonNull(getIntent().getStringExtra("req_content")).contains(".pdf"))//if content is a pdf url
        {
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ getIntent().getStringExtra("req_content"));
            Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
        }
        else
            webView.loadDataWithBaseURL(null,getIntent().getStringExtra("req_content"),"text/html","utf-8",null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
