package com.MyMentor.competitive.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.ListAdapterForQCM;
import com.MyMentor.competitive.app.AudioPlayer;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.models.ANWSER;
import com.MyMentor.competitive.models.QUESTION;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for exams sessions test and tutorials quizz
 */

public class QcmActivity extends AppCompatActivity implements View.OnClickListener{

    TextView hour, minute, second;
    private ProgressBar progressHour, progressMinute, progressSecond, progressBar;
    LinearLayout card;
    private long timeleftinmillisecond = 0, timeTotal, timeUsedMilliSecond=0;
    private CountDownTimer countDownTimer;
    private AudioPlayer audioPlayer = new AudioPlayer();
    ListView list;
    private Button button;

    private int qcmId;
    public static List<QUESTION> questions;
    public static List<List<ANWSER>> answers;
    public static List<Integer> results; //contains a student answers
    public static boolean isCorrection = false;
    private enum Dialog_Type  {DIALOG_ERROR, DIALOG_FINISH}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);


        showLoading();

        qcmId = getIntent().getIntExtra("qcmId",0);

        //setup action bar
        String title = getIntent().getStringExtra("qcmTitle");
        String htmlTitle = "<p style=\"font-size:10px\">"+ title +"</p>";
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml(htmlTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //setup view
        progressBar = findViewById(R.id.progressBar);
        list = findViewById(R.id.listView);

        @SuppressLint("InflateParams") View footer = getLayoutInflater().inflate(R.layout.button_list_footer, null);
        list.addFooterView(footer);
        button = list.findViewById(R.id.finish);
        card = findViewById(R.id.card_timer);


        button.setOnClickListener(this);

        getData();
    }

    private void getData() {
        DBManager dbManager = new DBManager(this);
        dbManager.open();

        questions = dbManager.getQuestionByQcmId(qcmId);
        if (questions.size()==0){
            hideLoadding();
            showDialogBox(Dialog_Type.DIALOG_ERROR);
        }
        else {
            for (int i =0; i<questions.size(); i++){
                List<ANWSER> answerGroup;
                answerGroup = dbManager.getAnwserByQuestionId(questions.get(i).getQUEST_ID());
                answers.add(answerGroup);
            }
        }

        dbManager.close();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isCorrection){
            button.setText(getString(R.string.go_back));
            card.setVisibility(View.GONE);
        }
        else {//set chrono
            String chrono = getIntent().getStringExtra("time");
            assert chrono != null;
            int heure = Integer.valueOf(chrono.substring(0,2));
            int minutes = Integer.valueOf(chrono.substring(3,5));
            timeTotal = timeleftinmillisecond = 60000*((heure*60)+minutes);

            //Log.e("CHRONO"+chrono,heure+":"+minutes);

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

        results = new ArrayList<>();
        ListAdapterForQCM adapterForQCM = new ListAdapterForQCM(this);
        list.setAdapter(adapterForQCM);
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
                showDialogBox(Dialog_Type.DIALOG_FINISH);
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
                .make(list, getString(R.string.cancel_test), Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        QcmActivity.super.onBackPressed();
                    }
                });
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        Snackbar snackbar;
            if (isCorrection){
                snackbar = Snackbar.make(list, getString(R.string.close_correction), Snackbar.LENGTH_LONG);
            }else {
                snackbar = Snackbar.make(list, getString(R.string.finish_test), Snackbar.LENGTH_LONG);
            }

            snackbar.setAction("Yes", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isCorrection){
                        finish();
                    }else {
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                            countDownTimer = null;
                        }
                        showDialogBox(Dialog_Type.DIALOG_FINISH);
                    }
                }
            });
        snackbar.show();
    }

    private  int calculateNote(){
        int note=0;
        for(int i=0; i<questions.size(); i++){
            if (i<results.size()){
                if (answers.get(i).get(results.get(i)).getANWS_STATE()==1)
                    note++;
            }
        }
        return note;
    }

    @SuppressLint("InflateParams")
    private void showDialogBox(Dialog_Type type){
        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView;
        if (type == Dialog_Type.DIALOG_FINISH){
            promptView = layoutInflater.inflate(R.layout.layout_result, null);
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
            final Button restart =  promptView.findViewById(R.id.restart);
            TextView note_text =  promptView.findViewById(R.id.note);
            TextView text =  promptView.findViewById(R.id.text);
            LinearLayout card =  promptView.findViewById(R.id.card);

            int note  = calculateNote();
            if (note<questions.size()/2){
                card.setBackground(getResources().getDrawable(R.drawable.cercle_2));
            }
            note_text.setText(note_text.getText().toString().replaceAll("xx",""+note).replaceAll("yy",""+questions.size()));
            text.setText(text.getText().toString().replaceAll("xx",""+note).replaceAll("yy",""+questions.size()));

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertD.dismiss();
                    if(countDownTimer!=null){
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    QcmActivity.this.finish();
                }
            });

            result.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    isCorrection = true;
                    onStart();
                }
            });

            restart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onStart();
                }
            });
        }else {
            promptView = layoutInflater.inflate(R.layout.layout_error, null);
            Button buttonClose = promptView.findViewById(R.id.close);
            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        alertD.setView(promptView);

        alertD.show();
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadding() {
        progressBar.setVisibility(View.GONE);
    }
}
