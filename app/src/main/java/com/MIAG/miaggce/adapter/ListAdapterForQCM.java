package com.MIAG.miaggce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.models.Answer_test;
import com.MIAG.miaggce.models.QCM;

import java.util.ArrayList;
import java.util.List;

import static com.MIAG.miaggce.ui.paper1.Paper1Activity.results;

public class ListAdapterForQCM extends BaseAdapter {
    private Context context;
    private List<QCM> qcms;
    private boolean isCorrection;
    public ListAdapterForQCM(Context context, List<QCM> qcms, boolean isCorrection){
        this.context = context;
        this.qcms = new ArrayList<>();
        this.qcms = qcms;
        this.isCorrection = isCorrection;
        results = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return qcms.size();
    }

    @Override
    public Object getItem(int i) {
        return qcms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return qcms.get(i).getId();
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            v = inflater.inflate(R.layout.qcm_item_layout, null);
        }else {
            Log.e("INFLATE ERROR","erreur survenu");
        }
        if (results.size()<=i){
            results.add(i,new Answer_test(5));
        }
        TextView question = v.findViewById(R.id.question);
        TextView numero = v.findViewById(R.id.numero);
        RadioButton answer1 = v.findViewById(R.id.answer1);
        RadioButton answer2 = v.findViewById(R.id.answer2);
        RadioButton answer3 = v.findViewById(R.id.answer3);
        RadioButton answer4 = v.findViewById(R.id.answer4);

        question.setText(qcms.get(i).getQuestion());
        numero.setText(""+(i+1));
        answer1.setText(qcms.get(i).getAnswer1());
        answer2.setText(qcms.get(i).getAnswer2());
        answer3.setText(qcms.get(i).getAnswer3());
        answer4.setText(qcms.get(i).getAnswer4());
        if (isCorrection){
            switch (qcms.get(i).getCorrect_answer()){
                case 1:
                    answer1.setChecked(true);
                    break;
                case 2:
                    answer2.setChecked(true);
                    break;
                case 3:
                    answer3.setChecked(true);
                    break;
                case 4:
                    answer4.setChecked(true);
                    break;
            }
            answer1.setClickable(false);
            answer2.setClickable(false);
            answer3.setClickable(false);
            answer4.setClickable(false);
        }else {
            if(results.get(i).getAnswer()==1){
                answer1.setChecked(true);
            }
            if(results.get(i).getAnswer()==2){
                answer2.setChecked(true);
            }
            if(results.get(i).getAnswer()==3){
                answer3.setChecked(true);
            }
            if(results.get(i).getAnswer()==4){
                answer4.setChecked(true);
            }
            final int position = i;
            answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        results.get(position).setAnswer(1);
                    }
                }
            });
            answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        results.get(position).setAnswer(2);
                    }
                }
            });
            answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        results.get(position).setAnswer(3);
                    }
                }
            });
            answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        results.get(position).setAnswer(4);
                    }
                }
            });
        }
        return v;
    }
}
