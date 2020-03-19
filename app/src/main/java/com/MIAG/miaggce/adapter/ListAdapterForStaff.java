package com.MIAG.miaggce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.models.STAFFMEMBER;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterForStaff extends BaseAdapter {
    private List<STAFFMEMBER> list;
    private Context context;
    private ItemClicklistener itemClicklistener;

    public ListAdapterForStaff(Context applicationContext, List<STAFFMEMBER> list, ItemClicklistener itemClicklistener) {
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
        CircularImageView image = v.findViewById(R.id.image);

        name.setText(list.get(i).getSM_NAME());
        function.setText(list.get(i).getSM_FUNCTION());
        if (list.get(i).getSM_IMAGE()!=null&&  !list.get(i).getSM_IMAGE().isEmpty())
            Picasso.get().load(list.get(i).getSM_IMAGE()).into(image);
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
        void onItemClick(View view, int position);
        void onItemClick2(View view, int position);
    }
}
