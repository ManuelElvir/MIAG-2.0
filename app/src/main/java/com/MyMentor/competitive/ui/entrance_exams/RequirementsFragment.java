package com.MyMentor.competitive.ui.entrance_exams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.GridAdapterForCOMP;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.ui.download.DownloadActivity;
import com.MyMentor.competitive.models.COMPETITIVE;
import com.MyMentor.competitive.ui.activity.TextActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequirementsFragment extends Fragment{
    private GridView gridView;
    private List<COMPETITIVE> competitives = new ArrayList<>();
    private DBManager dbManager = new DBManager(getActivity());

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_requierement, container, false);

        gridView = root.findViewById(R.id.gridView);

        dbManager.open();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int reqId = dbManager.getRequierementIdByCompId(competitives.get(i).getCOMP_ID());
                if (reqId==0)
                    suggestDownload();
                else{
                    Intent intent = new Intent(getActivity(), TextActivity.class);
                    intent.putExtra("requierementId",reqId);
                    intent.putExtra("title",competitives.get(i).getCOMP_NAME());
                    startActivity(intent);
                }

            }
        });

        getData();
        return root;
    }

    private void getData() {
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
        text.setText(getString(R.string.no_requierement));
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