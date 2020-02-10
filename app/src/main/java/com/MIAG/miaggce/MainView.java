package com.MIAG.miaggce;

import com.MIAG.miaggce.models.EXAM;

import java.util.List;

public interface MainView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveExams(List<EXAM> exams);

}
