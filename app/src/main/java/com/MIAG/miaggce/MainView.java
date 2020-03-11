package com.MIAG.miaggce;

import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.EXAM;
import com.MIAG.miaggce.models.SUBJECT;

import java.util.List;

public interface MainView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveExams(List<EXAM> exams);
    void onReceiveSubject(List<SUBJECT> subjects);
    void onReceiveCompetitive(List<COMPETITIVE> competitives);

}
