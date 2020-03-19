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
import com.MIAG.miaggce.models.TUTORIAL;

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
            contentValue.put("EXAM_DATE_END",exams.get(i).getEXAM_DATE_END());
            database.insert(DatabaseHelper.EXAM, null, contentValue);
        }
    }

    /**
     * get collect of EXAM
     * @return exams Collection of EXAM
     */
    public int getExamByNameAndDate(String name, String date) {
        Cursor cursor = database.rawQuery("SELECT EXAM_ID FROM "+DatabaseHelper.EXAM+" WHERE EXAM_NAME LIKE %"+name+"% AND WHERE EXAM_DATE LIKE %"+date+"%", null);
        if (cursor != null) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }else{
            //if no exam found
            return 0;
        }
    }

    /**
     *  insert new list of Competitive in database
     * @param competitives COMPETITIVE
     */
    public void insertListCompetitive(List<COMPETITIVE> competitives) {
        database.delete(DatabaseHelper.COMPETITIVE, "1=1", null);
        for (int i=0; i<competitives.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("COMP_ID",competitives.get(i).getCOMP_ID());
            contentValue.put("COMP_NAME",competitives.get(i).getCOMP_NAME());
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
                if (cursor.getColumnCount()>1) {
                    COMPETITIVE competitive = new COMPETITIVE();
                    competitive.setCOMP_ID(cursor.getInt(0));
                    competitive.setCOMP_NAME(cursor.getString(1));
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
        database.delete(DatabaseHelper.SUBJECT, "1=1", null);
        for (int i=0; i<subjects.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("SJ_ID",subjects.get(i).getSJ_ID());
            contentValue.put("SJ_NAME",subjects.get(i).getSJ_NAME());
            database.insert(DatabaseHelper.SUBJECT, null, contentValue);
        }
    }

    /**
     *  get collect of SUBJECT
     * @return subjects Collection of SUBJECT object
     */
    public List<SUBJECT> fetchSubject() {
        List<SUBJECT> subjects;
        subjects = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); i++){
                if (cursor.getColumnCount()>1) {
                    SUBJECT subject = new SUBJECT();
                    subject.setSJ_ID(cursor.getInt(0));
                    subject.setSJ_NAME(cursor.getString(1));
                    subjects.add(subject);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return subjects;
    }

    /**
     *  get collect of SUBJECT
     * @return subjects  of SUBJECT object
     */
    public SUBJECT getSubjectById(int SJ_ID) {
        SUBJECT subject = new SUBJECT();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT + " WHERE SJ_ID = "+SJ_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            subject.setSJ_ID(cursor.getInt(0));
            subject.setSJ_NAME(cursor.getString(1));
            cursor.close();
        }
        return subject;
    }


    /**
     *  insert new list of CHAPTER in database
     * @param chapters CHAPTER
     */
    public void insertListChapter(List<CHAPTER> chapters) {
        for (int i=0; i<chapters.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE CHAP_ID = " + chapters.get(i).getCHAP_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("CHAP_ID",chapters.get(i).getCHAP_ID());
                contentValue.put("SJ_ID",chapters.get(i).getSJ_ID());
                contentValue.put("CHAP_NAME",chapters.get(i).getCHAP_NAME());
                contentValue.put("COMP_ID",chapters.get(i).getCOMP_ID());
                database.insert(DatabaseHelper.CHAPTER, null, contentValue);
            }
            else
                cursor.close();
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
                if (cursor.getColumnCount()>3) {
                    CHAPTER chapter = new CHAPTER();
                    chapter.setCHAP_ID(cursor.getInt(0));
                    chapter.setSJ_ID(cursor.getInt(1));
                    chapter.setCHAP_NAME(cursor.getString(2));
                    chapter.setCOMP_ID(cursor.getInt(3));
                    chapters.add(chapter);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return chapters;
    }

    /**
     *  insert new list of TUTORIAL in database
     * @param tutorials TUTORIAL
     */
    public void insertListTutorial(List<TUTORIAL> tutorials) {
        for (int i=0; i<tutorials.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TUTORIAL+" WHERE TUTO_ID = " + tutorials.get(i).getTUTO_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("TUTO_ID",tutorials.get(i).getTUTO_ID());
                contentValue.put("TUTO_NAME",tutorials.get(i).getTUTO_NAME());
                contentValue.put("CHAP_ID",tutorials.get(i).getCHAP_ID());
                contentValue.put("COMP_ID",tutorials.get(i).getCOMP_ID());
                database.insert(DatabaseHelper.TUTORIAL, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    /**
     *  get collect of TUTORIAL
     * @return tutorials Collection of TUTORIAL object
     */
    public List<TUTORIAL> getTutorialByCompAndChapter(int COMP_ID, int CHAP_ID) {
        List<TUTORIAL> tutorials;
        tutorials = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE COMP_ID = " + COMP_ID +" AND WHERE CHAP_ID = "+CHAP_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>3) {
                    TUTORIAL tutorial = new TUTORIAL();
                    tutorial.setTUTO_ID(cursor.getInt(0));
                    tutorial.setTUTO_NAME(cursor.getString(1));
                    tutorial.setCHAP_ID(cursor.getInt(2));
                    tutorial.setCOMP_ID(cursor.getInt(3));
                    tutorials.add(tutorial);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return tutorials;
    }

    /**
     *  get collect of TUTORIAL
     * @return tutorials  TUTORIAL object
     */
    public TUTORIAL getTutorialById(int TUTO_ID) {
        TUTORIAL tutorial = new TUTORIAL();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE TUTO_ID = " + TUTO_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            tutorial.setTUTO_ID(cursor.getInt(0));
            tutorial.setTUTO_NAME(cursor.getString(1));
            tutorial.setCHAP_ID(cursor.getInt(2));
            tutorial.setCOMP_ID(cursor.getInt(3));
            cursor.close();
        }
        return tutorial;
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
                if (cursor.getColumnCount()>3) {
                    CHAPTER chapter = new CHAPTER();
                    chapter.setCHAP_ID(cursor.getInt(0));
                    chapter.setSJ_ID(cursor.getInt(1));
                    chapter.setCHAP_NAME(cursor.getString(2));
                    chapter.setCOMP_ID(cursor.getInt(3));
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
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE PAPER1_ID = " + paper1s.get(i).getPAPER1_ID(), null);
            if (cursor.getCount()==0) {
                Log.e("INSERT PAPER 1", paper1s.get(i).toString());
                ContentValues contentValue = new ContentValues();
                contentValue.put("PAPER1_ID",paper1s.get(i).getPAPER1_ID());
                contentValue.put("QCM_ID",paper1s.get(i).getSJ_ID());
                contentValue.put("TEST_NAME",paper1s.get(i).getTEST_NAME());
                contentValue.put("TEST_CHRONO",paper1s.get(i).getTEST_CHRONO());
                contentValue.put("SJ_ID",paper1s.get(i).getEXAM_ID());
                database.insert(DatabaseHelper.PAPER1, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    /**
     *  get collect of PAPER1
     * @return paper1 Collection of PAPER1 object
     */
    public PAPER1 getPaper1BySubjectAndExam(int SJ_ID, int EXAM_ID) {
        Cursor cursor;
        PAPER1 paper1 = new PAPER1();
        if (EXAM_ID>0)
            cursor= database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE SJ_ID = "+SJ_ID+" AND WHERE EXAM_ID = "+EXAM_ID, null);
        else
            cursor= database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE SJ_ID = "+SJ_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            paper1.setPAPER1_ID(cursor.getInt(0));
            paper1.setSJ_ID(cursor.getInt(1));
            paper1.setTEST_NAME(cursor.getString(2));
            paper1.setTEST_CHRONO(cursor.getString(3));
            paper1.setEXAM_ID(cursor.getInt(4));
            cursor.close();
        }
        return paper1;
    }

    /**
     *  get collect of PAPER1
     * @return paper1 PAPER1 object
     */
    public PAPER1 getPaper1ById(int PAPER1_ID) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1+" WHERE PAPER1_ID = "+PAPER1_ID, null);
        PAPER1 paper1 = new PAPER1();
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount()>0) {
                paper1.setPAPER1_ID(cursor.getInt(0));
                paper1.setSJ_ID(cursor.getInt(1));
                paper1.setTEST_NAME(cursor.getString(2));
                paper1.setTEST_CHRONO(cursor.getString(3));
                paper1.setEXAM_ID(cursor.getInt(4));
            }
            cursor.close();
        }
        return paper1;
    }

    /**
     *  return list of all PAPER1 in database
     */
    public List<PAPER1> fetchPaper1(){
        List<PAPER1> paper1s = new ArrayList<>();
        Cursor cursor= database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER1, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i=0; i<cursor.getCount(); i++){
                PAPER1 paper1 = new PAPER1();
                paper1.setPAPER1_ID(cursor.getInt(0));
                paper1.setSJ_ID(cursor.getInt(1));
                paper1.setTEST_NAME(cursor.getString(2));
                paper1.setTEST_CHRONO(cursor.getString(3));
                paper1.setEXAM_ID(cursor.getInt(4));
                paper1s.add(paper1);
            }
            cursor.close();
        }
        return paper1s;
    }

    /**
     *  insert new list of PAPER2 in database
     * @param paper2s PAPER2
     */
    public void insertListPaper2(List<PAPER2> paper2s) {
        database.delete(DatabaseHelper.PAPER2,"1=1", null);
        for (int i=0; i<paper2s.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER2+" WHERE PAPER2_ID = " + paper2s.get(i).getPAPER2_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("PAPER2_ID",paper2s.get(i).getPAPER2_ID());
                contentValue.put("SJ_ID",paper2s.get(i).getSJ_ID());
                contentValue.put("TEST_NAME",paper2s.get(i).getTEST_NAME());
                contentValue.put("TEST_CHRONO",paper2s.get(i).getTEST_CHRONO());
                contentValue.put("TEST_CONTENT",paper2s.get(i).getTEST_CONTENT());
                contentValue.put("EXAM_ID",paper2s.get(i).getEXAM_ID());
                database.insert(DatabaseHelper.PAPER2, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    /**
     *  get PAPER2 by Paper Subject ID and EXAM ID
     * @return paper2s Collection of PAPER2 object
     */
    public PAPER2 getPaper2BySubjectAndExam(int SJ_ID, int EXAM_ID) {
        PAPER2 paper2 = new PAPER2();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER2+" WHERE SJ_ID = "+SJ_ID+" AND WHERE EXAM_ID = "+EXAM_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount()>0) {
                paper2.setPAPER2_ID(cursor.getInt(0));
                paper2.setSJ_ID(cursor.getInt(1));
                paper2.setTEST_NAME(cursor.getString(2));
                paper2.setTEST_CHRONO(cursor.getString(3));
                paper2.setTEST_CONTENT(cursor.getString(4));
                paper2.setEXAM_ID(cursor.getInt(5));
            }
            cursor.close();
        }
        return paper2;
    }

    /**
     *  get collection of PAPER2
     * @return paper2 PAPER2 object
     */
    public PAPER2 getPaper2ById(int PAPER2_ID) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER2+" WHERE PAPER2_ID = "+PAPER2_ID, null);
        PAPER2 paper2 = new PAPER2();
        if (cursor != null) {
            cursor.moveToFirst();
            paper2.setPAPER2_ID(cursor.getInt(0));
            paper2.setSJ_ID(cursor.getInt(1));
            paper2.setTEST_NAME(cursor.getString(2));
            paper2.setTEST_CHRONO(cursor.getString(3));
            paper2.setTEST_CONTENT(cursor.getString(4));
            paper2.setEXAM_ID(cursor.getInt(5));
            cursor.close();
        }
        return paper2;
    }

    /**
     *  insert new list of PAPER3 in database
     * @param paper3s PAPER3
     */
    public void insertListPaper3(List<PAPER3> paper3s) {
        database.delete(DatabaseHelper.PAPER3,"1=1", null);
        for (int i=0; i<paper3s.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER3+" WHERE PAPER3_ID = " + paper3s.get(i).getPAPER3_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("PAPER3_ID",paper3s.get(i).getPAPER3_ID());
                contentValue.put("SJ_ID",paper3s.get(i).getSJ_ID());
                contentValue.put("TEST_NAME",paper3s.get(i).getTEST_NAME());
                contentValue.put("TEST_CONTENT",paper3s.get(i).getTEST_CONTENT());
                contentValue.put("EXAM_ID",paper3s.get(i).getEXAM_ID());
                database.insert(DatabaseHelper.PAPER3, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    /**
     *  get PAPER3 from her SUBJECT_ID AND EXAM_ID
     * @return paper3s Collection of PAPER3 object
     */
    public PAPER3 getPaper3BySubjectAndExam(int SJ_ID, int EXAM_ID) {
        PAPER3 paper3 = new PAPER3();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER3+" WHERE SJ_ID = "+SJ_ID+" AND WHERE EXAM_ID = "+EXAM_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount()>0) {
                paper3.setPAPER3_ID(cursor.getInt(0));
                paper3.setSJ_ID(cursor.getInt(1));
                paper3.setTEST_NAME(cursor.getString(2));
                paper3.setTEST_CONTENT(cursor.getString(3));
                paper3.setEXAM_ID(cursor.getInt(4));
            }
            cursor.close();
        }
        return paper3;
    }

    /**
     *  get collection of PAPER3
     * @return paper3  PAPER3 object
     */
    public PAPER3 getPaper3ById(int PAPER3_ID) {
        PAPER3 paper3 = new PAPER3();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAPER3+" WHERE PAPER3_ID = "+PAPER3_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            paper3.setPAPER3_ID(cursor.getInt(0));
            paper3.setSJ_ID(cursor.getInt(1));
            paper3.setTEST_NAME(cursor.getString(2));
            paper3.setTEST_CONTENT(cursor.getString(3));
            paper3.setEXAM_ID(cursor.getInt(4));
            cursor.close();
        }
        return paper3;
    }

    /**
     *  insert new list of REQUIEREMENT in database
     * @param requierements REQUIEREMENT
     */
    public void insertListRequierement(List<REQUIEREMENT> requierements) {
        for (int i=0; i<requierements.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.REQUIEREMENT+" WHERE REQ_ID = "+requierements.get(i).getREQ_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("REQ_ID",requierements.get(i).getREQ_ID());
                contentValue.put("COMP_ID",requierements.get(i).getCOMP_ID());
                contentValue.put("REQ_NAME",requierements.get(i).getREQ_NAME());
                contentValue.put("REQ_FILE",requierements.get(i).getREQ_FILE());
                contentValue.put("REQ_CONTENT",requierements.get(i).getREQ_CONTENT());
                database.insert(DatabaseHelper.REQUIEREMENT, null, contentValue);
            }else if (cursor.getCount()<1){
                ContentValues contentValue = new ContentValues();
                contentValue.put("REQ_ID",requierements.get(i).getREQ_ID());
                contentValue.put("COMP_ID",requierements.get(i).getCOMP_ID());
                contentValue.put("REQ_NAME",requierements.get(i).getREQ_NAME());
                contentValue.put("REQ_FILE",requierements.get(i).getREQ_FILE());
                contentValue.put("REQ_CONTENT",requierements.get(i).getREQ_CONTENT());
                database.insert(DatabaseHelper.REQUIEREMENT, null, contentValue);
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
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.REQUIEREMENT+" WHERE COMP_ID = "+COMP_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>4) {
                    REQUIEREMENT requierement = new REQUIEREMENT();
                    requierement.setREQ_ID(cursor.getInt(0));
                    requierement.setCOMP_ID(cursor.getInt(1));
                    requierement.setREQ_NAME(cursor.getString(2));
                    requierement.setREQ_FILE(cursor.getString(3));
                    requierement.setREQ_CONTENT(cursor.getString(4));
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
        database.delete(DatabaseHelper.STAFFMEMBER, "1=1", null);
        for (int i=0; i<staffmembers.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("SM_NAME",staffmembers.get(i).getSM_NAME());
            contentValue.put("SM_FUNCTION",staffmembers.get(i).getSM_FUNCTION());
            contentValue.put("SM_NUMBER",staffmembers.get(i).getSM_NUMBER());
            contentValue.put("SM_IMAGE",staffmembers.get(i).getSM_IMAGE());
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
            for (int i=0; i<cursor.getCount(); i++){
                if (cursor.getColumnCount()>4) {
                    STAFFMEMBER staffmember = new STAFFMEMBER();
                    staffmember.setSM_NAME(cursor.getString(1));
                    staffmember.setSM_FUNCTION(cursor.getString(2));
                    staffmember.setSM_NUMBER(cursor.getString(3));
                    staffmember.setSM_IMAGE(cursor.getString(4));
                    staffmembers.add(staffmember);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return staffmembers;
    }

    /**
     *  get collect of SUBJECT_CORRECTION
     * @return subject_corrections Collection of SUBJECT_CORRECTION object
     */
    public SUBJECT_CORRECTION getSubjectCorrectionByPaper3Id(int SC_PAPER3_ID) {
        SUBJECT_CORRECTION subject_correction = new SUBJECT_CORRECTION();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_PAPER3_ID = "+SC_PAPER3_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            subject_correction.setSC_ID(cursor.getInt(0));
            subject_correction.setSC_CONTENT(cursor.getString(1));
            subject_correction.setSC_DATE(cursor.getString(2));
            subject_correction.setSC_PAPER1_ID(cursor.getInt(3));
            subject_correction.setSC_PAPER2_ID(cursor.getInt(4));
            cursor.close();
        }
        return subject_correction;
    }

    /**
     *  get collect of SUBJECT_CORRECTION
     * @return subject_corrections Collection of SUBJECT_CORRECTION object
     */
    public SUBJECT_CORRECTION getSubjectCorrectionByPaper2Id(int SC_PAPER2_ID) {
        SUBJECT_CORRECTION subject_correction = new SUBJECT_CORRECTION();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_PAPER2_ID = "+SC_PAPER2_ID, null);
        if (cursor != null) {
            cursor.moveToFirst();
            subject_correction.setSC_ID(cursor.getInt(0));
            subject_correction.setSC_CONTENT(cursor.getString(1));
            subject_correction.setSC_DATE(cursor.getString(2));
            subject_correction.setSC_PAPER1_ID(cursor.getInt(3));
            subject_correction.setSC_PAPER2_ID(cursor.getInt(4));
            cursor.close();
        }
        return subject_correction;
    }

    /**
     *  insert new list of SUBJECT_CORRECTION in database
     * @param correction SUBJECT_CORRECTION
     */
    public void insertListSubjectCorrection(SUBJECT_CORRECTION correction) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.SUBJECT_CORRECTION+" WHERE SC_ID = "+correction.getSC_ID(), null);
        if (cursor.getCount()==0) {
            ContentValues contentValue = new ContentValues();
            contentValue.put("SC_ID",correction.getSC_ID());
            contentValue.put("SC_CONTENT",correction.getSC_CONTENT());
            contentValue.put("SC_DATE",correction.getSC_DATE());
            contentValue.put("SC_PAPER1_ID",correction.getSC_PAPER1_ID());
            contentValue.put("SC_PAPER2_ID",correction.getSC_PAPER2_ID());
            database.insert(DatabaseHelper.SUBJECT_CORRECTION, null, contentValue);
        }else if (cursor.getCount()<1){
            ContentValues contentValue = new ContentValues();
            contentValue.put("SC_ID",correction.getSC_ID());
            contentValue.put("SC_CONTENT",correction.getSC_CONTENT());
            contentValue.put("SC_DATE",correction.getSC_DATE());
            contentValue.put("SC_PAPER1_ID",correction.getSC_PAPER1_ID());
            contentValue.put("SC_PAPER2_ID",correction.getSC_PAPER2_ID());
            database.insert(DatabaseHelper.SUBJECT_CORRECTION, null, contentValue);
            cursor.close();
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
                if (cursor.getColumnCount()>3) {
                    QUESTION question = new QUESTION();
                    question.setQUEST_ID(cursor.getInt(0));
                    question.setQUEST_LABEL(cursor.getString(1));
                    question.setPAPER1_ID(cursor.getInt(2));
                    question.setTUT_ID(cursor.getInt(3));
                    questions.add(question);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return questions;
    }

    /**
     *  get number of QUESTION for particular Paper1
     * @return int
     * @param PAPER1_ID int id of PAPER1 who have this QUESTION
     */
    public int getQuestionCountForPaper1(int PAPER1_ID) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE PAPER1_ID = "+PAPER1_ID, null);
        int size = 0;
        if (cursor != null) {
            size = cursor.getCount();
            cursor.close();
        }
        return size;
    }

    /**
     *  get collect of QUESTION
     * @return questions Collection of QUESTION object
     * @param tuto_id int id of TUTORIAL who have this QUESTION
     */
    public List<QUESTION> getQuestionByTutoId(int tuto_id) {
        List<QUESTION> questions;
        questions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE TUTO_ID = "+tuto_id, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>3) {
                    QUESTION question = new QUESTION();
                    question.setQUEST_ID(cursor.getInt(0));
                    question.setQUEST_LABEL(cursor.getString(1));
                    question.setPAPER1_ID(cursor.getInt(2));
                    question.setTUT_ID(cursor.getInt(3));
                    questions.add(question);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return questions;
    }

    /**
     *  get number of question for particular tutorial
     * @return int
     * @param tuto_id int id of TUTORIAL who have this QUESTION
     */
    public int getQuestionCountTuto(int tuto_id) {
        int size =0;
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE TUTO_ID = "+tuto_id, null);
        if (cursor != null) {
            size = cursor.getCount();
            cursor.close();
        }
        return size;
    }

    /**
     *  insert new list of QUESTION in database
     * @param questions QUESTION
     */
    public void insertListQuestion(List<QUESTION> questions, int paperId, int tuto_id) {
        for (int i=0; i<questions.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE QUEST_ID = " + questions.get(i).getQUEST_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("QUEST_ID",questions.get(i).getQUEST_ID());
                contentValue.put("QUEST_LABEL",questions.get(i).getQUEST_LABEL());
                contentValue.put("PAPER1_ID",paperId);
                contentValue.put("TUTO_ID",tuto_id);
                database.insert(DatabaseHelper.QUESTION, null, contentValue);
            }
            else
                cursor.close();
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
                if (cursor.getColumnCount()>3) {
                    ANWSER anwser = new ANWSER();
                    anwser.setANWS_ID(cursor.getInt(0));
                    anwser.setANWS_CONTENT(cursor.getString(1));
                    anwser.setANWS_STATE(cursor.getInt(2));
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
        database.delete(DatabaseHelper.ANWSER, "1=1", null);
        for (int i=0; i<anwsers.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE ANWS_ID = " + anwsers.get(i).getANWS_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("ANWS_ID",anwsers.get(i).getANWS_ID());
                contentValue.put("ANWS_CONTENT",anwsers.get(i).getANWS_CONTENT());
                contentValue.put("ANWS_STATE",anwsers.get(i).getANWS_STATE());
                contentValue.put("QUEST_ID",anwsers.get(i).getQUEST_ID());
                database.insert(DatabaseHelper.ANWSER, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    /**
     *  get collect of File
     * @return files Collection of FILE object
     */
    public List<FILE> listFileByType() {
        List<FILE> files;
        files = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.FILE, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i=0; i<cursor.getCount(); i++){
                FILE file = new FILE();
                file.setFILE_URL(cursor.getString(1));
                files.add(file);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return files;
    }

    /**
     *  insert new list of FILE in database
     * @param files FILE
     */
    public void insertListFile(List<FILE> files) {
        database.delete(DatabaseHelper.FILE, "1=1", null);
        for (int i=0; i<files.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("FILE_URL",files.get(i).getFILE_URL());
            database.insert(DatabaseHelper.FILE, null, contentValue);

        }
    }


}
