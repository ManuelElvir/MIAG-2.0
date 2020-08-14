package com.MyMentor.competitive.ui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.MyMentor.competitive.R;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.models.NOTE;
import com.MyMentor.competitive.models.REQUIEREMENT;

import java.util.Objects;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for requierement and note
 */
public class TextActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requierement);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);

        int noteId = getIntent().getIntExtra("noteId",0);
        int requierementId = getIntent().getIntExtra("requierementId",0);

        DBManager dbManager = new DBManager(this);
        dbManager.open();
        String content = null;
        if (noteId!=0){
            NOTE note = dbManager.getNoteById(noteId);
            content = note.getNOTE_CONTENT();
        }
        else if (requierementId!= 0){
            REQUIEREMENT requierement = dbManager.getRequierementById(requierementId);
            content = requierement.getREQ_CONTENT();
        }
        else
            showErrorDialogBox();

        dbManager.close();

        if (content==null)
            showErrorDialogBox();
        else{
            String htmlTitle = "<p style=\"font-size:10px\">"+getIntent().getStringExtra("title")+"</p>";
            Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress>95)
                        progressBar.setVisibility(View.GONE);
                    else
                        progressBar.setProgress(newProgress);
                    super.onProgressChanged(view, newProgress);
                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
            if (Objects.requireNonNull(content).contains(".pdf"))//if content is a pdf url
            {
                webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ content);
                Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
            }
            else
                webView.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("InflateParams")
    private void showErrorDialogBox(){
        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView;
        promptView = layoutInflater.inflate(R.layout.layout_error, null);
        Button buttonClose = promptView.findViewById(R.id.close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alertD.setView(promptView);
        alertD.show();
    }

}
