package com.MIAG.miaggce.ui.paper2;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;
import org.jetbrains.annotations.NotNull;
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

    void getPaperCorrection(int paperId, boolean isPaper3) {
        paper2View.showLoading();
        Call<SUBJECT_CORRECTION> call;
        if (isPaper3)
             call = apiInterface.getPaper3Correction(paperId);
        else
            call = apiInterface.getPaper2Correction(paperId);
        call.enqueue(new Callback<SUBJECT_CORRECTION>() {
            @Override
            public void onResponse(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Response<SUBJECT_CORRECTION> response) {
                if (response.isSuccessful() && response.body() != null)
                    paper2View.onReceiveCorrection(response.body());
                else
                    paper2View.onErrorLoadind("Error when get Paper Correction");
                paper2View.hideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Throwable t) {
                paper2View.onErrorLoadind(t.getLocalizedMessage());
                paper2View.hideLoadding();
            }
        });
    }
}
