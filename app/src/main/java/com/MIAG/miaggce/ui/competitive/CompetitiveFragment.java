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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForGCE;
import com.MIAG.miaggce.ui.paper1.Paper1Activity;
import com.MIAG.miaggce.ui.requierement.RequierementActivity;

import java.util.ArrayList;
import java.util.List;

import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class CompetitiveFragment extends Fragment {
    private GridView gridView;
    private List<String> school;
    private List<String> subjects;
    private List<String> chapter;
    private boolean isclick = false;
    private SharedPreferences mPrefs;

    private static final int MENU1 = 0;
    private static final int MENU2 = 1;

    private static final int MENU_1_ITEM = Menu.FIRST;
    private static final int MENU_2_ITEM = Menu.FIRST + 1;
    private static final int MENU_2_1_ITEM = MENU_2_ITEM + 1;
    private static final int MENU_2_2_ITEM = MENU_2_ITEM + 2;
    private static final int MENU_2_3_ITEM = MENU_2_ITEM + 1;
    private static final int MENU_2_4_ITEM = MENU_2_ITEM + 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_competitive, container, false);

        gridView = root.findViewById(R.id.gridView);
        mPrefs = getContext().getSharedPreferences(PREFERENCE,0);

        //replace to dynamics
        school = new ArrayList<>();
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

        subjects = new ArrayList<>();
        subjects.add("Mathematics");
        subjects.add("Physical");
        subjects.add("French");
        subjects.add("General culture");


        chapter = new ArrayList<>();
        chapter.add("chapter 1 : limits");
        chapter.add("chapter 2 : derivation and primitive");
        chapter.add("chapter 3 : Equation and inegality");
        chapter.add("chapter 4 : vector of the space");
        chapter.add("chapter 5 : Coniques");
        chapter.add("chapter 6 : arithmetics");
        chapter.add("chapter 7 : complexe numbers");

        GridAdapterForGCE adapter = new GridAdapterForGCE(this.getContext(), school);
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

        popupMenu.getMenu().add(MENU1, MENU_1_ITEM, 0, getText(R.string.requierement));
        SubMenu menu2 = popupMenu.getMenu().addSubMenu(MENU2, MENU_2_ITEM, 1, getText(R.string.tutorials));
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
                    Intent intent = new Intent(getActivity(), RequierementActivity.class);
                    intent.putExtra("title","Requierement for "+school.get(i));
                    startActivity(intent);
                }
                else if(item.getItemId() <= MENU_2_ITEM+subjects.size()){
                    //nothing
                }
                else{
                    showDialog(item.getTitle().toString(),item.getGroupId());
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showDialog(final String title, final int paperId){

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
                    intent.putExtra("paper",paperId);
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
}