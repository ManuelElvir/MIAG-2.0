package com.MyMentor.competitive.api;


import com.MyMentor.competitive.models.ANWSER_RES;
import com.MyMentor.competitive.models.BLOC_NOTE_RES;
import com.MyMentor.competitive.models.CHAPTER;
import com.MyMentor.competitive.models.CHAPTER_RES;
import com.MyMentor.competitive.models.COMPETITIVE_RES;
import com.MyMentor.competitive.models.COMPETITIVE_TEST_RES;
import com.MyMentor.competitive.models.CONTENT_RES;
import com.MyMentor.competitive.models.FILE;
import com.MyMentor.competitive.models.FILE_RES;
import com.MyMentor.competitive.models.NOTE_RES;
import com.MyMentor.competitive.models.PAPER1;
import com.MyMentor.competitive.models.PAPER2;
import com.MyMentor.competitive.models.PAPER3;
import com.MyMentor.competitive.models.PAST_QUESTIONS_RES;
import com.MyMentor.competitive.models.QCM_RES;
import com.MyMentor.competitive.models.QUESTION;
import com.MyMentor.competitive.models.QUESTION_ANWSER_RES;
import com.MyMentor.competitive.models.QUESTION_RES;
import com.MyMentor.competitive.models.REQUIEREMENT;
import com.MyMentor.competitive.models.REQUIEREMENT_RES;
import com.MyMentor.competitive.models.RESPONSE;
import com.MyMentor.competitive.models.STAFFMEMBER;
import com.MyMentor.competitive.models.STAFFMEMBER_RES;
import com.MyMentor.competitive.models.SUBJECT;
import com.MyMentor.competitive.models.SUBJECT_CORRECTION;
import com.MyMentor.competitive.models.SUBJECT_RES;
import com.MyMentor.competitive.models.TEST_RES;
import com.MyMentor.competitive.models.TUTORIAL;
import com.MyMentor.competitive.models.TUTORIAL_QCM_RES;
import com.MyMentor.competitive.models.TUTORIAL_RES;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("COMPETITIVE/get")
    Call<COMPETITIVE_RES> listCompetitive(
            @Query("last_id") int last_id
    );

    @GET("ANWSER/get")
    Call<ANWSER_RES> listAnswer(
            @Query("last_id") int last_id
    );

    @GET("BLOC_NOTE/get")
    Call<BLOC_NOTE_RES> listBlocNote(
            @Query("last_id") int last_id
    );

    @GET("SUBJECT/get")
    Call<SUBJECT_RES> listSubject(
            @Query("last_id") int last_id
    );

    @GET("CHAPTER/get")
    Call<CHAPTER_RES> listChapter(
            @Query("last_id") int last_id
    );

    @GET("COMPETITIVE_TEST/get")
    Call<COMPETITIVE_TEST_RES> listCompetitiveTest(
            @Query("last_id") int last_id
    );

    @GET("CONTENT/get")
    Call<CONTENT_RES> listContent(
            @Query("last_id") int last_id
    );

    @GET("FILES/get")
    Call<FILE_RES> listFile(
            @Query("last_id") int last_id
    );

    @GET("NOTE/get")
    Call<NOTE_RES> listNote(
            @Query("last_id") int last_id
    );

    @GET("PAST_QUESTIONS/get")
    Call<PAST_QUESTIONS_RES> listPastQuestions(
            @Query("last_id") int last_id
    );

    @GET("QCM/get")
    Call<QCM_RES> listQCM(
            @Query("last_id") int last_id
    );

    @GET("QUESTION/get")
    Call<QUESTION_RES> listQuestion(
            @Query("last_id") int last_id
    );

    @GET("QUESTION_ANWSER/get")
    Call<QUESTION_ANWSER_RES> listQuestionAnswer(
            @Query("last_id") int last_id
    );

    @GET("REQUIEREMENT/get")
    Call<REQUIEREMENT_RES> listRequierement(
            @Query("last_id") int last_id
    );

    @GET("STAFFMEMBER/get")
    Call<STAFFMEMBER_RES> listStaffMember(
    );

    @GET("TEST/get")
    Call<TEST_RES> listTest(
            @Query("last_id") int last_id
    );

    @GET("TUTORIAL/get")
    Call<TUTORIAL_RES> listTutorial(
            @Query("last_id") int last_id
    );

    @GET("TUTORIAL_QCM/get")
    Call<TUTORIAL_QCM_RES> listTutorialQcm(
            @Query("last_id") int last_id
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
    @POST("STUDENT/UPD")
    Call<RESPONSE> updateStudent(
            @Field("std_id") int STD_ID,
            @Field("name") String STD_NAME,
            @Field("number") String STD_NUMBER,
            @Field("password") String STD_PASSWORD,
            @Field("email") String STD_EMAIL,
            @Field("parent1") String STD_PARENT1,
            @Field("parent2") String STD_PARENT2,
            @Field("state") int STATE
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
            @Field("studentid") int ID,
            @Field("registration_key") String REGISTER_CODE
    );
}
