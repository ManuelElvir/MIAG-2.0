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

    @GET("PAPERONE/list")
    Call<List<PAPER1>> listPaper1(
            @Query("subjectid") int SJ_ID
    );

    @GET("PAPERTWO/list")
    Call<List<PAPER2>> listPaper2(
            @Query("subjectid") int SJ_ID
    );

    @GET("PAPERTREE/list")
    Call<List<PAPER3>> listPaper3(
            @Query("subjectid") int SJ_ID
    );

    @GET("CHAPTER/LIST")
    Call<List<CHAPTER>> listChapter(
            @Query("Competitiveid") int COMP_ID
    );

    @GET("REQUIEREMENT/GET")
    Call<List<REQUIEREMENT>> getRequierement(
            @Query("Competitiveid") int SJ_ID
    );

    @GET("FILE/GET")
    Call<List<FILE>> getFile(
            @Query("Fileid") int FILE_ID
    );

    @GET("QUESTION/PAPERONELIST")
    Call<List<QUESTION>> listQuestionPaper1(
            @Query("Paperoneid") int PAPER1_ID
    );

    @GET("QUESTION/PAPERTWOLIST")
    Call<List<QUESTION>> listQuestionPaper2(
            @Query("Papertwoid") int PAPER2_ID
    );

    @GET("QUESTION/PAPERTREELIST")
    Call<List<QUESTION>> listQuestionPaper3(
            @Query("Papertreeid") int PAPER3_ID
    );

    @GET("QUESTION/TUTORIALIST")
    Call<List<QUESTION>> listQuestionComp(
            @Query("tutorialid") int TUTORIAL_ID
    );

    @GET("ANSWER/GET")
    Call<List<ANWSER>> listAnswer(
            @Query("question_id") int QUEST_ID
    );

    @GET("subject_correction/get")
    Call<List<SUBJECT_CORRECTION>> getSubjectCorrection(
            @Query("PAPER_ID") int PAPER_ID
    );

    @GET("staff_member/list")
    Call<List<STAFFMEMBER>> listStaffMember(
    );

    @GET("image/list")
    Call<List<FILE>> listImage(
            @Query("FILE_TYPE") String FILE_TYPE // file_type always image
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
    @POST("student/register")
    Call<RESPONSE> registerStudent(
            @Field("id") int ID,
            @Field("REGISTER_CODE") String REGISTER_CODE
    );
}
