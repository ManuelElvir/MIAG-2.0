package com.MIAG.miaggce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.models.TeamMember;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterForStaff extends BaseAdapter {
    private List<TeamMember> list;
    private Context context;
    private ItemClicklistener itemClicklistener;

    public ListAdapterForStaff(Context applicationContext, List<TeamMember> list, ItemClicklistener itemClicklistener) {
        this.context = applicationContext;
        this.list = new ArrayList<>();
        this.list = list;
        this.itemClicklistener = itemClicklistener;
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
            v = inflater.inflate(R.layout.member_item, null);
        }
        TextView name = v.findViewById(R.id.name);
        TextView function = v.findViewById(R.id.function);
        Button whatsapp = v.findViewById(R.id.contact);
        Button facebook = v.findViewById(R.id.contact_2);
        CircularImageView image = (CircularImageView) v.findViewById(R.id.image);

        name.setText(list.get(i).getName());
        function.setText(list.get(i).getFunction());
        function.setText(list.get(i).getFunction());
        //Picasso.get().load("https://i.imgur.com/oW1dGDI.jpg").into(image);
        final int position = i;
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicklistener.onItemClick(view,position);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicklistener.onItemClick2(view,position);
            }
        });
        return v;
    }



    public interface ItemClicklistener {
        public void onItemClick(View view, int position);
        public void onItemClick2(View view, int position);
    }
}
