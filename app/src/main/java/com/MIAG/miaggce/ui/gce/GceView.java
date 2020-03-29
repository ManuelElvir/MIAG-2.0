package com.MIAG.miaggce.ui.gce;

import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;

import java.util.List;

public interface GceView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceivePaper1(List<PAPER1> paper1s);
    void onReceivePaper2(List<PAPER2> paper2s);
    void onReceivePaper3(List<PAPER3> paper3s);
    void onReceiveQuestion(List<QUESTION> questions, int paperId);
    void onReceivePaper2Correction(SUBJECT_CORRECTION correction);
    void onReceivePaper3Correction(SUBJECT_CORRECTION correction);
    void onReceiveAnwser(List<ANWSER> anwsers, int questId);
}
