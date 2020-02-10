package com.MIAG.miaggce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MIAG.miaggce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapterForImage extends BaseAdapter {
    private List<String> images;
    private Context context;

    public GridAdapterForImage(Context applicationContext, List<String> images) {
        this.context = applicationContext;
        this.images = new ArrayList<String>();
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
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
            v = inflater.inflate(R.layout.image_grid_item, null);
        }
        ImageView imageViewBackground = v.findViewById(R.id.image);
        Picasso.get().load(images.get(i)).into(imageViewBackground);
        return v;
    }
}