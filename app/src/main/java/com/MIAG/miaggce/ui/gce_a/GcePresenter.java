package com.MIAG.miaggce.ui.gce_a;

import android.util.Log;

import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.EXAM;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;

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

    public void getPaper1(int SJ_ID) {
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
                gceView.onErrorLoadind("PAPER1:"+t.getLocalizedMessage());
                Log.e("PAPER1:",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getPaper2(int SJ_ID) {
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
                gceView.onErrorLoadind("PAPER2:"+t.getLocalizedMessage());
                Log.e("PAPER2:",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getPaper3(int SJ_ID) {
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
                gceView.onErrorLoadind("PAPER3:"+t.getLocalizedMessage());
                Log.e("PAPER3:",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getPaper2Correction(final int paper2Id){
        gceView.showLoading();
        Call<SUBJECT_CORRECTION> call = apiInterface.getPaper2Correction(paper2Id);
        call.enqueue(new Callback<SUBJECT_CORRECTION>() {
            @Override
            public void onResponse(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Response<SUBJECT_CORRECTION> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceivePaper2Correction(response.body());
                else
                    gceView.onErrorLoadind("Error when get Paper2 Correction");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Throwable t) {
                gceView.onErrorLoadind("PAPER2 Correction :"+t.getLocalizedMessage());
                Log.e("PAPER2 Correction :",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getPaper3Correction(final int paper3Id){
        gceView.showLoading();
        Call<SUBJECT_CORRECTION> call = apiInterface.getPaper3Correction(paper3Id);
        call.enqueue(new Callback<SUBJECT_CORRECTION>() {
            @Override
            public void onResponse(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Response<SUBJECT_CORRECTION> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceivePaper3Correction(response.body());
                else
                    gceView.onErrorLoadind("Error when get Paper3 Correction");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<SUBJECT_CORRECTION> call, @NotNull Throwable t) {
                gceView.onErrorLoadind("PAPER3 Correction :"+t.getLocalizedMessage());
                Log.e("PAPER3 Correction :",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getQuestions(final int PAPER1_ID) {
        gceView.showLoading();
        Call<List<QUESTION>> call = apiInterface.listQuestionPaper1(PAPER1_ID);
        call.enqueue(new Callback<List<QUESTION>>() {
            @Override
            public void onResponse(@NotNull Call<List<QUESTION>> call, @NotNull Response<List<QUESTION>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceiveQuestion(response.body(), PAPER1_ID);
                else
                    gceView.onErrorLoadind("Error when get Paper1 Question");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<QUESTION>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind("QUESTIONS:"+t.getLocalizedMessage());
                Log.e("QUESTIONS:",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }

    public void getAnswers(int QUEST_ID) {
        gceView.showLoading();
        Call<List<ANWSER>> call = apiInterface.listAnswer(QUEST_ID);
        call.enqueue(new Callback<List<ANWSER>>() {
            @Override
            public void onResponse(@NotNull Call<List<ANWSER>> call, @NotNull Response<List<ANWSER>> response) {
                if (response.isSuccessful() && response.body()!=null)
                    gceView.onReceiveAnwser(response.body());
                else
                    gceView.onErrorLoadind("Error when get Question Answer");
                gceView.HideLoadding();
            }

            @Override
            public void onFailure(@NotNull Call<List<ANWSER>> call, @NotNull Throwable t) {
                gceView.onErrorLoadind("ANSWER:"+t.getLocalizedMessage());
                Log.e("ANSWER:",t.getMessage(),t);
                gceView.HideLoadding();
            }
        });
    }
}
