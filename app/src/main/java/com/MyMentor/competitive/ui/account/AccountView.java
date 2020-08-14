package com.MyMentor.competitive.ui.account;

import com.MyMentor.competitive.models.RESPONSE;

interface AccountView {
    void onShowLoading();
    void onHideLoadig();
    void onUpdateSuccess(RESPONSE response);
    void onReceiveError(String message);
    void onRegisterSuccess();

}
