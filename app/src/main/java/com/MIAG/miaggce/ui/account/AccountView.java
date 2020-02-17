package com.MIAG.miaggce.ui.account;

import com.MIAG.miaggce.models.RESPONSE;

interface AccountView {
    void onShowLoading();
    void onHideLoadig();
    void onUpdateSuccess(RESPONSE response);
    void onReceiveError(String message);
    void onRegisterSuccess();

}
