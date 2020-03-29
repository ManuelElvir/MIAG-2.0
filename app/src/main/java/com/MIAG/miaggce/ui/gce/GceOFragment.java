package com.MIAG.miaggce.ui.gce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForGCE;
import com.MIAG.miaggce.app.AsyncTaskRunner;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;
import com.MIAG.miaggce.ui.paper2.Paper2Activity;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class GceOFragment extends Fragment  implements GceView {

    private List<String> years;
    private SharedPreferences mPrefs;
    private ProgressBar progressBar;
    private GridView gridView;
    private GcePresenter presenter;
    private List<SUBJECT> subjects_list= new ArrayList<>();
    private DBManager dbManager;
    private int position;
    private boolean isManuallyStart = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gce_o, container, false);
        gridView = root.findViewById(R.id.gridView);
        progressBar = root.findViewById(R.id.progressBar);
        mPrefs = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCE,0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showSubMenu(i, view);
            }
        });

        getData();

        return root;
    }

    private void getData() {
        presenter = new GcePresenter(this, MainActivity.userKey);
        dbManager = new DBManager(getActivity());
        dbManager.open();

        subjects_list = dbManager.fetchSubject();
        years = dbManager.listExamDateByLevel("o");
        refreshContent();

        AsyncTaskRunner.AsyncTaskListener asyncTaskListener = new AsyncTaskRunner.AsyncTaskListener() {
            @Override
            public void startDownload() {
                presenter.getPaper1(subjects_list.get(position).getSJ_ID());
            }
        };
        if (subjects_list.size()>0){
            position = 0;
            AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(asyncTaskListener);
            asyncTaskRunner.execute("Update Data...");
        }
    }

    private void refreshContent() {
        GridAdapterForGCE adapter = new GridAdapterForGCE(this.getContext(), subjects_list);
        gridView.setAdapter(adapter);
    }


    private void showSubMenu(final int i, final View view) {

        PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
        for (int j=0; j<years.size(); j++){
            int MENU_ITEM_ID = Menu.FIRST+j;
            SubMenu menu = popupMenu.getMenu().addSubMenu(j,MENU_ITEM_ID,j,years.get(j));
            menu.add(j,MENU_ITEM_ID+years.size()+1,0,getText(R.string.paper_1));
            menu.add(j,MENU_ITEM_ID+years.size()+2,1,getText(R.string.paper_2));
            menu.add(j,MENU_ITEM_ID+years.size()+3,2,getText(R.string.paper_3));
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int subject = subjects_list.get(i).getSJ_ID();
                int exam = dbManager.getExamByNameAndDate("o",years.get(item.getGroupId()));
                if(item.getTitle() == getText(R.string.paper_1)){
                    final PAPER1 paper1 = dbManager.getPaper1BySubjectAndExam(subject,exam);
                    if(paper1.getPAPER1_ID()==0){
                        onErrorLoadind(getString(R.string.paper_not_exist));
                        //on relance le téléchargement de ce paper
                        presenter.getPaper1(subject);
                        isManuallyStart=true;
                    }else {
                        if(dbManager.getQuestionCountForPaper1(paper1.getPAPER1_ID())>0)
                            showDialog(paper1.getTEST_NAME(),PAPER.PAPER1,paper1.getPAPER1_ID());
                        else{
                            Snackbar snackbar = Snackbar
                                    .make(progressBar, "The Question are not Downloaded", Snackbar.LENGTH_LONG)
                                    .setAction("Download", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            presenter.getQuestions(paper1.getPAPER1_ID());
                                        }
                                    });
                            snackbar.show();
                        }
                    }
                }
                else if(item.getTitle() == getText(R.string.paper_2)){
                    PAPER2 paper2 = dbManager.getPaper2BySubjectAndExam(subject,exam);
                    if(paper2.getPAPER2_ID()==0){
                        onErrorLoadind(getString(R.string.paper_not_exist));
                        //on relance le téléchargement de ce paper
                        presenter.getPaper2(subject);
                        isManuallyStart =true;
                    }else {
                        showDialog(paper2.getTEST_NAME(),PAPER.PAPER2,paper2.getPAPER2_ID());
                    }
                }
                else if(item.getTitle() == getText(R.string.paper_3)){
                    PAPER3 paper3 = dbManager.getPaper3BySubjectAndExam(subject,exam);
                    if(paper3.getPAPER3_ID()==0){
                        onErrorLoadind(getString(R.string.paper_not_exist));
                        //on relance le téléchargement de ce paper
                        presenter.getPaper3(subject);
                        isManuallyStart =true;
                    }else {
                        showDialog(paper3.getTEST_NAME(),PAPER.PAPER3,paper3.getPAPER3_ID());
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }



    private void showDialog(final String title, final PAPER paper, final int paperId){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        if (mPrefs.getBoolean(ENABLE, false)) { //get Boolean
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_start_exam, null);

            final AlertDialog builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
            builder1.setView(promptView);
            TextView text_title = promptView.findViewById(R.id.title);
            text_title.setText(title);
            Button start = promptView.findViewById(R.id.start);
            Button cancel = promptView.findViewById(R.id.cancel);
            Button back =  promptView.findViewById(R.id.back);

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    builder1.dismiss();
                }
            });

            builder1.setCancelable(true);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i;
                    switch (paper){
                        case PAPER1:
                            i = new Intent(getActivity(), Paper1Activity.class);
                            break;

                        case PAPER2:
                            i = new Intent(getActivity(), Paper2Activity.class);
                            break;

                        default: //Default is Paper3Type
                            i = new Intent(getActivity(), Paper2Activity.class);
                            i.putExtra("no_timer",true);
                            break;
                    }
                    i.putExtra("title",title);
                    i.putExtra("paper",paperId);
                    startActivity(i);
                    builder1.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder1.cancel();
                }
            });
            builder1.show();
        }
        else {
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_not_register, null);
            final AlertDialog builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
            builder1.setView(promptView);
            Button back =  promptView.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    builder1.dismiss();
                }
            });
            builder1.show();
        }
    }

    @Override
    public void showLoading() {
        Objects.requireNonNull(progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void HideLoadding() {
        Objects.requireNonNull(progressBar).setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoadind(String cause) {
        if (GceOFragment.this.getContext()!=null)
            Toast.makeText(GceOFragment.this.getContext(), cause, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceivePaper1(List<PAPER1> paper1s) {
        dbManager.insertListPaper1(paper1s);
        if (isManuallyStart)// si le téléchargement a été démarer manuellement
        {
            starGetQuestion(paper1s);
            isManuallyStart = false;
        }
        else
            presenter.getPaper2(subjects_list.get(position).getSJ_ID());
    }

    @Override
    public void onReceivePaper2(List<PAPER2> paper2s) {
        dbManager.insertListPaper2(paper2s);
        for (int i=0; i<paper2s.size(); i++){
            presenter.getPaper2Correction(paper2s.get(i).getPAPER2_ID());
        }
        if (isManuallyStart)
            isManuallyStart = false;
        else
            presenter.getPaper3(subjects_list.get(position).getSJ_ID());
    }

    @Override
    public void onReceivePaper2Correction(SUBJECT_CORRECTION correction){
        dbManager.insertListSubjectCorrection(correction);
    }

    @Override
    public void onReceivePaper3(List<PAPER3> paper3s) {
        dbManager.insertListPaper3(paper3s);
        for (int i=0; i<paper3s.size(); i++){
            presenter.getPaper3Correction(paper3s.get(i).getPAPER3_ID());
        }

        if (isManuallyStart)
            isManuallyStart = false;
        else{
            if (position<subjects_list.size()-1){
                position++;
                presenter.getPaper1(subjects_list.get(position).getSJ_ID());
            }else {
                starGetQuestion(null);
            }
        }
    }

    @Override
    public void onReceivePaper3Correction(SUBJECT_CORRECTION correction){
        dbManager.insertListSubjectCorrection(correction);
    }

    @Override
    public void onReceiveQuestion(List<QUESTION> questions, int paperId) {
        dbManager.insertListQuestion(questions, paperId,0);
        for (int i=0; i<questions.size(); i++){
            presenter.getAnswers(questions.get(i).getQUEST_ID());
            if (i == questions.size()-1)
                Toast.makeText(getContext(), getString(R.string.question_download), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReceiveAnwser(List<ANWSER> anwsers, int questId) {
        dbManager.insertListAnwser(anwsers, questId);
    }

    private void starGetQuestion(List<PAPER1> paper1_list) {
        if (paper1_list!=null)
            for (int i =0; i<paper1_list.size(); i++){
                presenter.getQuestions(paper1_list.get(i).getPAPER1_ID());
            }
        else{
            List<PAPER1> paper1s = dbManager.fetchPaper1();
            for (int i =0; i<paper1s.size(); i++){
                presenter.getQuestions(paper1s.get(i).getPAPER1_ID());
            }
        }
    }
}
