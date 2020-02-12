package com.MIAG.miaggce.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
import com.MIAG.miaggce.models.STAFFMEMBER;
import com.MIAG.miaggce.models.SUBJECT;
import com.MIAG.miaggce.models.SUBJECT_CORRECTION;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel Elvir
 * This class is use to manage at the SQLDataBase
 */
public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * insert new exams in database
     * @param exams collection of EXAM
     */
    public void insertListExam(List<EXAM> exams) {
        database.delete(DatabaseHelper.EXAM, "", null);
        for (int i=0; i<exams.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("EXAM_ID",exams.get(i).getEXAM_ID());
            contentValue.put("EXAM_NAME",exams.get(i).getEXAM_NAME());
            contentValue.put("EXAM_DATE_START",exams.get(i).getEXAM_DATE_START());
            contentValue.put("EXAM_DATE_END",exams.get(i).getEXAM_DATE_END());
            database.insert(DatabaseHelper.EXAM, null, contentValue);
        }
    }

    /**
     * get collect of EXAM
     * @return exams Collection of EXAM
     */
    public List<EXAM> fetchExam() {
        List<EXAM> exams;
        exams = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.EXAM, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                Log.d("FETCH EXAM","Position "+cursor.getPosition());
                if (cursor.getColumnCount()<3){
                    Log.d("FETCH EXAM NOT EMPTY","Position "+cursor.getPosition()+" ColumCount "+cursor.getColumnCount());
                    EXAM exam = new EXAM();
                    exam.setEXAM_ID(cursor.getInt(0));
                    exam.setEXAM_NAME(cursor.getString(1));
                    exam.setEXAM_DATE_START(cursor.getString(2));
                    exam.setEXAM_DATE_END(cursor.getString(3));
                    exams.add(exam);
                }

            }while(cursor.moveToNext());
            cursor.close();
        }
        return exams;
    }

    /**
     *  insert new list of Competitive in database
     * @param competitives COMPETITIVE
     */
    public void insertListCompetitive(List<COMPETITIVE> competitives) {
        database.delete(DatabaseHelper.COMPETITIVE, "", null);
        for (int i=0; i<competitives.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("COMP_ID",competitives.get(i).getCOMP_ID());
            contentValue.put("COMP_NAME",competitives.get(i).getCOMP_NAME());
            contentValue.put("COMP_DATE",competitives.get(i).getCOMP_DATE());
            database.insert(DatabaseHelper.COMPETITIVE, null, contentValue);
        }
    }

    /**
     *  get collect of COMPETITIVE
     * @return competitives Collection of COMPETITIVE object
     */
    public List<COMPETITIVE> fetchCompetitive() {
        List<COMPETITIVE> competitives;
        competitives = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.COMPETITIVE, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                Log.d("FETCH COMPETITIVE","Position "+cursor.getPosition());
                if (cursor.getColumnCount()<=2) {
                    Log.d("FETCH COMPP NOT EMPTY", "Position " + cursor.getPosition() + " ColumCount " + cursor.getColumnCount());
                    COMPETITIVE competitive = new COMPETITIVE();
                    competitive.setCOMP_ID(cursor.getInt(0));
                    competitive.setCOMP_NAME(cursor.getString(1));
                    competitive.setCOMP_DATE(cursor.getString(2));
                    competitives.add(competitive);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return competitives;
    }

    /**
     *  insert new list of SUBJECT in database
     * @param subjects SUBJECT
     */
    public void insertListSubject(List<SUBJECT> subjects) {
        for (int i=0; i<subjects.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT+" WHERE SJ_ID = "+subjects.get(i).getSJ_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("SJ_ID",subjects.get(i).getSJ_ID());
            contentValue.put("SJ_NAME",subjects.get(i).getSJ_NAME());
            contentValue.put("SJ_DATE",subjects.get(i).getSJ_DATE());
            contentValue.put("EXAM_ID",subjects.get(i).getEXAM_ID());
            if (cursor ==  null){//si l'élément n'exite pas déjà dans la base de donnée
                database.insert(DatabaseHelper.SUBJECT, null, contentValue);
            }else {
                database.update(DatabaseHelper.SUBJECT, contentValue, "SJ_ID = "+subjects.get(i).getSJ_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of SUBJECT
     * @return subjects Collection of COMPETITIVE object
     */
    public List<SUBJECT> fetchSubject() {
        List<SUBJECT> subjects;
        subjects = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    SUBJECT subject = new SUBJECT();
                    subject.setSJ_ID(cursor.getInt(0));
                    subject.setSJ_NAME(cursor.getString(1));
                    subject.setSJ_DATE(cursor.getString(2));
                    subject.setEXAM_ID(cursor.getInt(3));
                    subjects.add(subject);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return subjects;
    }

    /**
     *  get collect of SUBJECT
     * @return subjects Collection of COMPETITIVE object
     */
    public List<SUBJECT> getSubjectbyExamId(int EXAM_ID, String SJ_NAME, String SJ_DATE) {
        List<SUBJECT> subjects;
        subjects = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT+" WHERE EXAM_ID = "+EXAM_ID+" AND SJ_NAME LIKE '%"+SJ_NAME+"%' AND SJ_DATE LIKE '%"+SJ_DATE+"%'", null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    SUBJECT subject = new SUBJECT();
                    subject.setSJ_ID(cursor.getInt(0));
                    subject.setSJ_NAME(cursor.getString(1));
                    subject.setSJ_DATE(cursor.getString(2));
                    subject.setEXAM_ID(cursor.getInt(3));
                    subjects.add(subject);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return subjects;
    }

    /**
     *  insert new list of CHAPTER in database
     * @param chapters CHAPTER
     */
    public void insertListChapter(List<CHAPTER> chapters) {

        for (int i=0; i<chapters.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE CHAP_ID = "+chapters.get(i).getCHAP_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("CHAP_ID",chapters.get(i).getCHAP_ID());
            contentValue.put("SJ_ID",chapters.get(i).getSJ_ID());
            contentValue.put("CHAP_NAME",chapters.get(i).getCHAP_NAME());
            contentValue.put("CHAP_DATE",chapters.get(i).getCHAP_DATE());
            contentValue.put("COMP_ID",chapters.get(i).getCOMP_ID());
            if (cursor == null){
                database.insert(DatabaseHelper.CHAPTER, null, contentValue);
            }else {
                database.update(DatabaseHelper.CHAPTER, contentValue, "CHAP_ID = "+chapters.get(i).getCHAP_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of CHAPTER
     * @return chapters Collection of CHAPTER object
     */
    public List<CHAPTER> getChapterByCompId(int COMP_ID) {
        List<CHAPTER> chapters;
        chapters = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE COMP_ID = "+COMP_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    CHAPTER chapter = new CHAPTER();
                    chapter.setCHAP_ID(cursor.getInt(0));
                    chapter.setSJ_ID(cursor.getString(1));
                    chapter.setCHAP_NAME(cursor.getString(2));
                    chapter.setCHAP_DATE(cursor.getString(3));
                    chapter.setCOMP_ID(cursor.getInt(4));
                    chapters.add(chapter);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return chapters;
    }

    /**
     *  get collect of CHAPTER
     * @return chapters Collection of CHAPTER object
     */
    public List<CHAPTER> listChapter() {
        List<CHAPTER> chapters;
        chapters = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    CHAPTER chapter = new CHAPTER();
                    chapter.setCHAP_ID(cursor.getInt(0));
                    chapter.setSJ_ID(cursor.getString(1));
                    chapter.setCHAP_NAME(cursor.getString(2));
                    chapter.setCHAP_DATE(cursor.getString(3));
                    chapter.setCOMP_ID(cursor.getInt(4));
                    chapters.add(chapter);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return chapters;
    }

    /**
     *  insert new list of PAPER1 in database
     * @param paper1s PAPER1
     */
    public void insertListPaper1(List<PAPER1> paper1s) {
        for (int i=0; i<paper1s.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE PAPER1_ID = "+paper1s.get(i).getPAPER1_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("PAPER1_ID",paper1s.get(i).getPAPER1_ID());
            contentValue.put("QCM_ID",paper1s.get(i).getQCM_ID());
            contentValue.put("TEST_ID",paper1s.get(i).getTEST_ID());
            contentValue.put("TEST_NAME",paper1s.get(i).getTEST_NAME());
            contentValue.put("TEST_CHRONO",paper1s.get(i).getTEST_CHRONO());
            contentValue.put("TEST_DATE",paper1s.get(i).getTEST_DATE());
            contentValue.put("SJ_ID",paper1s.get(i).getSJ_ID());
            if (cursor==null){//enregistrement exist on data base
                database.insert(DatabaseHelper.PAPER1, null, contentValue);
            }else {
                database.update(DatabaseHelper.PAPER1, contentValue, "PAPER1_ID = "+paper1s.get(i).getPAPER1_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of PAPER1
     * @return paper1 Collection of PAPER1 object
     */
    public List<PAPER1> getPaper1BySubjectId(int SJ_ID) {
        List<PAPER1> paper1s;
        paper1s = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE SJ_ID = "+SJ_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    PAPER1 paper1 = new PAPER1();
                    paper1.setPAPER1_ID(cursor.getInt(0));
                    paper1.setQCM_ID(cursor.getInt(1));
                    paper1.setTEST_ID(cursor.getInt(2));
                    paper1.setTEST_NAME(cursor.getString(3));
                    paper1.setTEST_CHRONO(cursor.getString(4));
                    paper1.setTEST_DATE(cursor.getString(5));
                    paper1.setSJ_ID(cursor.getInt(6));
                    paper1s.add(paper1);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return paper1s;
    }

    /**
     *  insert new list of PAPER2 in database
     * @param paper2s PAPER2
     */
    public void insertListPaper2(List<PAPER2> paper2s) {
        for (int i=0; i<paper2s.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER2+" WHERE PAPER2_ID = "+paper2s.get(i).getPAPER2_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("PAPER2_ID",paper2s.get(i).getPAPER2_ID());
            contentValue.put("TEST_ID",paper2s.get(i).getTEST_ID());
            contentValue.put("TEST_NAME",paper2s.get(i).getTEST_NAME());
            contentValue.put("TEST_CHRONO",paper2s.get(i).getTEST_CHRONO());
            contentValue.put("TEST_DATE",paper2s.get(i).getTEST_DATE());
            contentValue.put("TEST_CONTENT",paper2s.get(i).getTEST_CONTENT());
            contentValue.put("SJ_ID",paper2s.get(i).getSJ_ID());
            if (cursor==null){
                database.insert(DatabaseHelper.PAPER2, null, contentValue);
            }else {
                database.update(DatabaseHelper.PAPER2, contentValue, "PAPER2_ID = "+paper2s.get(i).getPAPER2_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collection of PAPER2
     * @return paper2s Collection of PAPER2 object
     */
    public List<PAPER2> getPaper2BySubjectId(int SJ_ID) {
        List<PAPER2> paper2s;
        paper2s = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER2+" WHERE SJ_ID = "+SJ_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    PAPER2 paper2 = new PAPER2();
                    paper2.setPAPER2_ID(cursor.getInt(0));
                    paper2.setTEST_ID(cursor.getInt(1));
                    paper2.setTEST_NAME(cursor.getString(2));
                    paper2.setTEST_CHRONO(cursor.getString(3));
                    paper2.setTEST_DATE(cursor.getString(4));
                    paper2.setTEST_CONTENT(cursor.getString(5));
                    paper2.setSJ_ID(cursor.getInt(6));
                    paper2s.add(paper2);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return paper2s;
    }

    /**
     *  insert new list of PAPER3 in database
     * @param paper3s PAPER3
     */
    public void insertListPaper3(List<PAPER3> paper3s) {
        for (int i=0; i<paper3s.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER3+" WHERE PAPER3_ID = "+paper3s.get(i).getPAPER3_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("PAPER3_ID",paper3s.get(i).getPAPER3_ID());
            contentValue.put("TEST_ID",paper3s.get(i).getTEST_ID());
            contentValue.put("TEST_NAME",paper3s.get(i).getTEST_NAME());
            contentValue.put("TEST_CHRONO",paper3s.get(i).getTEST_CHRONO());
            contentValue.put("TEST_DATE",paper3s.get(i).getTEST_DATE());
            contentValue.put("TEST_CONTENT",paper3s.get(i).getTEST_CONTENT());
            contentValue.put("SJ_ID",paper3s.get(i).getSJ_ID());
            if (cursor==null){
                database.insert(DatabaseHelper.PAPER3, null, contentValue);
            }else {
                database.update(DatabaseHelper.PAPER2, contentValue, "PAPER3_ID = "+paper3s.get(i).getPAPER3_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collection of PAPER3
     * @return paper3s Collection of PAPER3 object
     */
    public List<PAPER3> getPaper3BySubjectId(int SJ_ID) {
        List<PAPER3> paper3s;
        paper3s = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER3+" WHERE SJ_ID = "+SJ_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    PAPER3 paper3 = new PAPER3();
                    paper3.setPAPER3_ID(cursor.getInt(0));
                    paper3.setTEST_ID(cursor.getInt(1));
                    paper3.setTEST_NAME(cursor.getString(2));
                    paper3.setTEST_CHRONO(cursor.getString(3));
                    paper3.setTEST_DATE(cursor.getString(4));
                    paper3.setTEST_CONTENT(cursor.getString(5));
                    paper3.setSJ_ID(cursor.getInt(6));
                    paper3s.add(paper3);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return paper3s;
    }

    /**
     *  insert new list of REQUIEREMENT in database
     * @param requierements REQUIEREMENT
     */
    public void insertListRequierement(List<REQUIEREMENT> requierements) {
        for (int i=0; i<requierements.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.REQUIEREMENT+" WHERE REQ_ID = "+requierements.get(i).getREQ_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("REQ_ID",requierements.get(i).getREQ_ID());
            contentValue.put("COMP_ID",requierements.get(i).getCOMP_ID());
            contentValue.put("REQ_NAME",requierements.get(i).getREQ_NAME());
            contentValue.put("REQ_FILE",requierements.get(i).getREQ_FILE());
            contentValue.put("REQ_CONTENT",requierements.get(i).getREQ_CONTENT());
            contentValue.put("REQ_DATE",requierements.get(i).getREQ_DATE());
            if (cursor==null){
                database.insert(DatabaseHelper.REQUIEREMENT, null, contentValue);
            }else {
                database.update(DatabaseHelper.REQUIEREMENT, contentValue, "REQ_ID = "+requierements.get(i).getREQ_ID(),null);
                cursor.close();
            }

        }
    }

    /**
     *  get collection of REQUIEREMENT
     * @return requierements Collection of REQUIEREMENT object
     */
    public List<REQUIEREMENT> getRequierementByCompId(int COMP_ID) {
        List<REQUIEREMENT> requierements;
        requierements = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.REQUIEREMENT+" WHERE SJ_ID = "+COMP_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    REQUIEREMENT requierement = new REQUIEREMENT();
                    requierement.setREQ_ID(cursor.getInt(0));
                    requierement.setCOMP_ID(cursor.getInt(1));
                    requierement.setREQ_NAME(cursor.getString(2));
                    requierement.setREQ_FILE(cursor.getString(3));
                    requierement.setREQ_CONTENT(cursor.getString(4));
                    requierement.setREQ_DATE(cursor.getString(5));
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return requierements;
    }

    /**
     *  insert new list of STAFFMEMBER in database
     * @param staffmembers STAFFMEMBER
     */
    public void insertListStaffMember(List<STAFFMEMBER> staffmembers) {
        database.delete(DatabaseHelper.STAFFMEMBER, "", null);
        for (int i=0; i<staffmembers.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("SM_ID",staffmembers.get(i).getSM_ID());
            contentValue.put("SM_NAME",staffmembers.get(i).getSM_NAME());
            contentValue.put("SM_FUNCTION",staffmembers.get(i).getSM_FUNCTION());
            contentValue.put("SM_NUMBER",staffmembers.get(i).getSM_NUMBER());
            contentValue.put("SM_DATE",staffmembers.get(i).getSM_DATE());
            database.insert(DatabaseHelper.STAFFMEMBER, null, contentValue);
        }
    }

    /**
     *  get collection of STAFFMEMBER
     * @return staffmembers Collection of STAFFMEMBER object
     */
    public List<STAFFMEMBER> listStaffMember() {
        List<STAFFMEMBER> staffmembers;
        staffmembers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.STAFFMEMBER, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    STAFFMEMBER staffmember = new STAFFMEMBER();
                    staffmember.setSM_ID(cursor.getInt(0));
                    staffmember.setSM_NAME(cursor.getString(1));
                    staffmember.setSM_FUNCTION(cursor.getString(2));
                    staffmember.setSM_NUMBER(cursor.getString(3));
                    staffmember.setSM_DATE(cursor.getString(4));
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return staffmembers;
    }

    /**
     *  get collect of SUBJECT_CORRECTION
     * @return subject_corrections Collection of SUBJECT_CORRECTION object
     */
    public List<SUBJECT_CORRECTION> getSubjectCorrectionByPaper1Id(int SC_PAPER1_ID) {
        List<SUBJECT_CORRECTION> subject_corrections;
        subject_corrections = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_PAPER1_ID = "+SC_PAPER1_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    SUBJECT_CORRECTION subject_correction = new SUBJECT_CORRECTION();
                    subject_correction.setSC_ID(cursor.getInt(0));
                    subject_correction.setSC_CONTENT(cursor.getString(1));
                    subject_correction.setSC_DATE(cursor.getString(2));
                    subject_correction.setSC_PAPER1_ID(cursor.getInt(3));
                    subject_correction.setSC_PAPER2_ID(cursor.getInt(4));
                    subject_corrections.add(subject_correction);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return subject_corrections;
    }

    /**
     *  get collect of SUBJECT_CORRECTION
     * @return subject_corrections Collection of SUBJECT_CORRECTION object
     */
    public List<SUBJECT_CORRECTION> getSubjectCorrectionByPaper2Id(int SC_PAPER2_ID) {
        List<SUBJECT_CORRECTION> subject_corrections;
        subject_corrections = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_PAPER2_ID = "+SC_PAPER2_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    SUBJECT_CORRECTION subject_correction = new SUBJECT_CORRECTION();
                    subject_correction.setSC_ID(cursor.getInt(0));
                    subject_correction.setSC_CONTENT(cursor.getString(1));
                    subject_correction.setSC_DATE(cursor.getString(2));
                    subject_correction.setSC_PAPER1_ID(cursor.getInt(3));
                    subject_correction.setSC_PAPER2_ID(cursor.getInt(4));
                    subject_corrections.add(subject_correction);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return subject_corrections;
    }

    /**
     *  insert new list of SUBJECT_CORRECTION in database
     * @param subject_corrections SUBJECT_CORRECTION
     */
    public void insertListSubjectCorrection(List<SUBJECT_CORRECTION> subject_corrections) {
        for (int i=0; i<subject_corrections.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_ID = "+subject_corrections.get(i).getSC_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("SC_ID",subject_corrections.get(i).getSC_ID());
            contentValue.put("SC_CONTENT",subject_corrections.get(i).getSC_CONTENT());
            contentValue.put("SC_DATE",subject_corrections.get(i).getSC_DATE());
            contentValue.put("SC_PAPER1_ID",subject_corrections.get(i).getSC_PAPER1_ID());
            contentValue.put("SC_PAPER2_ID",subject_corrections.get(i).getSC_PAPER2_ID());
            if (cursor==null){
                database.insert(DatabaseHelper.SUBJECT_CORRECTION, null, contentValue);
            }else {
                database.update(DatabaseHelper.SUBJECT_CORRECTION, contentValue, "SC_ID = "+subject_corrections.get(i).getSC_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of QUESTION
     * @return questions Collection of QUESTION object
     * @param PAPER1_ID int id of PAPER1 who have this QUESTION
     */
    public List<QUESTION> getQuestionByPaper1Id(int PAPER1_ID) {
        List<QUESTION> questions;
        questions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE PAPER1_ID = "+PAPER1_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    QUESTION question = new QUESTION();
                    question.setQUEST_ID(cursor.getInt(0));
                    question.setQUEST_LABEL(cursor.getString(1));
                    question.setQUEST_TYPE(cursor.getString(2));
                    question.setQUEST_DATE(cursor.getString(3));
                    question.setPAPER1_ID(cursor.getInt(4));
                    question.setCHAP_ID(cursor.getInt(5));
                    questions.add(question);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return questions;
    }

    /**
     *  get collect of QUESTION
     * @return questions Collection of QUESTION object
     * @param CHAP_ID int id of CHAPTER who have this QUESTION
     */
    public List<QUESTION> getQuestionByChapId(int CHAP_ID) {
        List<QUESTION> questions;
        questions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE CHAP_ID = "+CHAP_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    QUESTION question = new QUESTION();
                    question.setQUEST_ID(cursor.getInt(0));
                    question.setQUEST_LABEL(cursor.getString(1));
                    question.setQUEST_TYPE(cursor.getString(2));
                    question.setQUEST_DATE(cursor.getString(3));
                    question.setPAPER1_ID(cursor.getInt(4));
                    question.setCHAP_ID(cursor.getInt(5));
                    questions.add(question);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return questions;
    }

    /**
     *  insert new list of QUESTION in database
     * @param questions QUESTION
     */
    public void insertListQuestion(List<QUESTION> questions) {
        for (int i=0; i<questions.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE QUEST_ID = "+questions.get(i).getQUEST_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("QUEST_ID",questions.get(i).getQUEST_ID());
            contentValue.put("QUEST_LABEL",questions.get(i).getQUEST_LABEL());
            contentValue.put("QUEST_TYPE",questions.get(i).getQUEST_TYPE());
            contentValue.put("QUEST_DATE",questions.get(i).getQUEST_DATE());
            contentValue.put("PAPER1_ID",questions.get(i).getPAPER1_ID());
            contentValue.put("CHAP_ID",questions.get(i).getCHAP_ID());
            if(cursor==null){
                database.insert(DatabaseHelper.QUESTION, null, contentValue);
            }else {
                database.update(DatabaseHelper.SUBJECT_CORRECTION, contentValue, "QUEST_ID = "+questions.get(i).getQUEST_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of ANWSER
     * @return anwsers Collection of ANWSER object
     */
    public List<ANWSER> getAnwserByQuestionId(int QUEST_ID) {
        List<ANWSER> anwsers;
        anwsers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ANWSER+" WHERE QUEST_ID = "+QUEST_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    ANWSER anwser = new ANWSER();
                    anwser.setANWS_ID(cursor.getInt(0));
                    anwser.setANWS_CONTENT(cursor.getString(1));
                    anwser.setANWS_DATE(cursor.getString(2));
                    anwser.setQUEST_ID(cursor.getInt(3));
                    anwsers.add(anwser);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return anwsers;
    }

    /**
     *  insert new list of ANWSER in database
     * @param anwsers ANWSER
     */
    public void insertListAnwser(List<ANWSER> anwsers) {
        database.delete(DatabaseHelper.ANWSER, "", null);
        for (int i=0; i<anwsers.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ANWSER+" WHERE ANWS_ID = "+anwsers.get(i).getANWS_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("ANWS_ID",anwsers.get(i).getANWS_ID());
            contentValue.put("ANWS_CONTENT",anwsers.get(i).getANWS_CONTENT());
            contentValue.put("ANWS_DATE",anwsers.get(i).getANWS_DATE());
            contentValue.put("QUEST_ID",anwsers.get(i).getQUEST_ID());
            if (cursor == null)
                database.insert(DatabaseHelper.ANWSER, null, contentValue);
            else {
                database.update(DatabaseHelper.ANWSER, contentValue, "ANWS_ID = "+anwsers.get(i).getANWS_ID(),null);
                cursor.close();
            }
        }
    }

    /**
     *  get collect of File
     * @return files Collection of FILE object
     */
    public List<FILE> getFileById(int FILE_ID) {
        List<FILE> files;
        files = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.FILE+" WHERE FILE_ID = "+FILE_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    FILE file = new FILE();
                    file.setFILE_ID(cursor.getInt(0));
                    file.setFILE_NAME(cursor.getString(1));
                    file.setFILE_TYPE(cursor.getString(2));
                    file.setFILE_FORMAT(cursor.getString(3));
                    file.setFILE_DATE(cursor.getString(4));
                    files.add(file);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return files;
    }

    /**
     *  get collect of File
     * @return files Collection of FILE object
     */
    public List<FILE> listFileByType(String FILE_TYPE) {
        List<FILE> files;
        files = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.FILE+" WHERE FILE_TYPE = "+FILE_TYPE, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()<=2) {
                    FILE file = new FILE();
                    file.setFILE_ID(cursor.getInt(0));
                    file.setFILE_NAME(cursor.getString(1));
                    file.setFILE_TYPE(cursor.getString(2));
                    file.setFILE_FORMAT(cursor.getString(3));
                    file.setFILE_DATE(cursor.getString(4));
                    files.add(file);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return files;
    }

    /**
     *  insert new list of FILE in database
     * @param files FILE
     */
    public void insertListFile(List<FILE> files) {
        database.delete(DatabaseHelper.FILE, "", null);
        for (int i=0; i<files.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.FILE+" WHERE FILE_ID = "+files.get(i).getFILE_ID(), null);
            ContentValues contentValue = new ContentValues();
            contentValue.put("FILE_ID",files.get(i).getFILE_ID());
            contentValue.put("FILE_NAME",files.get(i).getFILE_NAME());
            contentValue.put("FILE_TYPE",files.get(i).getFILE_TYPE());
            contentValue.put("FILE_FORMAT",files.get(i).getFILE_FORMAT());
            contentValue.put("FILE_DATE",files.get(i).getFILE_DATE());
            if (cursor == null)
                database.insert(DatabaseHelper.FILE, null, contentValue);
            else {
                database.update(DatabaseHelper.FILE, contentValue, "FILE_ID = "+files.get(i).getFILE_ID(),null);
                cursor.close();
            }
        }
    }
}
