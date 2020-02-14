package com.MIAG.miaggce.ui.paper2;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Paper2Presenter
{

    private Paper2View paper2View;
    private ApiInterface apiInterface;

    Paper2Presenter(Paper2View paper2View, String userKey) {
        this.paper2View = paper2View;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    void getPaper2(int SJ_Id){
        paper2View.showLoading();
        Call<List<PAPER2>> call = apiInterface.listPaper2(SJ_Id);
        call.enqueue(new Callback<List<PAPER2>>() {
            @Override
            public void onResponse(@NotNull Call<List<PAPER2>> call, @NotNull Response<List<PAPER2>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    paper2View.onReceivePaper2(response.body());
                else
                    paper2View.onErrorLoadind("Error when get paper");
                paper2View.hideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<PAPER2>> call, @NotNull Throwable t) {
                paper2View.onErrorLoadind(t.getLocalizedMessage());
                paper2View.hideLoadding();
            }
        });
    }

    void getPaper3(int SJ_Id) {
        paper2View.showLoading();
        Call<List<PAPER3>> call = apiInterface.listPaper3(SJ_Id);
        call.enqueue(new Callback<List<PAPER3>>() {
            @Override
            public void onResponse(@NotNull Call<List<PAPER3>> call, @NotNull Response<List<PAPER3>> response) {
                if (response.isSuccessful() && response.body() != null)
                    paper2View.onReceivePaper3(response.body());
                else
                    paper2View.onErrorLoadind("Error when get paper");
                paper2View.hideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<PAPER3>> call, @NotNull Throwable t) {
                paper2View.onErrorLoadind(t.getLocalizedMessage());
                paper2View.hideLoadding();
            }
        });
    }

    void getPaperCorrection(int paperId) {
        paper2View.showLoading();
        Call<List<SUBJECT_CORRECTION>> call = apiInterface.getSubjectCorrection(paperId);
        call.enqueue(new Callback<List<SUBJECT_CORRECTION>>() {
            @Override
            public void onResponse(@NotNull Call<List<SUBJECT_CORRECTION>> call, @NotNull Response<List<SUBJECT_CORRECTION>> response) {
                if (response.isSuccessful() && response.body() != null)
                    paper2View.onReceiveCorrection(response.body());
                else
                    paper2View.onErrorLoadind("Error when get paper");
                paper2View.hideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<SUBJECT_CORRECTION>> call, @NotNull Throwable t) {
                paper2View.onErrorLoadind(t.getLocalizedMessage());
                paper2View.hideLoadding();
            }
        });
    }
}
