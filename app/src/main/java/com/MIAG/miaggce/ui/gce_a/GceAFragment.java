package com.MIAG.miaggce.ui.gce_a;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForGCE;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.ui.paper2.Paper2Activity;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class GceAFragment extends Fragment implements GceView{

    private List<String> subjects;
    private List<String> years;
    private SharedPreferences mPrefs;
    private ProgressBar progressBar;
    private GridView gridView;
    private GcePresenter presenter;
    private List<SUBJECT> subjects_list;
    private DBManager dbManager;
    private int position, paperChrono;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPrefs = getContext().getSharedPreferences(PREFERENCE,0);

        View root = inflater.inflate(R.layout.fragment_gce_a, container, false);
        gridView = root.findViewById(R.id.gridView);
        progressBar = root.findViewById(R.id.progressBar);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showSubMenu(i, view);
            }
        });

        refreshContent();
        getData();

        return root;
    }

    private void getData() {
        presenter = new GcePresenter(this, MainActivity.userKey);
        presenter.getSubject(1);
        dbManager = new DBManager(getActivity());
        dbManager.open();
    }

    private void refreshContent() {
        subjects = new ArrayList<>();
        subjects.add(subjects_list.get(0).getSJ_NAME());
        for (int i=1; i<subjects_list.size(); i++){
            int j = 0;
            boolean alReadyUsed = false;
            do{
                if (subjects_list.get(i).getSJ_NAME().equals(subjects_list.get(j).getSJ_NAME())){
                    alReadyUsed =true;
                }
                j++;
            }while (!alReadyUsed && j<i+1);
            if (!alReadyUsed){
                subjects.add(subjects_list.get(0).getSJ_NAME());
            }
        }

        //replace to dynamics
        if (subjects.size()==0){
            subjects.add("Mathematics");
            subjects.add("Physical");
            subjects.add("Chemistry");
            subjects.add("Biology");
            subjects.add("Economy");
            subjects.add("History");
            subjects.add("Geography");
            subjects.add("French");
            subjects.add("English");
            subjects.add("Biologie");
            subjects.add("Others");
        }

        years = new ArrayList<>();
        years.add("2019");
        years.add("2018");
        years.add("2017");
        years.add("2016");
        years.add("2015");
        years.add("2014");
        years.add("2013");
        years.add("2012");
        years.add("2011");
        years.add("2010");
        years.add("2009");

        GridAdapterForGCE adapter = new GridAdapterForGCE(this.getContext(), subjects);
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
                int subject = getSubjectIdFromNameAndyear(years.get(item.getGroupId()), years.get(item.getGroupId()));
                if (subject>0){
                    if(item.getTitle() == getText(R.string.paper_1))
                        showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i),PAPER.PAPER1,subject);
                    else if(item.getTitle() == getText(R.string.paper_2))
                        showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i),PAPER.PAPER2,subject);
                    else if(item.getTitle() == getText(R.string.paper_3))
                        showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i),PAPER.PAPER3,subject);
                }
                return false;
            }
        });
        popupMenu.show();
    }


    private int getSubjectIdFromNameAndyear(String sj_name, String sj_date) {
        List<SUBJECT> subject_choice = dbManager.getSubjectbyExamId(0,sj_name,sj_date);
        if (subject_choice==null){
            onErrorLoadind("This subject is not download, please connect your device to internet");
            return 0;
        }else if (subject_choice.size()==0){
            onErrorLoadind("This subject is not download, please connect your device to internet");
            return 0;
        }else{
            return subject_choice.get(0).getSJ_ID();
        }
    }

    private void showDialog(final String title, final PAPER paper, final int subjectId){

        int paperId = 0;
        if (paper == PAPER.PAPER1){
            List<PAPER1> paper1s = dbManager.getPaper1BySubjectId(subjectId);
            if (paper1s!=null)
                if (paper1s.size()>0){
                    paperId = paper1s.get(0).getPAPER1_ID();
                    paperChrono = paper1s.get(0).getTEST_CHRONO();
                }
        }
        if (paper == PAPER.PAPER2){
            List<PAPER2> paper2s = dbManager.getPaper2BySubjectId(subjectId);
            if (paper2s!=null)
                if (paper2s.size()>0)
                    paperId = paper2s.get(0).getPAPER2_ID();
        }
        if (paper == PAPER.PAPER3){
            List<PAPER3> paper3s = dbManager.getPaper3BySubjectId(subjectId);
            if (paper3s!=null)
                if (paper3s.size()>0)
                    paperId = paper3s.get(0).getPAPER3_ID();
        }
        if (paperId>0){
            final int paper_id = paperId;
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (mPrefs.getBoolean(ENABLE, false)){ //get Boolean
                @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_start_exam, null);

                final AlertDialog builder1 = new AlertDialog.Builder(getContext()).create();
                builder1.setView(promptView);
                TextView text_title = promptView.findViewById(R.id.title);
                TextView text_indication = promptView.findViewById(R.id.text);
                text_title.setText(title);
                if (paper==PAPER.PAPER3)
                    text_indication.setText(R.string.start_paper3_message);
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
                                i.putExtra("time",paperChrono);
                                break;

                            case PAPER2:
                                i = new Intent(getActivity(), Paper2Activity.class);
                                i.putExtra("subject",subjectId);
                                break;

                            default: //Default is Paper3Type
                                i = new Intent(getActivity(), Paper2Activity.class);
                                i.putExtra("no_timer",true);
                                i.putExtra("subject",subjectId);
                                break;
                        }
                        i.putExtra("title",title);
                        i.putExtra("paper",paper_id);
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
            }else{
                @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_not_register, null);
                final AlertDialog builder1 = new AlertDialog.Builder(getContext()).create();
                builder1.setView(promptView);
                Button back =  promptView.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        builder1.dismiss();
                    }
                });
                builder1.show();
            }
        }else {
            onErrorLoadind("this paper is not download, please connect your device to internet and try again.");
        }


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
    }

    @Override
    public void onReceiveSubject(List<SUBJECT> subjects) {
        dbManager.insertListSubject(subjects);
        subjects_list = dbManager.fetchSubject();
        if (subjects_list!=null){
            if (subjects_list.size()>0){
                refreshContent();
                position = 0;
                presenter.getPaper1(subjects_list.get(position).getSJ_ID());
            }
        }

    }

    @Override
    public void onReceivePaper1(List<PAPER1> paper1s) {
        dbManager.insertListPaper1(paper1s);
        presenter.getPaper2(subjects_list.get(position).getSJ_ID());
    }

    @Override
    public void onReceivePaper2(List<PAPER2> paper2s) {
        dbManager.insertListPaper2(paper2s);
        presenter.getPaper3(subjects_list.get(position).getSJ_ID());
    }

    @Override
    public void onReceivePaper3(List<PAPER3> paper3s) {
        dbManager.insertListPaper3(paper3s);
        if (position<subjects_list.size()-1){
            position++;
            presenter.getPaper1(subjects_list.get(position).getSJ_ID());
        }else {
            starGetQuestion();
        }
    }

    @Override
    public void onReceiveQuestion(List<QUESTION> questions) {
        dbManager.insertListQuestion(questions);
        for (int i=0; i<questions.size(); i++){
            presenter.getAnswers(questions.get(i).getQUEST_ID());
        }
    }

    @Override
    public void onReceiveAnwser(List<ANWSER> anwsers) {
        dbManager.insertListAnwser(anwsers);
    }

    private void starGetQuestion() {
        for (int i =0; i<subjects_list.size(); i++){
            List<PAPER1> paper1s = dbManager.getPaper1BySubjectId(subjects_list.get(i).getSJ_ID());
            if (paper1s !=null){
                if (paper1s.size()>0){
                    presenter.getQuestions(paper1s.get(0).getPAPER1_ID());
                }
            }
        }
    }


}

