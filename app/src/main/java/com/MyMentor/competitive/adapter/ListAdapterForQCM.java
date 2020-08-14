package com.MyMentor.competitive.adapter;

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
import com.MyMentor.competitive.R;

import static com.MyMentor.competitive.ui.activity.QcmActivity.answers;
import static com.MyMentor.competitive.ui.activity.QcmActivity.isCorrection;
import static com.MyMentor.competitive.ui.activity.QcmActivity.questions;
import static com.MyMentor.competitive.ui.activity.QcmActivity.results;

public class ListAdapterForQCM extends BaseAdapter {
    private Context context;
    public ListAdapterForQCM(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return questions.get(i).getQUEST_ID();
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
            results.add(i,6);
        }
        TextView question = v.findViewById(R.id.question);
        TextView numero = v.findViewById(R.id.numero);
        RadioButton[] answerRadio = new RadioButton[6];
        answerRadio[0] = v.findViewById(R.id.answer1);
        answerRadio[1] = v.findViewById(R.id.answer2);
        answerRadio[2] = v.findViewById(R.id.answer3);
        answerRadio[3] = v.findViewById(R.id.answer4);
        answerRadio[4] = v.findViewById(R.id.answer5);
        answerRadio[5] = v.findViewById(R.id.answer6);

        question.setText(questions.get(i).getQUEST_LABEL());
        numero.setText(""+(i+1));

        int j=0;
        while (j<6 && j<answers.get(i).size()){
            answerRadio[j].setVisibility(View.VISIBLE);
            answerRadio[j].setText(answers.get(i).get(j).getANWS_CONTENT());
            j++;
        }

        if (isCorrection){
            //shwo indication
            TextView indication = v.findViewById(R.id.text_indication);
            indication.setVisibility(View.VISIBLE);
            indication.setText(questions.get(i).getQUEST_ANSWER());

            //check answer that student has check
            if(results.get(i)<6){
                int correctAnswer=6, k=0;
                while (correctAnswer>5 && k<answers.get(i).size()){
                    if (answers.get(i).get(k).getANWS_STATE() == 1){
                        correctAnswer = k;
                    }
                    k++;
                }
                //check a student's answer
                answerRadio[results.get(i)].setChecked(true);
                //set color for correct answer
                answerRadio[correctAnswer].setBackground(context.getResources().getDrawable(R.drawable.background_green));
                if (correctAnswer!=results.get(i)){
                    //set color for bad student's answer
                    answerRadio[results.get(i)].setBackground(context.getResources().getDrawable(R.drawable.background_red));
                }
            }
            for (RadioButton radioButton : answerRadio) {
                radioButton.setClickable(false);
            }
        }
        else {
            if (results.get(i)<6){
                answerRadio[results.get(i)].setChecked(true);
            }
            final int position = i;
            for (int m= 0; m<answerRadio.length; m++) {
                final int finalM = m;
                answerRadio[m].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            results.set(position,finalM);
                        }
                    }
                });
            }
        }
        return v;
    }
}
