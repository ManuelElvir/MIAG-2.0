package com.MIAG.miaggce;

import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.EXAM;

import java.util.List;

public interface MainView {
    void showLoading();
    void HideLoadding();
    void StartDownload();
    void endDownload();
    void onErrorLoadind(String cause);
    void onReceiveExams(List<EXAM> exams);
    void onReceiveCompetitives(List<COMPETITIVE> competitives);

}
