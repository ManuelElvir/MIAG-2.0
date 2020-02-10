package com.MIAG.miaggce.ui.gce_a;

import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.SUBJECT;

import java.util.List;

public interface GceView {
    void showLoading();
    void HideLoadding();
    void onErrorLoadind(String cause);
    void onReceiveSubject(List<SUBJECT> subjects);
    void onReceivePaper1(List<PAPER1> paper1s);
    void onReceivePaper2(List<PAPER2> paper2s);
    void onReceivePaper3(List<PAPER3> paper3s);
}
