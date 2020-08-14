package com.MyMentor.competitive.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.models.BLOC_NOTE;
import java.util.ArrayList;
import java.util.List;

public class ListAdapterForBlocNote extends BaseAdapter {
    private List<BLOC_NOTE> list;
    private Context context;

    public ListAdapterForBlocNote(Context applicationContext, List<BLOC_NOTE> list) {
        this.context = applicationContext;
        this.list = new ArrayList<>();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getNOTE_ID();
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            v = inflater.inflate(R.layout.layout_bloc_note_item, null);
        }
        TextView textContent = v.findViewById(R.id.text_content);
        TextView textDate = v.findViewById(R.id.text_date);
        View barre = v.findViewById(R.id.view_bar);

        textContent.setText(list.get(i).getNOTE_CONTENT());
        textDate.setText(list.get(i).getNOTE_DATE());
        barre.setBackgroundColor(context.getResources().getColor(list.get(i).getNOTE_COLOR()));
        return v;
    }
}