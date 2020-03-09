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

    @GET("exam/list")
    Call<List<EXAM>> listExams(
    );

    @GET("competitive/list")
    Call<List<COMPETITIVE>> listCompetitive(
    );

    @GET("subject/get")
    Call<List<SUBJECT>> getSubject(
            @Query("examid") int EXAM_ID,
            @Query("sj_name") String SJ_NAME,
            @Query("sj_date") String SJ_DATE
    );

    @GET("paper1/list")
    Call<List<PAPER1>> listPaper1(
            @Query("sj_id") int SJ_ID
    );

    @GET("paper2/list")
    Call<List<PAPER2>> listPaper2(
            @Query("sj_id") int SJ_ID
    );

    @GET("paper3/list")
    Call<List<PAPER3>> listPaper3(
            @Query("sj_id") int SJ_ID
    );

    @GET("chapter/list")
    Call<List<CHAPTER>> listChapter(
            @Query("comp_id") int COMP_ID
    );

    @GET("requierement/get")
    Call<List<REQUIEREMENT>> getRequierement(
            @Query("SJ_ID") int SJ_ID
    );

    @GET("file/get")
    Call<List<FILE>> getFile(
            @Query("FILE_ID") int FILE_ID
    );

    @GET("question/list")
    Call<List<QUESTION>> listQuestion(
            @Query("paper1_id") int PAPER1_ID
    );

    @GET("answer/list")
    Call<List<ANWSER>> listAnswer(
            @Query("quest_id") int QUEST_ID
    );

    @GET("question/list2")
    Call<List<QUESTION>> listQuestionComp(
            @Query("CHAPTER_ID") int CHAPTER_ID,
            @Query("COMP_ID") int COMP_ID
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
            @Field("name") String SJ_NSTD_NAMEAME,
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
