package com.MIAG.miaggce.ui.paper2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.app.AudioPlayer;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;
import com.MIAG.miaggce.ui.gce_a.GcePresenter;
import com.MIAG.miaggce.ui.gce_a.GceView;
import com.MIAG.miaggce.ui.paper2_correction.Paper2CorrectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class Paper2Activity extends AppCompatActivity implements View.OnClickListener, GceView {

    TextView hour, minute, second;
    ProgressBar progressHour, progressMinute, progressSecond, progressBar;
    LinearLayout card;
    WebView webView;
    int paperId, paperTime;
    private long timeleftinmillisecond, timeTotal;
    private CountDownTimer countDownTimer;
    private AudioPlayer audioPlayer = new AudioPlayer();
    String message = "";
    private DBManager dbManager;
    private GcePresenter presenter;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper2);

        String htmlTitle = "<span style=\"font-size:12px\">"+getIntent().getStringExtra("title")+"</span>";
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        paperId = getIntent().getIntExtra("paper",0);


        Button correction = findViewById(R.id.correction);
        correction.setOnClickListener(this);
        card = findViewById(R.id.card_timer);

        getDataToDataBase();
    }

    private void getDataToDataBase() {
        showLoading();
        dbManager = new DBManager(this);
        dbManager.open();
        if (getIntent().getBooleanExtra("no_timer",false)){
            PAPER3 paper3 = dbManager.getPaper3ById(paperId);
            message = paper3.getTEST_CONTENT();
        }else {
            PAPER2 paper2 = dbManager.getPaper2ById(paperId);
            message = paper2.getTEST_CONTENT();
            int heure = Integer.valueOf(paper2.getTEST_CHRONO().substring(0,2));
            int minutes = Integer.valueOf(paper2.getTEST_CHRONO().substring(3,5));
            paperTime = (heure*60)+minutes;
        }
        HideLoadding();

        refreshContent();
    }

    private void refreshContent() {
        webView.loadDataWithBaseURL(null,message,"text/html","utf-8",null);

        if (getIntent().getBooleanExtra("no_timer",false)){
            card.setVisibility(View.GONE);
        }
        else {
            timeTotal= timeleftinmillisecond = paperTime*60000;

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

    private void startCount() {
        countDownTimer = new CountDownTimer(timeleftinmillisecond, 1000) {
            @Override
            public void onTick(long l) {
                timeleftinmillisecond = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Log.e("TIMES FINISH","");
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
                        SUBJECT_CORRECTION corrections;
                        if (getIntent().getBooleanExtra("no_timer",false)){
                            corrections = dbManager.getSubjectCorrectionByPaper3Id(paperId);
                        }else {
                            corrections = dbManager.getSubjectCorrectionByPaper2Id(paperId);
                        }

                        if (!corrections.equals(new SUBJECT_CORRECTION())){
                            Intent intent = new Intent(Paper2Activity.this, Paper2CorrectionActivity.class);
                            intent.putExtra("title","Correction : "+getIntent().getStringExtra("title"));
                            intent.putExtra("paper",paperId);
                            startActivity(intent);
                        }
                        else {
                            if (appConfig.isInternetAvailable()){
                                presenter = new GcePresenter(Paper2Activity.this, MainActivity.userKey);
                                if (getIntent().getBooleanExtra("no_timer",false)){
                                    presenter.getPaper3Correction(paperId);
                                }else {
                                    presenter.getPaper2Correction(paperId);
                                }
                            }else
                                onErrorLoadind("The correction is not download, please connect your device to internet");
                        }
                    }
                });
        snackbar.show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideLoadding() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onErrorLoadind(String cause) {
        Snackbar.make(progressBar,cause,Snackbar.LENGTH_SHORT).show();
        getDataToDataBase();
    }

    @Override
    public void onReceivePaper1(List<PAPER1> paper1s) {

    }

    @Override
    public void onReceivePaper2(List<PAPER2> paper2s) {

    }

    @Override
    public void onReceivePaper3(List<PAPER3> paper3s) {

    }

    @Override
    public void onReceiveQuestion(List<QUESTION> questions, int paperId) {

    }

    @Override
    public void onReceivePaper2Correction(SUBJECT_CORRECTION correction) {
        dbManager.insertListSubjectCorrection(correction);
        Intent intent = new Intent(Paper2Activity.this, Paper2CorrectionActivity.class);
        intent.putExtra("title","Correction : "+getIntent().getStringExtra("title"));
        intent.putExtra("sc_id",paperId);
        startActivity(intent);
    }

    @Override
    public void onReceivePaper3Correction(SUBJECT_CORRECTION correction) {
        dbManager.insertListSubjectCorrection(correction);
        Intent intent = new Intent(Paper2Activity.this, Paper2CorrectionActivity.class);
        intent.putExtra("title","Correction : "+getIntent().getStringExtra("title"));
        intent.putExtra("isPaper3",true);
        intent.putExtra("sc_id",paperId);
        startActivity(intent);
    }

    @Override
    public void onReceiveAnwser(List<ANWSER> anwsers, int questId) {

    }
}
