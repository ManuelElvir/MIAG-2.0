package com.MIAG.miaggce.ui.competitive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForCOMP;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.DownloadFileFromURL;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.REQUIEREMENT;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.models.TUTORIAL;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.MIAG.miaggce.ui.requierement.RequierementActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class CompetitiveFragment extends Fragment implements CompetitiveView {
    private GridView gridView;
    private SharedPreferences mPrefs;
    private ProgressBar progressBar;
    private CompetitivePresenter competitivePresenter;
    private List<COMPETITIVE> competitives = new ArrayList<>();
    private List<TUTORIAL> tutorials = new ArrayList<>();
    private DBManager dbManager;
    private int position;

    private static final int MENU1 = 0;
    private static final int MENU2 = 1;

    private static final int MENU_1_ITEM = Menu.FIRST;
    private static final int MENU_2_ITEM = Menu.FIRST + 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_competitive, container, false);

        gridView = root.findViewById(R.id.gridView);
        mPrefs = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCE,0);

        progressBar = root.findViewById(R.id.progressBar);
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
        competitivePresenter = new CompetitivePresenter(this, MainActivity.userKey);
        dbManager = new DBManager(getActivity());
        dbManager.open();

        this.competitives = dbManager.fetchCompetitive();
        refreshContent();
        if (this.competitives!=null){
            if (this.competitives.size()>0){
                position = 0;
                competitivePresenter.getChapter(competitives.get(position).getCOMP_ID());
            }
        }
    }

    private void refreshContent() {
        GridAdapterForCOMP adapter = new GridAdapterForCOMP(this.getContext(), competitives);
        gridView.setAdapter(adapter);
    }

    private void showSubMenu(final int i, final View view) {

        PopupMenu popupMenu = new PopupMenu(this.getContext(), view);

        popupMenu.getMenu().add(MENU1, MENU_1_ITEM, 0, getText(R.string.requierement));
        SubMenu menu2 = popupMenu.getMenu().addSubMenu(MENU2, MENU_2_ITEM, 1, getText(R.string.tutorials));

        //get subject for range chapter
        List<SUBJECT> subjects = new ArrayList<>();
        List<CHAPTER> chapters = dbManager.getChapterByCompId(competitives.get(i).getCOMP_ID());//get all chapters for this competitive

        if (chapters!=null){//if chapter is follow
            for (int j=0; j<chapters.size(); j++){
                SUBJECT subject = dbManager.getSubjectById(chapters.get(j).getSJ_ID());
                //This method add subject awich verify the the current subject is already used
                if (!subject.equals(new SUBJECT())){
                    boolean alReadyUsed = false;
                    int k=1;
                    if (j==0)
                        subjects.add(subject);
                    do{
                        if (subjects.get(k).getSJ_ID()==subject.getSJ_ID()){//if this subject is already add on String subject list
                            alReadyUsed =true;
                        }
                        k++;
                    }while (!alReadyUsed && k<j+1);
                    if (!alReadyUsed){//if never use add then
                        subjects.add(subject);
                    }
                }

            }
            if (chapters.size()==0)
                onErrorLoadind(getString(R.string.no_chapter));
            else {
                for (int j = 0; j< subjects.size(); j++){
                    SubMenu menu2sub = menu2.addSubMenu(subjects.get(j).getSJ_NAME());
                    for (int k =0; k<chapters.size(); k++){
                        SubMenu menu3sub =menu2sub.addSubMenu(chapters.get(k).getCHAP_NAME());
                        List<TUTORIAL> tutorials = dbManager.getTutorialByCompAndChapter(i,chapters.get(k).getCHAP_ID());
                        for (int l=0; l<tutorials.size(); l++){
                            menu3sub.add(tutorials.get(l).getTUTO_ID(),10000+l,l,tutorials.get(l).getTUTO_NAME());
                        }
                        if (tutorials.size()==0)
                            onErrorLoadind(getString(R.string.no_tutorial));
                    }
                }
            }
        }else//if any chapter follow show error message
            onErrorLoadind(getString(R.string.no_chapter));

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == MENU_1_ITEM){
                    List<REQUIEREMENT> requierements = dbManager.getRequierementByCompId(competitives.get(i).getCOMP_ID());
                    if (requierements==null){
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                        competitivePresenter.getRequierement(competitives.get(i).getCOMP_ID());
                    }else if (requierements.size()==0)
                        onErrorLoadind("This requierement is not download, please connect your device to internet");
                    else {
                        if (requierements.get(0).getREQ_CONTENT().isEmpty()){
                            DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL("Requierement for "+competitives.get(i),requierements.get(0).getREQ_NAME(),getContext(),CompetitiveFragment.this);
                            downloadFileFromURL.execute(requierements.get(0).getREQ_FILE());
                        }else{
                            Intent intentReq = new Intent(getActivity(),RequierementActivity.class);
                            intentReq.putExtra("req",requierements.get(0).getREQ_ID());
                            intentReq.putExtra("isFile",true);
                            startActivity(intentReq);
                        }
                    }
                }
                else if(item.getItemId() >= 10000){
                    int tutorialid = item.getItemId()-10000;
                    TUTORIAL tutorial = dbManager.getTutorialById(tutorialid);
                    showDialog(tutorial.getTUTO_NAME(),tutorial.getTUTO_ID());
                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void showDialog(final String title, final int tuto_id){

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
                    builder1.cancel();
                    Intent intent = new Intent(getActivity(), Paper1Activity.class);
                    intent.putExtra("tuto",tuto_id);
                    intent.putExtra("title",title);
                    intent.putExtra("isChapter",true);
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
    public void onReceiveChapter(List<CHAPTER> chapters) {
        dbManager.insertListChapter(chapters);
        competitivePresenter.getTutorial(competitives.get(position).getCOMP_ID());
    }

    @Override
    public void onReceiveTutorial(List<TUTORIAL> tutorials){
        dbManager.insertListTutorial(tutorials);
        for (int i=0; i<tutorials.size(); i++){
            TUTORIAL tutorial = tutorials.get(i);
            if (!this.tutorials.contains(tutorial))
                this.tutorials.add(tutorial);
        }
        if (position<competitives.size()-1){
            position++;
            competitivePresenter.getChapter(competitives.get(position).getCOMP_ID());
        }else {
            starGetQuestion();
        }
    }

    @Override
    public void onReceiveQuestion(List<QUESTION> questions, int chapterId) {
        dbManager.insertListQuestion(questions,0, chapterId);
        for (int i=0; i<questions.size(); i++){
            competitivePresenter.getAnswers(questions.get(i).getQUEST_ID());
        }
    }

    @Override
    public void onReceiveAnwser(List<ANWSER> anwsers) {
        dbManager.insertListAnwser(anwsers);
    }

    @Override
    public void onReceiveRequierement(List<REQUIEREMENT> requierements) {
        dbManager.insertListRequierement(requierements);
    }

    @Override
    public void openRequierement(String pathFile, String reqName) {
        Intent intent = new Intent(getActivity(), RequierementActivity.class);
        intent.putExtra("title",reqName);
        intent.putExtra("pathFile",pathFile);
        startActivity(intent);
    }

    private void starGetQuestion() {
        for (int i =0; i<tutorials.size(); i++){
            competitivePresenter.getQuestions(tutorials.get(i).getTUTO_ID());
        }
    }
}