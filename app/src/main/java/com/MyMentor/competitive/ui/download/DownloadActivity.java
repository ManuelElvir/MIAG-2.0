package com.MyMentor.competitive.ui.download;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.MyMentor.competitive.MainActivity;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.app.appConfig;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.models.ANWSER_RES;
import com.MyMentor.competitive.models.CHAPTER_RES;
import com.MyMentor.competitive.models.COMPETITIVE_RES;
import com.MyMentor.competitive.models.CONTENT_RES;
import com.MyMentor.competitive.models.FILE_RES;
import com.MyMentor.competitive.models.NOTE_RES;
import com.MyMentor.competitive.models.PAST_QUESTIONS_RES;
import com.MyMentor.competitive.models.QCM_RES;
import com.MyMentor.competitive.models.QUESTION_ANWSER_RES;
import com.MyMentor.competitive.models.QUESTION_RES;
import com.MyMentor.competitive.models.REQUIEREMENT_RES;
import com.MyMentor.competitive.models.STAFFMEMBER_RES;
import com.MyMentor.competitive.models.SUBJECT_RES;
import com.MyMentor.competitive.models.TUTORIAL_QCM_RES;
import com.MyMentor.competitive.models.TUTORIAL_RES;
import com.MyMentor.competitive.ui.identification.IdentificationActivity;

import static com.MyMentor.competitive.app.appConfig.ID;
import static com.MyMentor.competitive.app.appConfig.PREFERENCE;
import static com.MyMentor.competitive.app.appConfig.USERKEY;

public class DownloadActivity extends AppCompatActivity implements DownloaderInterface {

    DownloaderPresenter downloaderPresenter;
    DBManager dbManager;
    int errorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREFERENCE, 0);
        String userKey = pref.getString(USERKEY, appConfig.DEFAULT_KEY);

        int userID = pref.getInt(ID,0);
        if (userID!=0){
            goToIdentificationActivity();
        }
        else{
            downloaderPresenter = new DownloaderPresenter(userKey,this);
            dbManager = new DBManager(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        errorCount = 0;
        if (appConfig.isNetworkConnected(this))
            downloaderPresenter.downloadCompetitives(dbManager.getLastCompId());
        else
            internetErrror();
    }

    @Override
    public void onReceiveCompetitive(COMPETITIVE_RES competitiveRes) {
        if (competitiveRes.isSuccess() && competitiveRes.getCompetitives()!=null){
            int size = competitiveRes.getCompetitives().size();
            if (size>0){
                dbManager.insertListCompetitive(competitiveRes.getCompetitives());
                int lastIdReceive =competitiveRes.getCompetitives().get(size-1).getCOMP_ID();
                if (competitiveRes.getLast()<lastIdReceive){
                    Log.d("COMPETITIVE", "download from "+lastIdReceive);
                    downloaderPresenter.downloadCompetitives(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadSubjects(dbManager.getLastSubjectId());
            }
            else{
                onErrorLoadind("SIZE competitive nul");
                downloaderPresenter.downloadSubjects(dbManager.getLastSubjectId());
            }
        }
        else {
            onErrorLoadind("Error competitive");
            downloaderPresenter.downloadSubjects(dbManager.getLastSubjectId());
        }
    }

    @Override
    public void onReceiveSubject(SUBJECT_RES subjects) {
        if (subjects.isSuccess() && subjects.getSubjects()!=null){
            int size = subjects.getSubjects().size();
            if (size>0){
                dbManager.insertListSubject(subjects.getSubjects());
                int lastIdReceive = subjects.getSubjects().get(size-1).getSJ_ID();
                if (subjects.getLast()<lastIdReceive){
                    Log.d("SUBJECT", "download from "+lastIdReceive);
                    downloaderPresenter.downloadSubjects(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadChapters(dbManager.getLastChapterId());
            }
            else{
                onErrorLoadind("SIZE subject nul");
                downloaderPresenter.downloadChapters(dbManager.getLastChapterId());
            }
        }
        else {
            onErrorLoadind("Error subject");
            downloaderPresenter.downloadChapters(dbManager.getLastChapterId());
        }
    }

    @Override
    public void onReceiveChapter(CHAPTER_RES chapters) {
        if (chapters.isSuccess() && chapters.getChapters()!=null){
            int size = chapters.getChapters().size();
            if (size>0){
                dbManager.insertListChapter(chapters.getChapters());
                int lastIdReceive = chapters.getChapters().get(size-1).getSJ_ID();
                if (chapters.getLast()<lastIdReceive){
                    Log.d("CHAPTER", "download from "+lastIdReceive);
                    downloaderPresenter.downloadChapters(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadTutorials(dbManager.getLastTutorialId());
            }
            else{
                onErrorLoadind("SIZE chapter nul");
                downloaderPresenter.downloadTutorials(dbManager.getLastTutorialId());
            }
        }
        else {
            onErrorLoadind("Error chapter");
            downloaderPresenter.downloadTutorials(dbManager.getLastTutorialId());
        }
    }

    @Override
    public void onReceiveTutorial(TUTORIAL_RES tutorials) {
        if (tutorials.isSuccess() && tutorials.getTutorials()!=null){
            int size = tutorials.getTutorials().size();
            if (size>0){
                dbManager.insertListTutorial(tutorials.getTutorials());
                int lastIdReceive = tutorials.getTutorials().get(size-1).getTUTO_ID();
                if (tutorials.getLast()<lastIdReceive){
                    Log.d("TUTORIAL", "download from "+lastIdReceive);
                    downloaderPresenter.downloadTutorials(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadNotes(dbManager.getLastNoteId());
            }
            else{
                onErrorLoadind("SIZE tutorial nul");
                downloaderPresenter.downloadNotes(dbManager.getLastNoteId());
            }
        }
        else {
            onErrorLoadind("Error tutorial");
            downloaderPresenter.downloadNotes(dbManager.getLastNoteId());
        }
    }

    @Override
    public void onReceiveNote(NOTE_RES notes) {
        if (notes.isSuccess() && notes.getNotes()!=null){
            int size = notes.getNotes().size();
            if (size>0){
                dbManager.insertListNote(notes.getNotes());
                int lastIdReceive = notes.getNotes().get(size-1).getNOTE_ID();
                if (notes.getLast()<lastIdReceive){
                    Log.d("NOTE", "download from "+lastIdReceive);
                    downloaderPresenter.downloadNotes(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadPastQuestions(dbManager.getLastPastQuestionId());
            }
            else{
                onErrorLoadind("SIZE note nul");
                downloaderPresenter.downloadPastQuestions(dbManager.getLastPastQuestionId());
            }
        }
        else {
            onErrorLoadind("Error note");
            downloaderPresenter.downloadPastQuestions(dbManager.getLastPastQuestionId());
        }
    }

    @Override
    public void onReceivePastQuestion(PAST_QUESTIONS_RES past_questions) {
        if (past_questions.isSuccess() && past_questions.getPast_questions()!=null){
            int size = past_questions.getPast_questions().size();
            if (size>0){
                dbManager.insertListPastQuestion(past_questions.getPast_questions());
                int lastIdReceive = past_questions.getPast_questions().get(size-1).getPQ_ID();
                if (past_questions.getLast()<lastIdReceive){
                    Log.d("PAST_QUESTIONS", "download from "+lastIdReceive);
                    downloaderPresenter.downloadPastQuestions(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadQcms(dbManager.getLastQcmId());
            }
            else{
                onErrorLoadind("SIZE past_question nul");
                downloaderPresenter.downloadQcms(dbManager.getLastQcmId());
            }
        }
        else {
            onErrorLoadind("Error past_question");
            downloaderPresenter.downloadQcms(dbManager.getLastQcmId());
        }
    }

    @Override
    public void onReceiveQCM(QCM_RES qcms) {
        if (qcms.isSuccess() && qcms.getQcms()!=null){
            int size = qcms.getQcms().size();
            if (size>0){
                dbManager.insertListQcm(qcms.getQcms());
                int lastIdReceive = qcms.getQcms().get(size-1).getQCM_ID();
                if (qcms.getLast()<lastIdReceive){
                    Log.d("QCM", "download from "+lastIdReceive);
                    downloaderPresenter.downloadQcms(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadTutorialsQcm(dbManager.getLastTutorialQcmId());
            }
            else{
                onErrorLoadind("SIZE qcm nul");
                downloaderPresenter.downloadTutorialsQcm(dbManager.getLastTutorialQcmId());
            }
        }
        else {
            onErrorLoadind("Error qcm");
            downloaderPresenter.downloadTutorialsQcm(dbManager.getLastTutorialQcmId());
        }
    }

    @Override
    public void onReceiveTutorialQcm(TUTORIAL_QCM_RES tutorial_qcms) {
        if (tutorial_qcms.isSuccess() && tutorial_qcms.getTutorial_qcms()!=null){
            int size = tutorial_qcms.getTutorial_qcms().size();
            if (size>0){
                dbManager.insertListTutorialQcm(tutorial_qcms.getTutorial_qcms());
                int lastIdReceive = tutorial_qcms.getTutorial_qcms().get(size-1).getTUTO_ID();
                if (tutorial_qcms.getLast()<lastIdReceive){
                    Log.d("TUTORIAL_QCM", "download from "+lastIdReceive);
                    downloaderPresenter.downloadTutorialsQcm(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadContents(dbManager.getLastContentId());
            }
            else{
                onErrorLoadind("SIZE tutorial_qcm nul");
                downloaderPresenter.downloadContents(dbManager.getLastContentId());
            }
        }
        else {
            onErrorLoadind("Error tutorial_qcm");
            downloaderPresenter.downloadContents(dbManager.getLastContentId());
        }
    }

    @Override
    public void onReceiveContent(CONTENT_RES contents) {
        if (contents.isSuccess() && contents.getContents()!=null){
            int size = contents.getContents().size();
            if (size>0){
                dbManager.insertListContent(contents.getContents());
                int lastIdReceive = contents.getContents().get(size-1).getQCM_ID();
                if (contents.getLast()<lastIdReceive){
                    Log.d("CONTENT", "download from "+lastIdReceive);
                    downloaderPresenter.downloadContents(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadQuestions(dbManager.getLastQuestionId());
            }
            else{
                onErrorLoadind("SIZE content nul");
                downloaderPresenter.downloadQuestions(dbManager.getLastQuestionId());
            }
        }
        else {
            onErrorLoadind("Error content");
            downloaderPresenter.downloadQuestions(dbManager.getLastQuestionId());
        }
    }

    @Override
    public void onReceiveQuestion(QUESTION_RES questions) {
        if (questions.isSuccess() && questions.getQuestions()!=null){
            int size = questions.getQuestions().size();
            if (size>0){
                dbManager.insertListQuestion(questions.getQuestions());
                int lastIdReceive = questions.getQuestions().get(size-1).getQUEST_ID();
                if (questions.getLast()<lastIdReceive){
                    Log.d("QUESTION", "download from "+lastIdReceive);
                    downloaderPresenter.downloadQuestions(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadQuestionAnswers(dbManager.getLastQuestionAnswerId());
            }
            else{
                onErrorLoadind("SIZE question nul");
                downloaderPresenter.downloadQuestionAnswers(dbManager.getLastQuestionAnswerId());
            }
        }
        else {
            onErrorLoadind("Error question");
            downloaderPresenter.downloadQuestionAnswers(dbManager.getLastQuestionAnswerId());
        }
    }

    @Override
    public void onReceiveQuestionAnswer(QUESTION_ANWSER_RES question_anwsers) {
        if (question_anwsers.isSuccess() && question_anwsers.getQuestion_anwsers()!=null){
            int size = question_anwsers.getQuestion_anwsers().size();
            if (size>0){
                dbManager.insertListQuestionAnswer(question_anwsers.getQuestion_anwsers());
                int lastIdReceive = question_anwsers.getQuestion_anwsers().get(size-1).getQUEST_ID();
                if (question_anwsers.getLast()<lastIdReceive){
                    Log.d("QUESTION_ANWSER", "download from "+lastIdReceive);
                    downloaderPresenter.downloadQuestionAnswers(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadAnswers(dbManager.getLastAnswerId());
            }
            else{
                onErrorLoadind("SIZE question_answer nul");
                downloaderPresenter.downloadAnswers(dbManager.getLastAnswerId());
            }
        }
        else {
            onErrorLoadind("Error question_answer");
            downloaderPresenter.downloadAnswers(dbManager.getLastAnswerId());
        }
    }

    @Override
    public void onReceiveAnswer(ANWSER_RES anwsers) {
        if (anwsers.isSuccess() && anwsers.getAnwsers()!=null){
            int size = anwsers.getAnwsers().size();
            if (size>0){
                dbManager.insertListAnwser(anwsers.getAnwsers());
                int lastIdReceive = anwsers.getAnwsers().get(size-1).getANWS_ID();
                if (anwsers.getLast()<lastIdReceive){
                    Log.d("QUESTION_ANWSER", "download from "+lastIdReceive);
                    downloaderPresenter.downloadAnswers(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadRequierements(dbManager.getLastReqId());
            }
            else{
                onErrorLoadind("SIZE question_answer nul");
                downloaderPresenter.downloadRequierements(dbManager.getLastReqId());
            }
        }
        else {
            onErrorLoadind("Error question_answer");
            downloaderPresenter.downloadRequierements(dbManager.getLastReqId());
        }
    }

    @Override
    public void onReceiveRequierement(REQUIEREMENT_RES requierements) {
        if (requierements.isSuccess() && requierements.getRequierements()!=null){
            int size = requierements.getRequierements().size();
            if (size>0){
                dbManager.insertListRequierement(requierements.getRequierements());
                int lastIdReceive = requierements.getRequierements().get(size-1).getREQ_ID();
                if (requierements.getLast()<lastIdReceive){
                    Log.d("REQUIEREMENT", "download from "+lastIdReceive);
                    downloaderPresenter.downloadRequierements(lastIdReceive);
                }
                else
                    downloaderPresenter.downloadStaffMembers();
            }
            else{
                onErrorLoadind("SIZE requierement nul");
                downloaderPresenter.downloadStaffMembers();
            }
        }
        else {
            onErrorLoadind("Error requierement");
            downloaderPresenter.downloadStaffMembers();
        }
    }

    @Override
    public void onReceiveStaffMember(STAFFMEMBER_RES staffmembers) {
        if (staffmembers.isSuccess() && staffmembers.getStaffmembers()!=null){
            int size = staffmembers.getStaffmembers().size();
            if (size>0){
                dbManager.insertListStaffMember(staffmembers.getStaffmembers());
                downloaderPresenter.downloadFiles(dbManager.getLastFileId());
            }
            else{
                onErrorLoadind("SIZE staff_member nul");
                downloaderPresenter.downloadFiles(dbManager.getLastFileId());
            }
        }
        else {
            onErrorLoadind("Error staff_member");
            downloaderPresenter.downloadFiles(dbManager.getLastFileId());
        }
    }

    @Override
    public void onReceiveImages(FILE_RES files) {
        if (files.isSuccess() && files.getFiles()!=null){
            int size = files.getFiles().size();
            if (size>0){
                dbManager.insertListFile(files.getFiles());
                onFinishDownload();
            }
            else{
                onErrorLoadind("SIZE staff_member nul");
                onFinishDownload();
            }
        }
        else {
            onErrorLoadind("Error staff_member");
            onFinishDownload();
        }
    }

    @Override
    public void onErrorLoadind(String cause) {
        errorCount++;
        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishDownload() {
        if (errorCount==0){
            Toast.makeText(this, R.string.end_update_data, Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
        else{
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_download_finish, null);
            final AlertDialog builder1 = new AlertDialog.Builder(this).create();
            builder1.setView(promptView);
            Button start = promptView.findViewById(R.id.start);
            Button retry = promptView.findViewById(R.id.cancel);
            TextView text =  promptView.findViewById(R.id.text);

            text.setText(getString(R.string.downlaod_finish_with_error).replace("xx",""+errorCount));

            builder1.setCancelable(false);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToMainActivity();
                }
            });
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStart();
                }
            });
            builder1.show();
        }
    }

    private void internetErrror() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_error, null);
        final AlertDialog builder1 = new AlertDialog.Builder(this).create();
        builder1.setView(promptView);
        TextView text =  promptView.findViewById(R.id.text);
        Button retry = promptView.findViewById(R.id.close);

        text.setText(R.string.no_internet);

        builder1.setCancelable(false);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
            }
        });
        builder1.show();
    }

    void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void goToIdentificationActivity(){
        Intent intent = new Intent(this, IdentificationActivity.class);
        startActivity(intent);
        finish();
    }
}
