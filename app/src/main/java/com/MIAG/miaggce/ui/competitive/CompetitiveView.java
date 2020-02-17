package com.MIAG.miaggce.ui.competitive;

import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.QUESTION;

import java.util.List;

public interface CompetitiveView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveCompetitive(List<COMPETITIVE> competitives);
    void onReceiveChapter(List<CHAPTER> chapters);
    void onReceiveQuestion(List<QUESTION> questions);
    void onReceiveAnwser(List<ANWSER> anwsers);
    void openRequierement(String pathFile, String reqName);
}
