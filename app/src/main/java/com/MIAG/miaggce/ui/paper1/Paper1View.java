package com.MIAG.miaggce.ui.paper1;

import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.QUESTION;

import java.util.List;

interface Paper1View {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveQuestion(List<QUESTION> questions);
    void onReceiveAnwser(List<ANWSER> anwsers);
}
