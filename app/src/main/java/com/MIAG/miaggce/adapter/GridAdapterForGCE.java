package com.MIAG.miaggce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.models.Subject_test;

import java.util.ArrayList;
import java.util.List;

public class GridAdapterForGCE extends BaseAdapter {
    private List<Subject_test> list;
    private Context context;

    public GridAdapterForGCE(Context applicationContext, List<Subject_test> list) {
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
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            v = inflater.inflate(R.layout.subject_item, null);
        }

        TextView title = v.findViewById(R.id.text);title.setText(list.get(i).getName());
        TextView caps = v.findViewById(R.id.text_caps);
        int lastPosition = 0;
        StringBuilder text_caps = new StringBuilder();
        while (lastPosition>=0){
            if(lastPosition>0){
                if(list.get(i).getName().substring(lastPosition+1, lastPosition + 2).equals(list.get(i).getName().substring(lastPosition+1, lastPosition + 2).toUpperCase()))
                    text_caps.append(list.get(i).getName().substring(lastPosition+1, lastPosition + 2));
            }
            else{
                if(list.get(i).getName().substring(lastPosition, lastPosition +1).equals(list.get(i).getName().substring(lastPosition, lastPosition +1).toUpperCase()))
                    text_caps.append(list.get(i).getName().substring(lastPosition, lastPosition +1));
            }
            lastPosition = list.get(i).getName().indexOf(" ",lastPosition+1);
        }
        caps.setText(text_caps.toString());
        return v;
    }
}
