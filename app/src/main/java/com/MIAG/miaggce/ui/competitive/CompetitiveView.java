package com.MIAG.miaggce.ui.competitive;

import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.REQUIEREMENT;
import com.MIAG.miaggce.models.TUTORIAL;

import java.util.List;

public interface CompetitiveView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveChapter(List<CHAPTER> chapters);
    void onReceiveQuestion(List<QUESTION> questions, int chapterId);
    void onReceiveAnwser(List<ANWSER> anwsers);
    void onReceiveRequierement(List<REQUIEREMENT> requierements);
    void onReceiveTutorial(List<TUTORIAL> tutorials);
    void openRequierement(String pathFile, String reqName);
}
