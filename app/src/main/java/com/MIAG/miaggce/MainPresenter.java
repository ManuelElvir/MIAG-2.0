package com.MIAG.miaggce;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.EXAM;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainPresenter {
    private MainView mainView;
    private ApiInterface apiInterface;

    MainPresenter(MainView mainView, String userKey) {
        this.mainView = mainView;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    void getExams(){
        mainView.showLoading();
        Call<List<EXAM>> call = apiInterface.listExams();
        call.enqueue(new Callback<List<EXAM>>() {
            @Override
            public void onResponse(@NotNull Call<List<EXAM>> call,@NotNull Response<List<EXAM>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    mainView.onReceiveExams(response.body());
                else
                    mainView.onErrorLoadind("Error when getExams");
                mainView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<EXAM>> call, @NotNull Throwable t) {
                mainView.onErrorLoadind(t.getLocalizedMessage());
                mainView.HideLoadding();
            }
        });
    }
}
