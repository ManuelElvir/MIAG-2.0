package com.MIAG.miaggce.ui.paper2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.app.AudioPlayer;
import com.MIAG.miaggce.ui.paper2_correction.Paper2CorrectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Paper2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView hour, minute, second;
    ProgressBar progressHour, progressMinute, progressSecond;
    LinearLayout card;
    int paperId;
    private long timeleftinmillisecond, timeTotal;
    private CountDownTimer countDownTimer;
    private AudioPlayer audioPlayer = new AudioPlayer();
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper2);

        String htmlTitle = "<span style=\"font-size:12px\">"+getIntent().getStringExtra("title")+"</span>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);


        getPaperText();
        webView.loadDataWithBaseURL(null,message,"text/html","utf-8",null);

        Button correction = findViewById(R.id.correction);
        correction.setOnClickListener(this);
        card = findViewById(R.id.card_timer);
        if (getIntent().getBooleanExtra("no_timer",false)){

            card.setVisibility(View.GONE);
        }else {
            timeTotal= timeleftinmillisecond = 60000*getIntent().getIntExtra("time",12);


            hour = findViewById(R.id.text_hour);
            minute = findViewById(R.id.text_minute);
            second = findViewById(R.id.text_second);
            progressHour = findViewById(R.id.progress_hour);
            progressMinute = findViewById(R.id.progress_minute);
            progressSecond =  findViewById(R.id.progress_second);



            int time_hours = (int) timeTotal/3600000;
            int time_minutes = (int) (timeTotal - (time_hours)*3600000)/60000;
            Toast.makeText(this,getString(R.string.message_star).replaceAll("xx",""+time_hours).replaceAll("yy",""+time_minutes),Toast.LENGTH_SHORT).show();
            progressHour.setMax(time_hours);
            if(timeTotal>3600000){
                progressHour.setMax(time_hours);
                progressMinute.setMax(60);
                progressHour.setProgress(0);
            }
            else{
                progressHour.setMax(1);
                progressMinute.setMax(time_minutes);
                progressHour.setProgress(1);
            }
            progressSecond.setMax(60);

            progressMinute.setProgress(0);
            progressSecond.setProgress(0);

            startCount();
        }

    }

    private void getPaperText() {
        InputStream is = this.getResources().openRawResource(R.raw.epreuve);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            while ((readLine = br.readLine()) != null) {

                message += readLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCount() {
        countDownTimer = new CountDownTimer(timeleftinmillisecond, 1000) {
            @Override
            public void onTick(long l) {
                timeleftinmillisecond = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Snackbar snackbar = Snackbar
                        .make(card, getString(R.string.time_over), Snackbar.LENGTH_LONG);
                snackbar.show();
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                Intent intent = new Intent(Paper2Activity.this, Paper2CorrectionActivity.class);
                intent.putExtra("title","Correction : "+getIntent().getStringExtra("title"));
                startActivity(intent);
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    private void updateTimer() {
        long timeUsedMilliSecond = timeTotal - timeleftinmillisecond;
        long time_second = timeUsedMilliSecond/1000;
        long time_minutes = time_second/60;
        int time_hours = (int) time_minutes/60;
        int minute_left = (int) (time_minutes - (time_hours*60));
        int second_left = (int) (time_second - (time_minutes*60));
        if (time_hours<10)
            this.hour.setText("0"+time_hours);
        else
            this.hour.setText(""+time_hours);
        if (minute_left<10)
            this.minute.setText("0"+minute_left);
        else
            this.minute.setText(""+minute_left);
        if (second_left<10)
            this.second.setText("0"+second_left);
        else
            this.second.setText(""+second_left);

        if (timeleftinmillisecond<=((timeTotal/2)+500) && ((timeTotal/2)-500)<=timeleftinmillisecond){
            audioPlayer.play(this,R.raw.bip_1);
            int time_hours_felt = (int) timeleftinmillisecond/3600000;
            int time_minutes_left = (int) (timeleftinmillisecond - (time_hours)*3600000)/60000;
            Toast.makeText(this,getString(R.string.message_star2).replaceAll("xx",""+time_hours_felt).replaceAll("yy",""+time_minutes_left),Toast.LENGTH_SHORT).show();
        }

        if (timeleftinmillisecond<=((timeTotal/4)+500) && ((timeTotal/4)-500)<=timeleftinmillisecond){
            audioPlayer.play(this,R.raw.bip_1);
            int time_hours_felt = (int) timeleftinmillisecond/3600000;
            int time_minutes_left = (int) (timeleftinmillisecond - (time_hours)*3600000)/60000;
            Toast.makeText(this,getString(R.string.message_star2).replaceAll("xx",""+time_hours_felt).replaceAll("yy",""+time_minutes_left),Toast.LENGTH_SHORT).show();
        }
        if (timeleftinmillisecond<=((5*60000)+500) && ((5*60000)-500)<=timeleftinmillisecond){
            audioPlayer.play(this,R.raw.bip_3);
            Toast.makeText(this,getString(R.string.message_star4),Toast.LENGTH_SHORT).show();
        }

        if (timeleftinmillisecond<=((10*60000)+500) && ((10*60000)-500)<=timeleftinmillisecond){
            card.setBackground(getResources().getDrawable(R.drawable.timer_background_red));
            Toast.makeText(this,getString(R.string.message_star3),Toast.LENGTH_SHORT).show();
        }
        if(timeTotal>3600000)
            progressHour.setProgress(time_hours);
        progressMinute.setProgress(minute_left);
        progressSecond.setProgress(second_left);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar
                .make(card, getString(R.string.cancel_test), Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        Paper2Activity.super.onBackPressed();
                    }
                });
        snackbar.show();
    }

    @Override
    public void onClick(View view) {

        Snackbar snackbar = Snackbar
                .make(card, getString(R.string.finish_test), Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        Intent intent = new Intent(Paper2Activity.this, Paper2CorrectionActivity.class);
                        intent.putExtra("title","Correction : "+getIntent().getStringExtra("title"));
                        startActivity(intent);
                    }
                });
        snackbar.show();

    }
}
