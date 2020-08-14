package com.MyMentor.competitive.ui.entrance_exams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.GridAdapterForCOMP;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.ui.download.DownloadActivity;
import com.MyMentor.competitive.models.CHAPTER;
import com.MyMentor.competitive.models.COMPETITIVE;
import com.MyMentor.competitive.models.SUBJECT;
import com.MyMentor.competitive.ui.activity.TextActivity;
import com.MyMentor.competitive.ui.activity.QcmActivity;
import java.util.List;
import java.util.Objects;
import static com.MyMentor.competitive.app.appConfig.ENABLE;
import static com.MyMentor.competitive.app.appConfig.PREFERENCE;
import static java.sql.Types.NULL;

public class TutorialsFragment extends Fragment{

    private SharedPreferences mPrefs;
    private GridView gridView;
    private DBManager dbManager;
    private List<COMPETITIVE> competitives;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tutorials, container, false);

        gridView = root.findViewById(R.id.gridView);
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

        List<SUBJECT> subjects = dbManager.getAllSubjectAvailableForComp(competitives.get(i).getCOMP_ID());

        if (subjects.size()==0){
            onErrorLoadind(getString(R.string.no_subject));
        }
        else {
            PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
            for (int j=0; j<subjects.size(); j++){

                //add subject
                SubMenu menu = popupMenu.getMenu().addSubMenu(subjects.get(j).getSJ_NAME());
                //get all chapters for this subject
                List<CHAPTER> chapters = dbManager.getChapterBySubjectId(subjects.get(j).getSJ_ID());

                for (int k=0; k<chapters.size(); k++){
                    //add chapter > subject
                    SubMenu sub_menu = menu.addSubMenu(chapters.get(k).getCHAP_NAME());
                    int noteId = dbManager.getNoteIdByChapId(chapters.get(k).getCHAP_ID());
                    int qcmId = dbManager.getQcmIdByChapId(chapters.get(k).getCHAP_ID());

                    //add note > chapter > subject
                    if (noteId>0)
                        sub_menu.add(k,noteId,NULL,"Note for "+ chapters.get(k).getCHAP_NAME());

                    //add quizz > chapter > subject
                    if (qcmId>0)
                        sub_menu.add(k,noteId,NULL,"Quizz for "+ chapters.get(k).getCHAP_NAME());

                }
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    boolean isQuiz = item.getTitle().toString().contains("Quizz");
                    String timing = "02-00";
                    showDialog(item.getTitle().toString(),item.getItemId(),timing, isQuiz);
                    return false;
                }
            });
            popupMenu.show();
        }
    }



    private void showDialog(final String title, final int id, final String timing, final boolean isQuizz){

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
            final TextView text_description = promptView.findViewById(R.id.text);
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
                    Intent i;
                    if (isQuizz){
                        i = new Intent(getActivity(), QcmActivity.class);
                        i.putExtra("qcmId",id);
                        i.putExtra("qcmTiming",timing);
                    }else {
                        text_description.setText(R.string.start_paper3_message);
                        i = new Intent(getActivity(), TextActivity.class);
                        i.putExtra("noteId",id);
                    }
                    i.putExtra("title",title);

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
        if (TutorialsFragment.this.getContext()!=null)
            Toast.makeText(TutorialsFragment.this.getContext(), cause, Toast.LENGTH_SHORT).show();
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
        text.setText(getString(R.string.no_tutorial));
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
