package com.MyMentor.competitive.ui.entrance_exams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.GridAdapterForCOMP;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.models.COMPETITIVE;
import com.MyMentor.competitive.models.PAST_QUESTIONS;
import com.MyMentor.competitive.ui.Activity.QcmActivity;
import com.MyMentor.competitive.ui.downloadActivity.DownloadActivity;
import java.util.List;
import java.util.Objects;
import static android.view.Menu.NONE;
import static com.MyMentor.competitive.app.appConfig.ENABLE;
import static com.MyMentor.competitive.app.appConfig.PREFERENCE;

public class ExamSessionsFragment extends Fragment{

    private SharedPreferences mPrefs;
    private GridView gridView;
    private List<COMPETITIVE> competitives;
    private DBManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPrefs = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCE,0);

        View root = inflater.inflate(R.layout.fragment_exam_sessions, container, false);
        gridView = root.findViewById(R.id.gridView);
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
        dbManager = new DBManager(getActivity());
        dbManager.open();
        competitives = dbManager.fetchCompetitive();
        if (competitives.size()==0){
            suggestDownload();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        GridAdapterForCOMP adapter = new GridAdapterForCOMP(this.getContext(), competitives);
        gridView.setAdapter(adapter);
    }

    private void showSubMenu(final int i, final View view) {

        List<Integer> year = dbManager.getAllSessionAvailableForComp(competitives.get(i).getCOMP_ID());
        if (year.size()==0){
            onErrorLoadind(getString(R.string.no_exam));
        }else {

            PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
            for (int j=0; j<year.size(); j++){
                //We add the date to the PopUp Menu
                SubMenu menu = popupMenu.getMenu().addSubMenu(year.get(j));
                //get Past-question from this competitive and this date
                List<PAST_QUESTIONS> past_questionsDate = dbManager.getPastQuestionByCompIdAndYear(competitives.get(i).getCOMP_ID(),year.get(j));
                //add all available past-question to the pop-up sub menu
                for (PAST_QUESTIONS past_question: past_questionsDate) {
                    menu.add(year.get(j),past_question.getPQ_ID(),NONE,past_question.getPQ_TITLE());
                }
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int pastQuestID = item.getItemId();
                    String timing = dbManager.getPastQuestionTimeById(pastQuestID);
                    int QcmId = dbManager.getQcmIdByPastQuesId(pastQuestID);

                    if (QcmId == 0){
                        onErrorLoadind(getString(R.string.no_question));
                        return false;
                    }
                    else{
                        showDialog(item.getTitle().toString(),QcmId,timing);
                        return true;
                    }
                }
            });
            popupMenu.show();
        }
    }


    private void showDialog(final String title, final int QcmId, final String timing){

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        if (mPrefs.getBoolean(ENABLE, false)){ //get Boolean
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_start_exam, null);

            final AlertDialog builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
            builder1.setView(promptView);
            TextView text_title = promptView.findViewById(R.id.title);
            text_title.setText(title);
            Button start = promptView.findViewById(R.id.start);
            Button cancel = promptView.findViewById(R.id.cancel);
            Button back =  promptView.findViewById(R.id.back);
            TextView text_description = promptView.findViewById(R.id.text);
            text_description.setText(text_description.getText().toString().replace("xx",timing));

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    builder1.dismiss();
                }
            });

            builder1.setCancelable(true);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), QcmActivity.class);
                    i.putExtra("qcmId",QcmId);
                    i.putExtra("qcmTitle",title);
                    i.putExtra("qcmTiming",timing);
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


    private void onErrorLoadind(String cause) {
        Toast.makeText(getContext(), cause, Toast.LENGTH_SHORT).show();
    }

    private void suggestDownload() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.layout_download_finish, null);
        final AlertDialog builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
        builder1.setView(promptView);
        Button start = promptView.findViewById(R.id.start);
        Button retry = promptView.findViewById(R.id.cancel);
        TextView text =  promptView.findViewById(R.id.text);
        TextView title =  promptView.findViewById(R.id.title);

        title.setText(R.string.data_error);
        text.setText(getString(R.string.no_sessions));
        retry.setText(R.string.no);

        builder1.setCancelable(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                startActivity(intent);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder1.cancel();
            }
        });
        builder1.show();
    }
}

