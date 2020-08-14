package com.MyMentor.competitive.ui.downloadActivity;

import android.util.Log;

import com.MyMentor.competitive.api.ApiClient;
import com.MyMentor.competitive.api.ApiInterface;
import com.MyMentor.competitive.models.ANWSER_RES;
import com.MyMentor.competitive.models.CHAPTER_RES;
import com.MyMentor.competitive.models.COMPETITIVE_RES;
import com.MyMentor.competitive.models.CONTENT_RES;
import com.MyMentor.competitive.models.FILE_RES;
import com.MyMentor.competitive.models.NOTE_RES;
import com.MyMentor.competitive.models.PAST_QUESTIONS_RES;
import com.MyMentor.competitive.models.QCM_RES;
import com.MyMentor.competitive.models.QUESTION_ANWSER_RES;
import com.MyMentor.competitive.models.QUESTION_RES;
import com.MyMentor.competitive.models.REQUIEREMENT_RES;
import com.MyMentor.competitive.models.STAFFMEMBER_RES;
import com.MyMentor.competitive.models.SUBJECT_RES;
import com.MyMentor.competitive.models.TUTORIAL_QCM_RES;
import com.MyMentor.competitive.models.TUTORIAL_RES;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
 class DownloaderPresenter {

    private ApiInterface apiInterface;
    private DownloaderInterface downloaderInterface;

    DownloaderPresenter(String userKey, DownloaderInterface downloaderInterface) {
        apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
        this.downloaderInterface = downloaderInterface;
    }

    void downloadCompetitives(int lastCompId){
        Call<COMPETITIVE_RES> call = apiInterface.listCompetitive(lastCompId);
        call.enqueue(new Callback<COMPETITIVE_RES>() {
            @Override
            public void onResponse(@NotNull Call<COMPETITIVE_RES> call, @NotNull Response<COMPETITIVE_RES> response) {
                if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                    downloaderInterface.onReceiveCompetitive(response.body());
                }
                else{
                    downloaderInterface.onErrorLoadind("Error when get Competitive");
                }
            }

            @Override
            public void onFailure(@NotNull Call<COMPETITIVE_RES> call, @NotNull Throwable t) {
                downloaderInterface.onErrorLoadind("COMPETITIVE: "+t.getLocalizedMessage());
                Log.e("COMPETITIVE:",t.getMessage(),t);
            }
        });
    }

    void downloadSubjects(int lastSubjectId) {
        Call<SUBJECT_RES> call = apiInterface.listSubject(lastSubjectId);
        call.enqueue(new Callback<SUBJECT_RES>() {
            @Override
            public void onResponse(@NotNull Call<SUBJECT_RES> call, @NotNull Response<SUBJECT_RES> response) {
                if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                    downloaderInterface.onReceiveSubject(response.body());
                }
                else{
                    downloaderInterface.onErrorLoadind("Error when get Subjects");
                }
            }

            @Override
            public void onFailure(@NotNull Call<SUBJECT_RES> call, @NotNull Throwable t) {
                downloaderInterface.onErrorLoadind("SUBJECT: "+t.getLocalizedMessage());
                Log.e("SUBJECT:",t.getMessage(),t);
            }
        });
    }

    void downloadChapters(int lastChapterId) {
        Call<CHAPTER_RES> call = apiInterface.listChapter(lastChapterId);
        call.enqueue(new Callback<CHAPTER_RES>() {
            @Override
            public void onResponse(@NotNull Call<CHAPTER_RES> call, @NotNull Response<CHAPTER_RES> response) {
                if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                    downloaderInterface.onReceiveChapter(response.body());
                }
                else{
                    downloaderInterface.onErrorLoadind("Error when get chapters");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CHAPTER_RES> call, @NotNull Throwable t) {
                downloaderInterface.onErrorLoadind("CHAPTER: "+t.getLocalizedMessage());
                Log.e("CHAPTER:",t.getMessage(),t);
            }
        });
     }

     void downloadTutorials(int lastTutorialId) {
         Call<TUTORIAL_RES> call = apiInterface.listTutorial(lastTutorialId);
         call.enqueue(new Callback<TUTORIAL_RES>() {
             @Override
             public void onResponse(@NotNull Call<TUTORIAL_RES> call, @NotNull Response<TUTORIAL_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveTutorial(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get tutorials");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<TUTORIAL_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("TUTORIAL: "+t.getLocalizedMessage());
                 Log.e("TUTORIAL:",t.getMessage(),t);
             }
         });
     }

      void downloadNotes(int lastNoteId) {
         Call<NOTE_RES> call = apiInterface.listNote(lastNoteId);
         call.enqueue(new Callback<NOTE_RES>() {
             @Override
             public void onResponse(@NotNull Call<NOTE_RES> call, @NotNull Response<NOTE_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveNote(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get notes");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<NOTE_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("NOTE: "+t.getLocalizedMessage());
                 Log.e("NOTE:",t.getMessage(),t);
             }
         });
     }

      void downloadPastQuestions(int lastPastQuestionId) {
          Call<PAST_QUESTIONS_RES> call = apiInterface.listPastQuestions(lastPastQuestionId);
          call.enqueue(new Callback<PAST_QUESTIONS_RES>() {
              @Override
              public void onResponse(@NotNull Call<PAST_QUESTIONS_RES> call, @NotNull Response<PAST_QUESTIONS_RES> response) {
                  if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                      downloaderInterface.onReceivePastQuestion(response.body());
                  }
                  else{
                      downloaderInterface.onErrorLoadind("Error when get past_question");
                  }
              }

              @Override
              public void onFailure(@NotNull Call<PAST_QUESTIONS_RES> call, @NotNull Throwable t) {
                  downloaderInterface.onErrorLoadind("PAST_QUESTIONS: "+t.getLocalizedMessage());
                  Log.e("PAST_QUESTIONS:",t.getMessage(),t);
              }
          });
     }

     void downloadQcms(int lastQcmId) {
         Call<QCM_RES> call = apiInterface.listQCM(lastQcmId);
         call.enqueue(new Callback<QCM_RES>() {
             @Override
             public void onResponse(@NotNull Call<QCM_RES> call, @NotNull Response<QCM_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveQCM(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get qcm");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<QCM_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("QCM: "+t.getLocalizedMessage());
                 Log.e("QCM:",t.getMessage(),t);
             }
         });
     }

      void downloadTutorialsQcm(int lastTutorialQcmId) {
          Call<TUTORIAL_QCM_RES> call = apiInterface.listTutorialQcm(lastTutorialQcmId);
          call.enqueue(new Callback<TUTORIAL_QCM_RES>() {
              @Override
              public void onResponse(@NotNull Call<TUTORIAL_QCM_RES> call, @NotNull Response<TUTORIAL_QCM_RES> response) {
                  if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                      downloaderInterface.onReceiveTutorialQcm(response.body());
                  }
                  else{
                      downloaderInterface.onErrorLoadind("Error when get tutorial_qcm");
                  }
              }

              @Override
              public void onFailure(@NotNull Call<TUTORIAL_QCM_RES> call, @NotNull Throwable t) {
                  downloaderInterface.onErrorLoadind("TUTORIAL_QCM: "+t.getLocalizedMessage());
                  Log.e("TUTORIAL_QCM:",t.getMessage(),t);
              }
          });
     }

     void downloadContents(int lastContentId) {
         Call<CONTENT_RES> call = apiInterface.listContent(lastContentId);
         call.enqueue(new Callback<CONTENT_RES>() {
             @Override
             public void onResponse(@NotNull Call<CONTENT_RES> call, @NotNull Response<CONTENT_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveContent(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get content");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<CONTENT_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("CONTENT: "+t.getLocalizedMessage());
                 Log.e("CONTENT:",t.getMessage(),t);
             }
         });
     }

     void downloadQuestions(int lastQuestionId) {
         Call<QUESTION_RES> call = apiInterface.listQuestion(lastQuestionId);
         call.enqueue(new Callback<QUESTION_RES>() {
             @Override
             public void onResponse(@NotNull Call<QUESTION_RES> call, @NotNull Response<QUESTION_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveQuestion(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get question");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<QUESTION_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("QUESTION: "+t.getLocalizedMessage());
                 Log.e("QUESTION:",t.getMessage(),t);
             }
         });
     }

     void downloadQuestionAnswers(int lastQuestionAnswerId) {
         Call<QUESTION_ANWSER_RES> call = apiInterface.listQuestionAnswer(lastQuestionAnswerId);
         call.enqueue(new Callback<QUESTION_ANWSER_RES>() {
             @Override
             public void onResponse(@NotNull Call<QUESTION_ANWSER_RES> call, @NotNull Response<QUESTION_ANWSER_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveQuestionAnswer(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get question_answer");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<QUESTION_ANWSER_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("QUESTION_ANWSER: "+t.getLocalizedMessage());
                 Log.e("QUESTION_ANWSER:",t.getMessage(),t);
             }
         });
     }

     void downloadAnswers(int lastAnswerId) {
         Call<ANWSER_RES> call = apiInterface.listAnswer(lastAnswerId);
         call.enqueue(new Callback<ANWSER_RES>() {
             @Override
             public void onResponse(@NotNull Call<ANWSER_RES> call, @NotNull Response<ANWSER_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveAnswer(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get answer");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<ANWSER_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("ANWSER: "+t.getLocalizedMessage());
                 Log.e("ANWSER:",t.getMessage(),t);
             }
         });
     }

     void downloadRequierements(int lastReqId) {
        Call<REQUIEREMENT_RES> call = apiInterface.listRequierement(lastReqId);
         call.enqueue(new Callback<REQUIEREMENT_RES>() {
             @Override
             public void onResponse(@NotNull Call<REQUIEREMENT_RES> call, @NotNull Response<REQUIEREMENT_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveRequierement(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get requierements");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<REQUIEREMENT_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("REQUIEREMENT: "+t.getLocalizedMessage());
                 Log.e("REQUIEREMENT:",t.getMessage(),t);
             }
         });
     }

     void downloadStaffMembers() {
         Call<STAFFMEMBER_RES> call = apiInterface.listStaffMember();
         call.enqueue(new Callback<STAFFMEMBER_RES>() {
             @Override
             public void onResponse(@NotNull Call<STAFFMEMBER_RES> call, @NotNull Response<STAFFMEMBER_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveStaffMember(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get staff_members");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<STAFFMEMBER_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("STAFFMEMBER: "+t.getLocalizedMessage());
                 Log.e("STAFFMEMBER:",t.getMessage(),t);
             }
         });
     }

     void downloadFiles(int lastFileId) {
         Call<FILE_RES> call = apiInterface.listFile(lastFileId);
         call.enqueue(new Callback<FILE_RES>() {
             @Override
             public void onResponse(@NotNull Call<FILE_RES> call, @NotNull Response<FILE_RES> response) {
                 if (response.isSuccessful() && response.body()!=null && response.body().isSuccess()){
                     downloaderInterface.onReceiveImages(response.body());
                 }
                 else{
                     downloaderInterface.onErrorLoadind("Error when get files");
                 }
             }

             @Override
             public void onFailure(@NotNull Call<FILE_RES> call, @NotNull Throwable t) {
                 downloaderInterface.onErrorLoadind("FILE: "+t.getLocalizedMessage());
                 Log.e("FILE:",t.getMessage(),t);
             }
         });
     }
 }
