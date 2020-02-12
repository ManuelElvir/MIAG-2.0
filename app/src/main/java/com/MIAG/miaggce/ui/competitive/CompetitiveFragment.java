package com.MIAG.miaggce.ui.competitive;

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
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.REQUIEREMENT;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.MIAG.miaggce.ui.requierement.RequierementActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class CompetitiveFragment extends Fragment implements CompetitiveView {
    private GridView gridView;
    private List<String> school;
    private List<String> subjects;
    private List<String> chapter;
    private List<CHAPTER> chapters;
    private SharedPreferences mPrefs;
    private ProgressBar progressBar;
    private CompetitivePresenter competitivePresenter;
    private List<COMPETITIVE> competitives;
    private DBManager dbManager;
    private int position;

    private static final int MENU1 = 0;
    private static final int MENU2 = 1;

    private static final int MENU_1_ITEM = Menu.FIRST;
    private static final int MENU_2_ITEM = Menu.FIRST + 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_competitive, container, false);

        gridView = root.findViewById(R.id.gridView);
        mPrefs = getContext().getSharedPreferences(PREFERENCE,0);

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
        competitivePresenter = new CompetitivePresenter(this, MainActivity.userKey);
        competitivePresenter.getCompetitive();
        dbManager = new DBManager(getActivity());
        dbManager.open();
    }

    private void refreshContent() {

        school = new ArrayList<>();
        for (int i=0; i<competitives.size(); i++){
            school.add(competitives.get(i).getCOMP_NAME());
        }

        chapter = new ArrayList<>();
        for (int i=0; i<chapter.size(); i++){
            chapter.add(chapters.get(i).getCHAP_NAME());
        }

        //replace to dynamics
        if (school.size() == 0){
            school.add("ENS Maroua");
            school.add("Polytechnique Yaoundé");
            school.add("Polytechnique Maroua");
            school.add("Ecole des Postes");
            school.add("Ecole des Travaux");
            school.add("Faculté du Génie Indistruel");
            school.add("IUT Douala");
            school.add("IUT Bandjoun");
            school.add("IUT Ngaoundéré");
            school.add("Faculté de Médécine Yaoundé");
            school.add("Faculté de Médécine Douala");
            school.add("Biomédical de Dschang");
        }

        if (chapter.size() == 0){
            chapter.add("chapter 1 : limits");
            chapter.add("chapter 2 : derivation and primitive");
            chapter.add("chapter 3 : Equation and inegality");
            chapter.add("chapter 4 : vector of the space");
            chapter.add("chapter 5 : Coniques");
            chapter.add("chapter 6 : arithmetics");
            chapter.add("chapter 7 : complexe numbers");
        }


        GridAdapterForGCE adapter = new GridAdapterForGCE(this.getContext(), school);
        gridView.setAdapter(adapter);
    }

    private void showSubMenu(final int i, final View view) {

        PopupMenu popupMenu = new PopupMenu(this.getContext(), view);

        popupMenu.getMenu().add(MENU1, MENU_1_ITEM, 0, getText(R.string.requierement));
        SubMenu menu2 = popupMenu.getMenu().addSubMenu(MENU2, MENU_2_ITEM, 1, getText(R.string.tutorials));

        //get subject for range chapter
        subjects = new ArrayList<>();
        List<CHAPTER> chaptersofComp = dbManager.getChapterByCompId(competitives.get(i).getCOMP_ID());//get all chapters for this competitive
        if (chaptersofComp!=null){//if chapter is follow
            for (int j=0; j<chaptersofComp.size(); j++){
                List<SUBJECT>  subjectOfComp = dbManager.getSubjectbyId(chaptersofComp.get(j).getSJ_ID());//for all chapter, we get the subject who correspond
                boolean alReadyUsed = false;
                int k=0;
                do{
                    if (subjectOfComp.get(j).getSJ_ID()==subjectOfComp.get(k).getSJ_ID()){//if this subject is already add on String subject list
                        alReadyUsed =true;
                    }
                    j++;
                }while (!alReadyUsed && k<j+1);
                if (!alReadyUsed){//if never use add then
                    subjects.add(subjectOfComp.get(0).getSJ_NAME());
                }
            }
        }else//if any chapter follow show error message
            onErrorLoadind("This competitive is not download, please connect your device to internet");

        if (subjects.size() == 0){
            subjects.add("Mathematics");
            subjects.add("Physical");
            subjects.add("French");
            subjects.add("General culture");
        }

        for (int j =0; j<subjects.size(); j++){
            SubMenu menu2sub = menu2.addSubMenu(MENU2, MENU_2_ITEM+j+1, j, subjects.get(j));

            for (int k =0; k<chapter.size(); k++){
                menu2sub.add(j, MENU_2_ITEM+subjects.size()+1+k+1, j, chapter.get(k));
            }
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == MENU_1_ITEM){
                    List<REQUIEREMENT> requierements = dbManager.getRequierementByCompId(competitives.get(i).getCOMP_ID());
                    if (requierements==null){
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                    }else if (requierements.size()==0)
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                    else {
                        Intent intent = new Intent(getActivity(), RequierementActivity.class);
                        intent.putExtra("title","Requierement for "+school.get(i));
                        intent.putExtra("req_id",requierements.get(0).getREQ_ID());
                        startActivity(intent);
                    }
                }
                else if(item.getItemId() <= MENU_2_ITEM+subjects.size()){
                    //nothing
                }
                else{
                    List<CHAPTER> chapters = dbManager.getChapterByNameAndCompId(competitives.get(i).getCOMP_ID(),item.getTitle().toString());
                    if (chapters==null){
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                    }else if (chapters.size()==0)
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                    else {
                        showDialog(item.getTitle().toString(),chapters.get(0).getCHAP_ID());
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showDialog(final String title, final int chapterId){

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        if (mPrefs.getBoolean(ENABLE, false)) { //get Boolean
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_start_exam, null);

            final AlertDialog builder1 = new AlertDialog.Builder(getContext()).create();
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
                    builder1.cancel();
                    Intent intent = new Intent(getActivity(), Paper1Activity.class);
                    intent.putExtra("paper",chapterId);
                    intent.putExtra("title",title);
                    startActivity(intent);
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
    public void onReceiveCompetitive(List<COMPETITIVE> competitives) {
        dbManager.insertListCompetitive(competitives);
        this.competitives = dbManager.fetchCompetitive();
        if (this.competitives!=null){
            if (this.competitives.size()>0){
                refreshContent();
                position = 0;
                competitivePresenter.getChapter(competitives.get(position).getCOMP_ID());
            }
        }
    }

    @Override
    public void onReceiveChapter(List<CHAPTER> chapters) {
        dbManager.insertListChapter(chapters);
        this.chapters = dbManager.listChapter();
        if (position<competitives.size()-1){
            position++;
            competitivePresenter.getChapter(competitives.get(position).getCOMP_ID());
        }else {
            starGetQuestion();
        }
    }

    @Override
    public void onReceiveQuestion(List<QUESTION> questions) {
        dbManager.insertListQuestion(questions);
        for (int i=0; i<questions.size(); i++){
            competitivePresenter.getAnswers(questions.get(i).getQUEST_ID());
        }
    }

    @Override
    public void onReceiveAnwser(List<ANWSER> anwsers) {
        dbManager.insertListAnwser(anwsers);
    }

    private void starGetQuestion() {
        for (int i =0; i<competitives.size(); i++){
            if (chapters !=null){
                if (chapters.size()>0){
                    competitivePresenter.getQuestions(chapters.get(i).getCHAP_ID(),competitives.get(i).getCOMP_ID());
                }
            }
        }
    }
}