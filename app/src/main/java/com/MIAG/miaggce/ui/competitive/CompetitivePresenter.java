package com.MIAG.miaggce.ui.competitive;

import android.util.Log;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.REQUIEREMENT;
import com.MIAG.miaggce.models.TUTORIAL;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CompetitivePresenter {
    private CompetitiveView competitiveView;
    private ApiInterface apiInterface;

    CompetitivePresenter(CompetitiveView competitiveView, String userKey) {
        this.competitiveView = competitiveView;
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
    }

    void getChapter(int COMP_ID){
        competitiveView.showLoading();
        Call<List<CHAPTER>> call = apiInterface.listChapter(COMP_ID);
        call.enqueue(new Callback<List<CHAPTER>>() {
            @Override
            public void onResponse(@NotNull Call<List<CHAPTER>> call, @NotNull Response<List<CHAPTER>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    competitiveView.onReceiveChapter(response.body());
                else
                    competitiveView.onErrorLoadind("Error when get Chapter");
                competitiveView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<CHAPTER>> call, @NotNull Throwable t) {
                competitiveView.onErrorLoadind("CHAPTER:"+t.getLocalizedMessage());
                Log.e("CHAPTER:",t.getMessage(),t);
                competitiveView.HideLoadding();
            }
        });
    }

    void getTutorial(int COMP_ID){
        competitiveView.showLoading();
        Call<List<TUTORIAL>> call = apiInterface.getTutorial(COMP_ID);
        call.enqueue(new Callback<List<TUTORIAL>>() {
            @Override
            public void onResponse(@NotNull Call<List<TUTORIAL>> call, @NotNull Response<List<TUTORIAL>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    competitiveView.onReceiveTutorial(response.body());
                else
                    competitiveView.onErrorLoadind("Error when get Tutorials");
                competitiveView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<TUTORIAL>> call, @NotNull Throwable t) {
                competitiveView.onErrorLoadind("Tutorials :"+t.getLocalizedMessage());
                Log.e("Tutorials :",t.getMessage(),t);
                competitiveView.HideLoadding();
            }
        });
    }

    void getQuestions(final int tutorial_id) {
        competitiveView.showLoading();
        Call<List<QUESTION>> call = apiInterface.listQuestionTutorial(tutorial_id);
        call.enqueue(new Callback<List<QUESTION>>() {
            @Override
            public void onResponse(@NotNull Call<List<QUESTION>> call, @NotNull Response<List<QUESTION>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    competitiveView.onReceiveQuestion(response.body(),tutorial_id);
                else
                    competitiveView.onErrorLoadind("Error when get Chapter Question");
                competitiveView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<QUESTION>> call, @NotNull Throwable t) {
                competitiveView.onErrorLoadind("UESTIONS:"+t.getLocalizedMessage());
                Log.e("QUESTIONS:",t.getMessage(),t);
                competitiveView.HideLoadding();
            }
        });
    }

    void getAnswers(final int QUEST_ID) {
        competitiveView.showLoading();
        Call<List<ANWSER>> call = apiInterface.listAnswer(QUEST_ID);
        call.enqueue(new Callback<List<ANWSER>>() {
            @Override
            public void onResponse(@NotNull Call<List<ANWSER>> call, @NotNull Response<List<ANWSER>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    competitiveView.onReceiveAnwser(response.body(), QUEST_ID);
                else
                    competitiveView.onErrorLoadind("Error when get Question Answer");
                competitiveView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<ANWSER>> call, @NotNull Throwable t) {
                competitiveView.onErrorLoadind("ANSWER:"+t.getLocalizedMessage());
                Log.e("ANSWER:",t.getMessage(),t);
                competitiveView.HideLoadding();
            }
        });
    }

    void getRequierement(int compId) {
        competitiveView.showLoading();
        Call<List<REQUIEREMENT>> call = apiInterface.getRequierement(compId);
        call.enqueue(new Callback<List<REQUIEREMENT>>() {
            @Override
            public void onResponse(@NotNull Call<List<REQUIEREMENT>> call, @NotNull Response<List<REQUIEREMENT>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    competitiveView.onReceiveRequierement(response.body());
                else
                    competitiveView.onErrorLoadind("Error when get Requierement");
                competitiveView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<REQUIEREMENT>> call, @NotNull Throwable t) {
                competitiveView.onErrorLoadind("Requierement :"+t.getLocalizedMessage());
                Log.e("Requierement :",t.getMessage(),t);
                competitiveView.HideLoadding();
            }
        });
    }
}
