package com.MIAG.miaggce.ui.gce_a;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForGCE;
import com.MIAG.miaggce.models.Subject_test;
import com.MIAG.miaggce.ui.paper2.Paper2Activity;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class GceAFragment extends Fragment{

    private List<Subject_test> subjects;
    private List<String> years;
    private SharedPreferences mPrefs;
    public static String SUBJECTS = "subjects";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPrefs = getContext().getSharedPreferences(PREFERENCE,0);

        View root = inflater.inflate(R.layout.fragment_gce_a, container, false);
        GridView gridView = root.findViewById(R.id.gridView);

        //replace to dynamics
        subjects = new ArrayList<>();
        subjects.add(new Subject_test(0,"Mathematics"));
        subjects.add(new Subject_test(0,"Physical"));
        subjects.add(new Subject_test(0,"Chemistry"));
        subjects.add(new Subject_test(0,"Biology"));
        subjects.add(new Subject_test(0,"Economy"));
        subjects.add(new Subject_test(0,"History"));
        subjects.add(new Subject_test(0,"Geography"));
        subjects.add(new Subject_test(0,"French"));
        subjects.add(new Subject_test(0,"English"));
        subjects.add(new Subject_test(0,"Biologie"));
        subjects.add(new Subject_test(0,"Others"));

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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showSubMenu(i, view);
            }
        });

        return root;
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
                int paperid = getPaperFromSubjectAndyear(years.get(item.getGroupId()), subjects.get(i).getId());
                if(item.getTitle() == getText(R.string.paper_1))
                    showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i).getName(),PAPER.PAPER1,paperid);
                else if(item.getTitle() == getText(R.string.paper_2))
                    showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i).getName(),PAPER.PAPER2,paperid);
                else if(item.getTitle() == getText(R.string.paper_3))
                    showDialog(getText(R.string.gce_as)+" "+years.get(item.getGroupId())+" : "+subjects.get(i).getName(),PAPER.PAPER3,paperid);
                return false;
            }
        });
        popupMenu.show();
    }

    //replace to call from server
    private int getPaperFromSubjectAndyear(String s, int id) {
        return 0;
    }

    private void showDialog(final String title, final PAPER paper, final int paperId){

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        if (mPrefs.getBoolean(ENABLE, false)){ //get Boolean
            View promptView = layoutInflater.inflate(R.layout.layout_start_exam, null);

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
        }else{
            View promptView = layoutInflater.inflate(R.layout.layout_not_register, null);
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

    private List<Subject_test> getSubjectToPreference(){
        Gson gson = new Gson();
        String json = mPrefs.getString(SUBJECTS, "");
        List<Subject_test> subjects = gson.fromJson(json, new TypeToken<ArrayList<Subject_test>>(){}.getType());
        return subjects;
    }
}

