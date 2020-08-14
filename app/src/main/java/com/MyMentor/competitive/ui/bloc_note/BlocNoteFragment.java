package com.MyMentor.competitive.ui.bloc_note;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.ListAdapterForBlocNote;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.models.BLOC_NOTE;
import java.util.List;

public class BlocNoteFragment extends Fragment {

    private ListView listView;
    private List<BLOC_NOTE> bloc_notes;
    private DBManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bloc_note, container, false);

        listView =  root.findViewById(R.id.listView);

        dbManager = new DBManager(getContext());
        dbManager.open();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openBlocnote(bloc_notes.get(i).getNOTE_ID());
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContent();
    }

    private void refreshContent() {
        bloc_notes = dbManager.listBlocNote();

        ListAdapterForBlocNote listAdapter = new ListAdapterForBlocNote(this.getContext(), bloc_notes);
        listView.setAdapter(listAdapter);
    }

    private void openBlocnote(int id) {
        Intent intent = new Intent(getActivity(), BlocNoteActivity.class);
        intent.putExtra("bloc_noteId",id);
        startActivity(intent);
    }
}