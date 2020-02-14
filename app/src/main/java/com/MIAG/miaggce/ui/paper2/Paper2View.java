package com.MIAG.miaggce.ui.paper2;

import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;

import java.util.List;

interface Paper2View {
    void showLoading();
    void hideLoadding();
    void onErrorLoadind(String cause);
    void onReceivePaper2(List<PAPER2> paper2);
    void onReceivePaper3(List<PAPER3> paper3);
    void onReceiveCorrection(List<SUBJECT_CORRECTION> corrections);
}
