package com.MyMentor.competitive.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.MyMentor.competitive.models.ANWSER;
import com.MyMentor.competitive.models.BLOC_NOTE;
import com.MyMentor.competitive.models.CHAPTER;
import com.MyMentor.competitive.models.COMPETITIVE;
import com.MyMentor.competitive.models.CONTENT;
import com.MyMentor.competitive.models.FILE;
import com.MyMentor.competitive.models.NOTE;
import com.MyMentor.competitive.models.PAST_QUESTIONS;
import com.MyMentor.competitive.models.QCM;
import com.MyMentor.competitive.models.QUESTION;
import com.MyMentor.competitive.models.QUESTION_ANWSER;
import com.MyMentor.competitive.models.REQUIEREMENT;
import com.MyMentor.competitive.models.STAFFMEMBER;
import com.MyMentor.competitive.models.SUBJECT;
import com.MyMentor.competitive.models.TUTORIAL;
import com.MyMentor.competitive.models.TUTORIAL_QCM;
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

    public int getLastCompId() {
        int id =0;

        Cursor cursor = database.rawQuery("SELECT COMP_ID FROM "+DatabaseHelper.COMPETITIVE+" ORDER BY COMP_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
                cursor.close();
        }
        return id;
    }

    public void insertListCompetitive(List<COMPETITIVE> competitives) {
        for (int i=0; i<competitives.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("COMP_ID",competitives.get(i).getCOMP_ID());
            contentValue.put("COMP_NAME",competitives.get(i).getCOMP_NAME());
            contentValue.put("COMP_DATE",competitives.get(i).getCOMP_DATE());

            //check if is replace or create
            Cursor cursor = database.rawQuery("SELECT COMP_ID FROM "+DatabaseHelper.COMPETITIVE+" WHERE COMP_ID = "+competitives.get(i).getCOMP_ID(), null);
            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.COMPETITIVE, contentValue, "COMP_ID="+competitives.get(i).getCOMP_ID(), null);
                else
                    database.insert(DatabaseHelper.COMPETITIVE, null, contentValue);
                cursor.close();
            }
            else
                database.insert(DatabaseHelper.COMPETITIVE, null, contentValue);
        }
    }

    public List<COMPETITIVE> fetchCompetitive() {
        List<COMPETITIVE> competitives;
        competitives = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.COMPETITIVE, null, null, null, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>2) {
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

    public int getLastSubjectId() {
        int id =0;

        Cursor cursor = database.rawQuery("SELECT SJ_ID FROM "+DatabaseHelper.SUBJECT+" ORDER BY SJ_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListSubject(List<SUBJECT> subjects) {
        for (int i=0; i<subjects.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("SJ_ID",subjects.get(i).getSJ_ID());
            contentValue.put("SJ_NAME",subjects.get(i).getSJ_NAME());
            contentValue.put("SJ_DATE",subjects.get(i).getSJ_DATE());

            //check if is replace or create
            Cursor cursor = database.rawQuery("SELECT SJ_ID FROM "+DatabaseHelper.SUBJECT+" WHERE SJ_ID = "+subjects.get(i).getSJ_ID(), null);
            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.SUBJECT, contentValue, "SJ_ID="+subjects.get(i).getSJ_ID(), null);
                else
                    database.insert(DatabaseHelper.SUBJECT, null, contentValue);
                cursor.close();
            }
            else
                database.insert(DatabaseHelper.SUBJECT, null, contentValue);
        }
    }

    public List<SUBJECT> getAllSubjectAvailableForComp(int comp_id) {
        List<SUBJECT> subjects = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT SJ_ID, SJ_NAME FROM subject"+
                " INNER JOIN chapter ON chapter.SJ_ID = subject.SJ_ID"+
                " INNER JOIN tutorial ON tutorial.CHAP_ID = chapter.CHAP_ID" +
                " WHERE tutorial.COMP_ID = " + comp_id, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>1) {
                    SUBJECT subject = new SUBJECT();
                    subject.setSJ_ID(cursor.getInt(0));
                    subject.setSJ_NAME(cursor.getString(1));
                    subjects.add(subject);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return subjects;
    }

    public int getLastChapterId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT CHAP_ID FROM "+DatabaseHelper.CHAPTER+" ORDER BY CHAP_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListChapter(List<CHAPTER> chapters) {
        for (int i=0; i<chapters.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("CHAP_ID",chapters.get(i).getCHAP_ID());
            contentValue.put("SJ_ID",chapters.get(i).getSJ_ID());
            contentValue.put("CHAP_NAME",chapters.get(i).getCHAP_NAME());
            contentValue.put("CHAP_DATE",chapters.get(i).getCHAP_DATE());

            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE CHAP_ID = " + chapters.get(i).getCHAP_ID(), null);
            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.CHAPTER, contentValue, "CHAP_ID=" + chapters.get(i).getCHAP_ID(), null);
                else
                    database.insert(DatabaseHelper.SUBJECT, null, contentValue);
                cursor.close();
            }
        }
    }

    public List<CHAPTER> getChapterBySubjectId(int sj_id) {
        List<CHAPTER> chapters = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.CHAPTER+" WHERE SJ_ID = " + sj_id, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>3) {
                    CHAPTER chapter = new CHAPTER();
                    chapter.setCHAP_ID(cursor.getInt(0));
                    chapter.setSJ_ID(cursor.getInt(1));
                    chapter.setCHAP_NAME(cursor.getString(2));
                    chapter.setCHAP_DATE(cursor.getString(3));
                    chapters.add(chapter);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }

        return chapters;
    }

    public int getLastTutorialId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT TUTO_ID FROM "+DatabaseHelper.TUTORIAL+" ORDER BY TUTO_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListTutorial(List<TUTORIAL> tutorials) {
        for (int i=0; i<tutorials.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("TUTO_ID",tutorials.get(i).getTUTO_ID());
            contentValue.put("CHAPT_ID",tutorials.get(i).getCHAP_ID());
            contentValue.put("COMP_ID",tutorials.get(i).getCOMP_ID());
            contentValue.put("TUTO_NAME",tutorials.get(i).getTUTO_NAME());
            contentValue.put("TUTO_DATE",tutorials.get(i).getTUT_DATE());
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TUTORIAL+" WHERE TUTO_ID = " + tutorials.get(i).getTUTO_ID(), null);

            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.TUTORIAL, contentValue, "TUTO_ID=" + tutorials.get(i).getTUTO_ID(), null);
                else
                    database.insert(DatabaseHelper.TUTORIAL, null, contentValue);
                cursor.close();
            }
        }
    }

    public int getLastNoteId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT NOTE_ID FROM "+DatabaseHelper.NOTE+" ORDER BY NOTE_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public NOTE getNoteById(int noteId) {
        NOTE note = new NOTE();
        Cursor cursor = database.rawQuery("SELECT NOTE_ID FROM "+DatabaseHelper.NOTE+" WHERE NOTE_ID = " + noteId, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                note.setNOTE_ID(cursor.getInt(0));
                note.setNOTE_CONTENT(cursor.getString(1));
                note.setNOTE_DATE(cursor.getString(2));
                note.setCHAP_ID(cursor.getInt(3));
            }
            cursor.close();
        }
        return note;
    }

    public int getNoteIdByChapId(int chap_id) {
        int noteId = 0;
        Cursor cursor = database.rawQuery("SELECT NOTE_ID FROM "+DatabaseHelper.NOTE+" WHERE CHAP_ID = "+chap_id+ " ORDER BY NOTE_ID DESC", null);
        if (cursor!=null){
            if (cursor.moveToFirst())
                noteId = cursor.getInt(0);
            cursor.close();
        }
        return noteId;
    }

    public void insertListNote(List<NOTE> notes) {
        for (int i=0; i<notes.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("NOTE_ID",notes.get(i).getNOTE_ID());
            contentValue.put("NOTE_CONTENT",notes.get(i).getNOTE_CONTENT());
            contentValue.put("NOTE_DATE",notes.get(i).getNOTE_DATE());
            contentValue.put("CHAP_ID",notes.get(i).getCHAP_ID());
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.NOTE+" WHERE NOTE_ID = " + notes.get(i).getNOTE_ID(), null);

            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.NOTE, contentValue, "NOTE_ID=" + notes.get(i).getNOTE_ID(), null);
                else
                    database.insert(DatabaseHelper.NOTE, null, contentValue);
                cursor.close();
            }
        }
    }

    public int getLastPastQuestionId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT PQ_ID FROM "+DatabaseHelper.PAST_QUESTION+" ORDER BY PQ_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListPastQuestion(List<PAST_QUESTIONS> past_questions) {
        for (int i=0; i<past_questions.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("PQ_ID",past_questions.get(i).getPQ_ID());
            contentValue.put("COMP_ID",past_questions.get(i).getCOMP_ID());
            contentValue.put("QCM_ID",past_questions.get(i).getQCM_ID());
            contentValue.put("PQ_TITLE",past_questions.get(i).getPQ_TITLE());
            contentValue.put("PQ_SESSION",past_questions.get(i).getPQ_SESSION());
            contentValue.put("PQ_TIMING",past_questions.get(i).getPQ_TIMING());
            contentValue.put("PQ_DATE",past_questions.get(i).getPQ_DATE());
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.PAST_QUESTION+" WHERE PQ_ID = " + past_questions.get(i).getPQ_ID(), null);

            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.PAST_QUESTION, contentValue, "PQ_ID=" + past_questions.get(i).getPQ_ID(), null);
                else
                    database.insert(DatabaseHelper.PAST_QUESTION, null, contentValue);
                cursor.close();
            }
        }
    }

    public List<PAST_QUESTIONS> getPastQuestionByCompIdAndYear(int comp_id, int year) {
        List<PAST_QUESTIONS> past_questions = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.PAST_QUESTION + " WHERE COMP_ID = " + comp_id + " AND PQ_SESSION =" + year , null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>5) {
                    PAST_QUESTIONS pastQuestion = new PAST_QUESTIONS();
                    pastQuestion.setPQ_ID(cursor.getInt(0));
                    pastQuestion.setCOMP_ID(cursor.getInt(1));
                    pastQuestion.setQCM_ID(cursor.getInt(2));
                    pastQuestion.setPQ_TITLE(cursor.getString(3));
                    pastQuestion.setPQ_SESSION(cursor.getInt(4));
                    pastQuestion.setPQ_TIMING(cursor.getString(5));
                    pastQuestion.setPQ_DATE(cursor.getString(6));
                    past_questions.add(pastQuestion);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return past_questions;
    }

    public String getPastQuestionTimeById(int pastQuestID) {
        String time = null;

        Cursor cursor = database.rawQuery("SELECT PQ_TIMING FROM "+DatabaseHelper.PAST_QUESTION+" WHERE PQ_ID = " + pastQuestID, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                time = cursor.getString(0);
            }
            cursor.close();
        }

        return time;
    }

    public List<Integer> getAllSessionAvailableForComp(int comp_id){
        List<Integer> year = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT PQ_SESSION FROM "+DatabaseHelper.PAST_QUESTION+" WHERE COMP_ID = " + comp_id, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>0) {
                    year.add(cursor.getInt(4));
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return year;
    }

    public int getQcmIdByPastQuesId(int pastQuestID) {
        int QcmId = 0;
        Cursor cursor = database.rawQuery("SELECT QCM_ID FROM "+DatabaseHelper.PAST_QUESTION+" WHERE PQ_ID = " + pastQuestID, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                QcmId = cursor.getInt(0);
            }
            cursor.close();
        }
        return  QcmId;
    }

    public int getLastQcmId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT QCM_ID FROM "+DatabaseHelper.QCM+" ORDER BY QCM_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListQcm(List<QCM> qcms) {
        for (int i=0; i<qcms.size(); i++)
        {
            ContentValues contentValue = new ContentValues();
            contentValue.put("QCM_ID",qcms.get(i).getQCM_ID());
            contentValue.put("QCM_TITLE",qcms.get(i).getQCM_TITLE());
            contentValue.put("QCM_DATE",qcms.get(i).getQCM_DATE());
            Cursor cursor = database.rawQuery("SELECT QCM_ID FROM "+DatabaseHelper.QCM+" WHERE QCM_ID = " + qcms.get(i).getQCM_ID(), null);

            if (cursor!=null){
                if (cursor.moveToFirst())
                    database.update(DatabaseHelper.QCM, contentValue, "PQ_ID=" + qcms.get(i).getQCM_ID(), null);
                else
                    database.insert(DatabaseHelper.QCM, null, contentValue);
                cursor.close();
            }
        }
    }

    public int getLastTutorialQcmId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT TUTO_ID FROM "+DatabaseHelper.TUTORIAL_QCM+" ORDER BY TUTO_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListTutorialQcm(List<TUTORIAL_QCM> tutorial_qcms) {
        for (int i=0; i<tutorial_qcms.size(); i++)
        {

            Cursor cursor = database.rawQuery("SELECT TUTO_ID FROM "+DatabaseHelper.TUTORIAL_QCM+" WHERE QCM_ID = " + tutorial_qcms.get(i).getQCM_ID() + " AND TUTO_ID = " + tutorial_qcms.get(i).getTUTO_ID(), null);

            if (cursor!=null){
                if (!cursor.moveToFirst()){
                    ContentValues contentValue = new ContentValues();
                    contentValue.put("TUTO_ID",tutorial_qcms.get(i).getTUTO_ID());
                    contentValue.put("QCM_ID",tutorial_qcms.get(i).getQCM_ID());
                    database.insert(DatabaseHelper.TUTORIAL_QCM, null, contentValue);
                }
                cursor.close();
            }
        }
    }

    public int getQcmIdByChapId(int chap_id) {
        int qcmId = 0;
        Cursor cursor = database.rawQuery("SELECT QCM_ID FROM tutorial_qcm INNER JOIN tutorial ON tutorial.TUTO_ID = tutorial_qcm.TUTO_ID WHERE tutorial.CHAPT_ID = " + chap_id, null);
        if (cursor!=null)
            if (cursor.moveToFirst()) {
                if (cursor.getColumnCount()>0) {
                    qcmId = cursor.getInt(0);
                }
                cursor.close();
            }
        return qcmId;
    }

    public int getLastContentId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT QCM_ID FROM "+DatabaseHelper.CONTENT+" ORDER BY QCM_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListContent(List<CONTENT> contents) {
        for (int i=0; i<contents.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT QCM_ID FROM "+DatabaseHelper.CONTENT+" WHERE QCM_ID = " + contents.get(i).getQCM_ID() + " AND QUEST_ID = " + contents.get(i).getQUEST_ID(), null);
            if (cursor!=null){
                if (!cursor.moveToFirst()){
                    ContentValues contentValue = new ContentValues();
                    contentValue.put("QCM_ID",contents.get(i).getQCM_ID());
                    contentValue.put("QUEST_ID",contents.get(i).getQUEST_ID());
                    database.insert(DatabaseHelper.CONTENT, null, contentValue);
                }
                cursor.close();
            }
        }
    }

    public int getLastQuestionId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT QUEST_ID FROM "+DatabaseHelper.QUESTION+" ORDER BY QUEST_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public List<QUESTION> getQuestionByQcmId(int qcmId) {
        List<QUESTION> questions = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT QUEST_ID, QUEST_LABEL, QUEST_ANSWER FROM question" +
                " INNER JOIN content ON content.QUEST_ID = question.QUEST_ID" +
                " WHERE content.QCM_ID = " + qcmId , null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>2) {
                    QUESTION question = new QUESTION();
                    question.setQUEST_ID(cursor.getInt(0));
                    question.setQUEST_LABEL(cursor.getString(1));
                    question.setQUEST_ANSWER(cursor.getString(2));
                    questions.add(question);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return questions;
    }

    public void insertListQuestion(List<QUESTION> questions) {
        for (int i=0; i<questions.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION+" WHERE QUEST_ID = " + questions.get(i).getQUEST_ID(), null);
            if (cursor!=null)
                if (cursor.getCount()==0) {
                    ContentValues contentValue = new ContentValues();
                    contentValue.put("QUEST_ID",questions.get(i).getQUEST_ID());
                    contentValue.put("QUEST_LABEL",questions.get(i).getQUEST_LABEL());
                    contentValue.put("QUEST_ANSWER",questions.get(i).getQUEST_ANSWER());
                    contentValue.put("QUEST_TYPE",questions.get(i).getQUEST_TYPE());
                    contentValue.put("QUEST_DATE",questions.get(i).getQUEST_DATE());
                    database.insert(DatabaseHelper.QUESTION, null, contentValue);
                }
            else
                cursor.close();
        }
    }

    public int getLastQuestionAnswerId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT QUEST_ID FROM "+DatabaseHelper.QUESTION_ANWSER+" ORDER BY QUEST_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public void insertListQuestionAnswer(List<QUESTION_ANWSER> question_anwsers) {
        for (int i=0; i<question_anwsers.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.QUESTION_ANWSER+" WHERE QUEST_ID = " + question_anwsers.get(i).getQUEST_ID() + " AND  ANWS_ID = " +question_anwsers.get(i).getANWS_ID(), null);
            if (cursor!=null)
                if (cursor.getCount()==0) {
                    ContentValues contentValue = new ContentValues();
                    contentValue.put("QUEST_ID",question_anwsers.get(i).getQUEST_ID());
                    contentValue.put("ANWS_ID",question_anwsers.get(i).getANWS_ID());
                    contentValue.put("ANWSER_STATE",question_anwsers.get(i).getANWS_STATE());
                    database.insert(DatabaseHelper.QUESTION_ANWSER, null, contentValue);
                }
                else
                    cursor.close();
        }
    }

    public int getLastAnswerId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT ANWS_ID FROM "+DatabaseHelper.ANWSER+" ORDER BY ANWS_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public List<ANWSER> getAnwserByQuestionId(int questId) {
        List<ANWSER> anwsers;
        anwsers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ANWS_ID, ANWS_CONTENT, question_answer.ANWSER_STATE FROM "+DatabaseHelper.ANWSER+
                " INNER JOIN  question_answer ON anwser.QUEST_ID = question_answer.QUEST_ID" +
                " WHERE question_answer.QUEST_ID = "+questId, null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            ANWSER anwser = new ANWSER();
            anwser.setANWS_ID(cursor.getInt(0));
            anwser.setANWS_CONTENT(cursor.getString(1));
            anwser.setANWS_STATE(cursor.getInt(2));
            anwsers.add(anwser);
            cursor.moveToNext();
        }
        cursor.close();
        return anwsers;
    }

    public void insertListAnwser(List<ANWSER> anwsers) {
        for (int i=0; i<anwsers.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT ANWS_ID FROM "+DatabaseHelper.ANWSER+" WHERE ANWS_ID = " + anwsers.get(i).getANWS_ID(), null);
            if (cursor.getCount()==0) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("ANWS_ID",anwsers.get(i).getANWS_ID());
                contentValue.put("ANWS_CONTENT",anwsers.get(i).getANWS_CONTENT());
                database.insert(DatabaseHelper.ANWSER, null, contentValue);
            }
            else
                cursor.close();
        }
    }

    public int getLastReqId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT REQ_ID FROM "+DatabaseHelper.REQUIEREMENT+" ORDER BY REQ_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    public REQUIEREMENT getRequierementById(int requirementId) {
        REQUIEREMENT requierement = new REQUIEREMENT();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.REQUIEREMENT+" WHERE REQ_ID = " + requirementId, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                if (cursor.getColumnCount()>5){
                    requierement.setREQ_ID(cursor.getInt(0));
                    requierement.setCOMP_ID(cursor.getInt(1));
                    requierement.setREQ_NAME(cursor.getString(2));
                    requierement.setREQ_FILE(cursor.getString(3));
                    requierement.setREQ_CONTENT(cursor.getString(4));
                    requierement.setREQ_DATE(cursor.getString(5));
                }
            }
            cursor.close();
        }
        return requierement;
    }

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
            if (cursor!=null){
                if (cursor.getCount()==0)
                    database.insert(DatabaseHelper.REQUIEREMENT, null, contentValue);
                else
                    database.update(DatabaseHelper.REQUIEREMENT,contentValue,"REQ_ID = "+requierements.get(i).getREQ_ID(),null);
                cursor.close();
            }
            else
                database.insert(DatabaseHelper.REQUIEREMENT, null, contentValue);
        }
    }

    public int getRequierementIdByCompId(int COMP_ID) {
        int reqId = 0;
        Cursor cursor = database.rawQuery("SELECT REQ_ID FROM "+DatabaseHelper.REQUIEREMENT+" WHERE COMP_ID = "+COMP_ID+ " ORDER BY REQ_ID DESC", null);
        if (cursor!=null){
            if (cursor.moveToFirst())
                reqId = cursor.getInt(0);
            cursor.close();
        }
        return reqId;
    }

    public BLOC_NOTE getBlocNoteById(int noteId) {
        BLOC_NOTE bloc_note = new BLOC_NOTE();

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.BLOC_NOTE+" WHERE NOTE_ID = " + noteId, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                if (cursor.getColumnCount()>4){
                    bloc_note.setNOTE_ID(cursor.getInt(0));
                    bloc_note.setUSER_ID(cursor.getInt(1));
                    bloc_note.setNOTE_CONTENT(cursor.getString(2));
                    bloc_note.setNOTE_COLOR(cursor.getInt(3));
                    bloc_note.setNOTE_DATE(cursor.getString(4));
                }
            }
            cursor.close();
        }
        return bloc_note;
    }

    public void saveBlocNote(BLOC_NOTE blocNote) {

        Cursor cursor = database.rawQuery("SELECT NOTE_ID FROM "+DatabaseHelper.BLOC_NOTE+" WHERE NOTE_ID = "+blocNote.getNOTE_ID(), null);

        ContentValues contentValue = new ContentValues();
        contentValue.put("NOTE_ID",blocNote.getNOTE_ID());
        contentValue.put("USER_ID",blocNote.getUSER_ID());
        contentValue.put("NOTE_CONTENT",blocNote.getNOTE_CONTENT());
        contentValue.put("NOTE_COLOR",blocNote.getNOTE_COLOR());
        contentValue.put("NOTE_DATE",blocNote.getNOTE_DATE());
        if (cursor!=null){
            if (cursor.getCount()==0)
                database.insert(DatabaseHelper.BLOC_NOTE, null, contentValue);
            else
                database.update(DatabaseHelper.BLOC_NOTE,contentValue,"NOTE_ID = "+blocNote.getNOTE_ID(),null);
            cursor.close();
        }
        else
            database.insert(DatabaseHelper.BLOC_NOTE, null, contentValue);
    }

    public List<BLOC_NOTE> listBlocNote() {
        List<BLOC_NOTE> bloc_notes =  new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.BLOC_NOTE+" ORDER BY NOTE_DATE DESC ", null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                if (cursor.getColumnCount()>4) {
                    BLOC_NOTE bloc_note = new BLOC_NOTE();
                    bloc_note.setNOTE_ID(cursor.getInt(0));
                    bloc_note.setUSER_ID(cursor.getInt(1));
                    bloc_note.setNOTE_CONTENT(cursor.getString(2));
                    bloc_note.setNOTE_COLOR(cursor.getInt(3));
                    bloc_note.setNOTE_DATE(cursor.getString(4));
                    bloc_notes.add(bloc_note);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }

        return  bloc_notes;
    }

    public int getLastFileId() {
        int id =0;
        Cursor cursor = database.rawQuery("SELECT FILE_ID FROM "+DatabaseHelper.FILE+" ORDER BY FILE_ID DESC LIMIT 1", null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }


    public void insertListFile(List<FILE> files) {
        for (int i=0; i<files.size(); i++)
        {
            Cursor cursor = database.rawQuery("SELECT FILE_ID FROM "+DatabaseHelper.FILE+" WHERE FILE_ID = "+files.get(i).getFILE_ID(), null);

            ContentValues contentValue = new ContentValues();
            contentValue.put("FILE_ID",files.get(i).getFILE_ID());
            contentValue.put("FILE_NAME",files.get(i).getFILE_NAME());
            contentValue.put("FILE_TYPE",files.get(i).getFILE_TYPE());
            contentValue.put("FILE_FORMAT",files.get(i).getFILE_FORMAT());
            if (cursor!=null){
                if (cursor.getCount()==0)
                    database.insert(DatabaseHelper.FILE, null, contentValue);
                else
                    database.update(DatabaseHelper.FILE,contentValue,"FILE_ID = "+files.get(i).getFILE_ID(),null);
                cursor.close();
            }
            else
                database.insert(DatabaseHelper.FILE, null, contentValue);
        }
    }

    public List<FILE> listFileByType(String type) {
        List<FILE> files;
        files = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT FILE_NAME FROM "+DatabaseHelper.FILE +" WHERE FILE_TYPE='"+type+"'", null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            for (int i=0; i<cursor.getCount(); i++){
                FILE file = new FILE();
                file.setFILE_NAME(cursor.getString(0));
                files.add(file);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return files;
    }

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

    public List<STAFFMEMBER> listStaffMember() {
        List<STAFFMEMBER> staffmembers;
        staffmembers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.STAFFMEMBER, null);
        if (cursor.getCount()>0) {
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

}
