package com.MIAG.miaggce.ui.paper1;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.QUESTION;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Paper1Presenter {
    private Paper1View paper1View;
    private ApiInterface apiInterface;

    Paper1Presenter(Paper1View paper1View, String userKey) {
        this.paper1View = paper1View;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    void getQuestionByPaperId(int paperId){
        paper1View.showLoading();
        Call<List<QUESTION>> call = apiInterface.listQuestionPaper1(paperId);
        call.enqueue(new Callback<List<QUESTION>>() {
            @Override
            public void onResponse(@NotNull Call<List<QUESTION>> call, @NotNull Response<List<QUESTION>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    paper1View.onReceiveQuestion(response.body());
                else
                    paper1View.onErrorLoadind("Error when get question");
                paper1View.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<QUESTION>> call, @NotNull Throwable t) {
                paper1View.onErrorLoadind(t.getLocalizedMessage());
                paper1View.HideLoadding();
            }
        });
    }

    void getQuestionByChapterId(int tutorialId){
        paper1View.showLoading();
        Call<List<QUESTION>> call = apiInterface.listQuestionTutorial(tutorialId);
        call.enqueue(new Callback<List<QUESTION>>() {
            @Override
            public void onResponse(@NotNull Call<List<QUESTION>> call, @NotNull Response<List<QUESTION>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    paper1View.onReceiveQuestion(response.body());
                else
                    paper1View.onErrorLoadind("Error when get question");
                paper1View.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<QUESTION>> call, @NotNull Throwable t) {
                paper1View.onErrorLoadind(t.getLocalizedMessage());
                paper1View.HideLoadding();
            }
        });
    }

    void getAnswer(int questId){
        paper1View.showLoading();
        Call<List<ANWSER>> call = apiInterface.listAnswer(questId);
        call.enqueue(new Callback<List<ANWSER>>() {
            @Override
            public void onResponse(@NotNull Call<List<ANWSER>> call, @NotNull Response<List<ANWSER>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    paper1View.onReceiveAnwser(response.body());
                else
                    paper1View.onErrorLoadind("Error when get Answer");
                paper1View.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<ANWSER>> call, @NotNull Throwable t) {
                paper1View.onErrorLoadind(t.getLocalizedMessage());
                paper1View.HideLoadding();
            }
        });
    }
}
