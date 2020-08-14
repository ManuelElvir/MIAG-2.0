package com.MyMentor.competitive.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Manuel Elvir
 * This class is use to access at the SQLDataBase
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String PAST_QUESTION = "past_question";
    static final String QCM = "qcm";
    static final String QUESTION = "question";
    static final String QUESTION_ANWSER = "question_answer";
    static final String BLOC_NOTE = "bloc_note";
    static final String COMPETITIVE = "competitive";
    static final String CONTENT = "content";
    static final String NOTE = "note";
    static final String SUBJECT = "subject";
    static final String REQUIEREMENT = "requierement";
    static final String CHAPTER = "chapter";
    static final String TUTORIAL = "tutorial";
    static final String TUTORIAL_QCM = "tutorial_qcm";
    static final String ANWSER = "anwser";
    static final String STAFFMEMBER = "staff_member";
    static final String FILE = "file";

    // Database Information
    private static final String DB_NAME = "MyMentor.DB";

    // database version
    private static final int DB_VERSION = 1;

    //new data base


    private static final String CREATE_TABLE_COMPETITIVE = "create table " + COMPETITIVE + "( COMP_ID INTEGER  PRIMARY KEY, COMP_NAME TEXT, COMP_DATE TEXT);";
    private static final String CREATE_TABLE_SUBJECT = "create table " + SUBJECT + "( SJ_ID INTEGER  PRIMARY KEY, SJ_NAME TEXT, SJ_DATE TEXT);";
    private static final String CREATE_TABLE_CHAPTER = "create table " + CHAPTER + "( CHAP_ID INTEGER PRIMARY KEY, SJ_ID INTEGER, CHAP_NAME TEXT, CHAP_DATE TEXT);";
    private static final String CREATE_TABLE_TUTORIAL = "create table " + TUTORIAL + "( TUTO_ID INTEGER PRIMARY KEY, CHAPT_ID INTEGER, COMP_ID INTEGER, TUTO_NAME TEXT, TUT_DATE TEXT);";
    private static final String CREATE_TABLE_NOTE = "create table " + NOTE + "( NOTE_ID INTEGER  PRIMARY KEY, NOTE_CONTENT TEXT, NOTE_DATE TEXT, CHAP_ID INTEGER);";
    private static final String CREATE_TABLE_PAST_QUESTION = "create table " + PAST_QUESTION + "( PQ_ID INTEGER  PRIMARY KEY, COMP_ID INTEGER, QCM_ID INTEGER, PQ_TITLE TEXT, PQ_SESSION INTEGER, PQ_TIMING TEXT, PQ_DATE TEXT);";
    private static final String CREATE_TABLE_QCM = "create table " + QCM + "( QCM_ID INTEGER  PRIMARY KEY, QCM_TITLE TEXT, QCM_DATE TEXT);";
    private static final String CREATE_TABLE_TUTORIAL_QCM = "create table " + TUTORIAL_QCM + "( TUTO_ID INTEGER, QCM_ID INTEGER, PRIMARY KEY (TUTO_ID,QCM_ID));";
    private static final String CREATE_TABLE_CONTENT = "create table " + CONTENT + "(QCM_ID INTEGER, QUEST_ID INTEGER, PRIMARY KEY (QCM_ID,QUEST_ID));";
    private static final String CREATE_TABLE_QUESTION = "create table " + QUESTION + "( QUEST_ID INTEGER  PRIMARY KEY, QUEST_LABEL TEXT, QUEST_ANSWER TEXT, QUEST_TYPE TEXT, QUEST_DATE TEXT);";
    private static final String CREATE_TABLE_QUESTION_ANWSER = "create table " + QUESTION_ANWSER + "(QUEST_ID INTEGER, ANWS_ID INTEGER, ANWSER_STATE INTEGER, PRIMARY KEY(QUEST_ID, ANWS_ID));";
    private static final String CREATE_TABLE_ANWSER = "create table " + ANWSER + "( ANWS_ID INTEGER PRIMARY KEY, ANWS_CONTENT  TEXT);";
    private static final String CREATE_TABLE_REQUIEREMENT = "create table " + REQUIEREMENT + "( REQ_ID INTEGER PRIMARY KEY, COMP_ID INTEGER, REQ_NAME TEXT, REQ_FILE TEXT, REQ_CONTENT TEXT, REQ_DATE TEXT);";
    private static final String CREATE_TABLE_BLOC_NOTE = "create table " + BLOC_NOTE + "( NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, NOTE_CONTENT TEXT, NOTE_COLOR INT, NOTE_DATE TEXT);";
    private static final String CREATE_TABLE_FILE = "create table " + FILE + "( FILE_ID INTEGER PRIMARY KEY, FILE_NAME TEXT, FILE_TYPE TEXT, FILE_FORMAT TEXT);";
    private static final String CREATE_TABLE_STAFFMEMBER = "create table " + STAFFMEMBER + "( SM_ID INTEGER PRIMARY KEY AUTOINCREMENT, SM_NAME  TEXT, SM_FUNCTION TEXT, SM_NUMBER TEXT, SM_IMAGE TEXT);";



    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMPETITIVE);
        db.execSQL(CREATE_TABLE_SUBJECT);
        db.execSQL(CREATE_TABLE_PAST_QUESTION);
        db.execSQL(CREATE_TABLE_QCM);
        db.execSQL(CREATE_TABLE_QUESTION_ANWSER);
        db.execSQL(CREATE_TABLE_CHAPTER);
        db.execSQL(CREATE_TABLE_REQUIEREMENT);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ANWSER);
        db.execSQL(CREATE_TABLE_STAFFMEMBER);
        db.execSQL(CREATE_TABLE_FILE);
        db.execSQL(CREATE_TABLE_TUTORIAL);
        db.execSQL(CREATE_TABLE_BLOC_NOTE);
        db.execSQL(CREATE_TABLE_CONTENT);
        db.execSQL(CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_TUTORIAL_QCM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMPETITIVE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + PAST_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + QCM);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_ANWSER);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + REQUIEREMENT);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + ANWSER);
        db.execSQL("DROP TABLE IF EXISTS " + STAFFMEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + FILE);
        db.execSQL("DROP TABLE IF EXISTS " + TUTORIAL);
        db.execSQL("DROP TABLE IF EXISTS " + BLOC_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTENT);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TUTORIAL_QCM);
        onCreate(db);
    }
}
