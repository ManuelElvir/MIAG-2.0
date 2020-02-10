package com.MIAG.miaggce.ui.gce_a;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.EXAM;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.SUBJECT;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GcePresenter {
    private GceView gceView;
    private ApiInterface apiInterface;

    public GcePresenter(GceView gceView, String userKey) {
        this.gceView = gceView;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    public void getSubject(int EXAM_ID){
        gceView.showLoading();
        Call<List<SUBJECT>> call = apiInterface.getSubject(EXAM_ID,null,null);
        call.enqueue(new Callback<List<SUBJECT>>() {
            @Override
            public void onResponse(@NotNull Call<List<SUBJECT>> call, @NotNull Response<List<SUBJECT>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceiveSubject(response.body());
                else
                    gceView.onErrorLoadind("Error when get Subjects");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<SUBJECT>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind(t.getLocalizedMessage());
                gceView.HideLoadding();
            }
        });
    }

    private void getPaper1(int SJ_ID) {
        gceView.showLoading();
        Call<List<PAPER1>> call = apiInterface.listPaper1(SJ_ID);
        call.enqueue(new Callback<List<PAPER1>>() {
            @Override
            public void onResponse(@NotNull Call<List<PAPER1>> call, @NotNull Response<List<PAPER1>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceivePaper1(response.body());
                else
                    gceView.onErrorLoadind("Error when get Subjects");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<PAPER1>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind(t.getLocalizedMessage());
                gceView.HideLoadding();
            }
        });
    }

    private void getPaper2(int SJ_ID) {
        gceView.showLoading();
        Call<List<PAPER2>> call = apiInterface.listPaper2(SJ_ID);
        call.enqueue(new Callback<List<PAPER2>>() {
            @Override
            public void onResponse(@NotNull Call<List<PAPER2>> call, @NotNull Response<List<PAPER2>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceivePaper2(response.body());
                else
                    gceView.onErrorLoadind("Error when get Subjects");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<PAPER2>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind(t.getLocalizedMessage());
                gceView.HideLoadding();
            }
        });
    }

    private void getPaper3(int SJ_ID) {
        gceView.showLoading();
        Call<List<PAPER3>> call = apiInterface.listPaper3(SJ_ID);
        call.enqueue(new Callback<List<PAPER3>>() {
            @Override
            public void onResponse(@NotNull Call<List<PAPER3>> call, @NotNull Response<List<PAPER3>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceivePaper3(response.body());
                else
                    gceView.onErrorLoadind("Error when get Subjects");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<PAPER3>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind(t.getLocalizedMessage());
                gceView.HideLoadding();
            }
        });
    }
}
