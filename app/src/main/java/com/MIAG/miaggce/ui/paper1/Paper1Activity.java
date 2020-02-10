package com.MIAG.miaggce.ui.paper1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.ListAdapterForQCM;
import com.MIAG.miaggce.app.AudioPlayer;
import com.MIAG.miaggce.models.Answer_test;
import com.MIAG.miaggce.models.QCM;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Paper1Activity extends AppCompatActivity implements View.OnClickListener {

    List<QCM> qcms;
    public static List<Answer_test> results;
    TextView hour, minute, second;
    ProgressBar progressHour, progressMinute, progressSecond;
    LinearLayout card;
    int paperId;
    private long timeleftinmillisecond = 0, timeTotal, timeUsedMilliSecond=0;
    private CountDownTimer countDownTimer;
    private AudioPlayer audioPlayer = new AudioPlayer();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper1);
        String htmlTitle = "<p style=\"font-size:10px\">"+getIntent().getStringExtra("title")+"</p>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        paperId = getIntent().getIntExtra("paper",1);

        getListofQCM(paperId);

        list = findViewById(R.id.listView);

        ListAdapterForQCM adapterForQCM = new ListAdapterForQCM(this,qcms,getIntent().getBooleanExtra("isCorrection",false));
        list.setAdapter(adapterForQCM);

        View footer = getLayoutInflater().inflate(R.layout.button_list_footer, null);
        list.addFooterView(footer);
        Button button = list.findViewById(R.id.finish);
        card = findViewById(R.id.card_timer);

        if(getIntent().getBooleanExtra("isCorrection",false)){
            button.setText(getString(R.string.go_back));
            card.setVisibility(View.GONE);
        }else{
            timeTotal = timeleftinmillisecond = 60000*getIntent().getIntExtra("time",12);

            card = findViewById(R.id.card_timer);
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
        button.setOnClickListener(this);
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
                showDialog(getIntent().getStringExtra("title"),getIntent().getIntExtra("paper",1));
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


    private void getListofQCM(int paperId) {
        qcms = new ArrayList<>();
        results = new ArrayList<>();
        for (int i=0; i<40; i++){
            qcms.add(new QCM(i,"Parmi la liste suivante, quel nombre n’est pas premier :","2","31","27","41",paperId,3));
            results.add(new Answer_test(5));
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
                .make(list, getString(R.string.cancel_test), Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        Paper1Activity.super.onBackPressed();
                    }
                });
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        Snackbar snackbar;
            if (getIntent().getBooleanExtra("isCorrection",false)){
                snackbar = Snackbar.make(list, getString(R.string.close_correction), Snackbar.LENGTH_LONG);
            }else {
                snackbar = Snackbar.make(list, getString(R.string.finish_test), Snackbar.LENGTH_LONG);
            }

            snackbar.setAction("Yes", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getIntent().getBooleanExtra("isCorrection",false)){
                        finish();
                    }else {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        showDialog(getIntent().getStringExtra("title"),getIntent().getIntExtra("paper",1));
                    }
                }
            });
        snackbar.show();
    }

    private  int getNote(){
        int note=0;
        for(int i=0; i<qcms.size(); i++){
            Log.e("TOUR : "+i,"");
            if (i<results.size()){
                if (qcms.get(i).getCorrect_answer()==results.get(i).getAnswer())
                    note++;
            }
        }
        return note;
    }

    private void showDialog(final String title, final int paperId){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.layout_result, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        alertD.setOnDismissListener(new DialogInterface.OnDismissListener() { //lorsque on ferme le alertDialog il démarre le countDownTimer
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                timeleftinmillisecond = timeTotal - timeUsedMilliSecond;
                startCount();
            }
        });

        alertD.setOnShowListener(new DialogInterface.OnShowListener() { //lorsque on affiche le alertDialog il arrête le countDownTimer
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
            }
        });

        Button back =  promptView.findViewById(R.id.back);
        Button result =  promptView.findViewById(R.id.result);
        Button restart =  promptView.findViewById(R.id.restart);
        TextView note =  promptView.findViewById(R.id.note);
        TextView text =  promptView.findViewById(R.id.text);
        LinearLayout card =  promptView.findViewById(R.id.card);

        if (getNote()<qcms.size()/2){
            card.setBackground(getResources().getDrawable(R.drawable.cercle_2));
        }
        note.setText(note.getText().toString().replaceAll("xx",""+getNote()).replaceAll("yy",""+qcms.size()));
        text.setText(text.getText().toString().replaceAll("xx",""+getNote()).replaceAll("yy",""+qcms.size()));

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                Paper1Activity.this.finish();
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Paper1Activity.this, Paper1Activity.class);
                i.putExtra("title","Correction : " +title);
                i.putExtra("paper",paperId);
                i.putExtra("isCorrection",true);
                startActivity(i);
                Paper1Activity.this.finish();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Paper1Activity.this, Paper1Activity.class);
                i.putExtra("title",title);
                i.putExtra("paper",paperId);
                startActivity(i);
                Paper1Activity.this.finish();
            }
        });

        alertD.setView(promptView);

        alertD.show();
    }
}
