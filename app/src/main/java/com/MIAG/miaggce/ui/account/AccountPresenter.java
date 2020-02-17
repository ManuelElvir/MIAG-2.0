package com.MIAG.miaggce.ui.account;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.RESPONSE;

import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 class AccountPresenter {
    private AccountView view;
    private ApiInterface apiInterface;

    AccountPresenter(AccountView view, String userKey) {
        this.view = view;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    void updateUser(int STD_ID, String STD_NAME, String STD_NUMBER, String STD_PASSWORD, String STD_EMAIL, String STD_TEL_PARENT1, String STD_TEL_PARENT2){
        view.onShowLoading();
        Call<RESPONSE> call = apiInterface.updateStudent(STD_ID,STD_NAME,STD_NUMBER,STD_PASSWORD,STD_EMAIL,STD_TEL_PARENT1,STD_TEL_PARENT2);
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()==1)
                        view.onUpdateSuccess(response.body());
                    else
                        view.onReceiveError(response.body().getCause());
                }
                else
                    view.onReceiveError("Error when update user");
                view.onHideLoadig();
            }

            @Override
            public void onFailure(@NotNull Call<RESPONSE> call, @NotNull Throwable t) {
                view.onReceiveError(t.getLocalizedMessage());
                view.onHideLoadig();
            }
        });
    }

    void register(int STD_ID, String code){
        view.onShowLoading();
        Call<RESPONSE> call = apiInterface.registerStudent(STD_ID,code);
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()==1)
                        view.onUpdateSuccess(response.body());
                    else
                        view.onReceiveError(response.body().getCause());
                }
                else
                    view.onReceiveError("Error when register user");
                view.onHideLoadig();
            }

            @Override
            public void onFailure(@NotNull Call<RESPONSE> call, @NotNull Throwable t) {
                view.onReceiveError(t.getLocalizedMessage());
                view.onHideLoadig();
            }
        });
    }
}
