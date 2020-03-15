package com.MIAG.miaggce.api;


import com.MIAG.miaggce.models.ANWSER;
import com.MIAG.miaggce.models.CHAPTER;
import com.MIAG.miaggce.models.COMPETITIVE;
import com.MIAG.miaggce.models.EXAM;
import com.MIAG.miaggce.models.FILE;
import com.MIAG.miaggce.models.PAPER1;
import com.MIAG.miaggce.models.PAPER2;
import com.MIAG.miaggce.models.PAPER3;
import com.MIAG.miaggce.models.QUESTION;
import com.MIAG.miaggce.models.REQUIEREMENT;
import com.MIAG.miaggce.models.RESPONSE;
import com.MIAG.miaggce.models.STAFFMEMBER;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;
import com.MIAG.miaggce.models.TUTORIAL;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("EXAM/LIST")
    Call<List<EXAM>> listExams(
    );

    @GET("COMPETITIVE/LIST")
    Call<List<COMPETITIVE>> listCompetitive(
    );

    @GET("SUBJECT/LIST")
    Call<List<SUBJECT>> listSubject(
    );

    @GET("PAPERONE/LIST")
    Call<List<PAPER1>> listPaper1(
            @Query("subjectid") int SJ_ID
    );

    @GET("PAPERTWO/LIST")
    Call<List<PAPER2>> listPaper2(
            @Query("subjectid") int SJ_ID
    );

    @GET("PAPERTHREE/LIST")
    Call<List<PAPER3>> listPaper3(
            @Query("subjectid") int SJ_ID
    );

    @GET("CHAPTER/GET")
    Call<List<CHAPTER>> listChapter(
            @Query("competitiveid") int COMP_ID
    );

    @GET("REQUIEREMENT/GET")
    Call<List<REQUIEREMENT>> getRequierement(
            @Query("competitiveid") int SJ_ID
    );

    @GET("FILE/LIST")
    Call<List<FILE>> listImage();

    @GET("QUESTION/PAPERONELIST")
    Call<List<QUESTION>> listQuestionPaper1(
            @Query("paperoneid") int PAPER1_ID
    );

    @GET("TUTORIAL/GET")
    Call<List<TUTORIAL>> getTutorial(
            @Query("chapterid") int CHAPTER_ID,
            @Query("competitiveid") int COMP_ID
    );

    @GET("QUESTION/TUTORIALLIST")
    Call<List<QUESTION>> listQuestionTutorial(
            @Query("tutorialid") int TUT_ID
    );

    @GET("ANWSER/GET")
    Call<List<ANWSER>> listAnswer(
            @Query("questionid") int QUEST_ID
    );

    @GET("STAFFMEMBER/LIST")
    Call<List<STAFFMEMBER>> listStaffMember(
    );

    @FormUrlEncoded
    @POST("STUDENT/ADD")
    Call<RESPONSE> addStudent(
            @Field("name") String STD_NAME,
            @Field("number") String STD_NUMBER,
            @Field("password") String STD_PASSWORD,
            @Field("email") String STD_EMAIL,
            @Field("tel_parent1") String STD_PARENT1,
            @Field("tel_parent2") String STD_PARENT2
    );

    @FormUrlEncoded
    @POST("STUDENT/update")
    Call<RESPONSE> updateStudent(
            @Field("id") int STD_ID,
            @Field("name") String STD_NAME,
            @Field("number") String STD_NUMBER,
            @Field("password") String STD_PASSWORD,
            @Field("email") String STD_EMAIL,
            @Field("tel_parent1") String STD_PARENT1,
            @Field("tel_parent2") String STD_PARENT2
    );

    @FormUrlEncoded
    @POST("STUDENT/CONNECT")
    Call<RESPONSE> connectStudent(
            @Field("username") String USERNAME,
            @Field("password") String PASSWORD
    );

    @FormUrlEncoded
    @POST("STUDENT/REGISTER")
    Call<RESPONSE> registerStudent(
            @Field("id") int ID,
            @Field("REGISTER_CODE") String REGISTER_CODE
    );
}
