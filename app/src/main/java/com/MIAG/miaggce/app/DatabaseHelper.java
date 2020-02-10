package com.MIAG.miaggce.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Manuel Elvir
 * This class is use to access at the SQLDataBase
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    static final String EXAM = "exam";
    static final String COMPETITIVE = "competitive";
    static final String SUBJECT = "subject";
    static final String PAPER1 = "paper1";
    static final String PAPER2 = "paper2";
    static final String PAPER3 = "paper3";
    static final String REQUIEREMENT = "requierement";
    static final String CHAPTER = "chapter";
    static final String QUESTION = "question";
    static final String ANWSER = "anwser";
    static final String SUBJECT_CORRECTION = "subject_correction";
    static final String STAFFMEMBER = "staff_member";
    static final String FILE = "file";

    // Table columns


    // Database Information
    private static final String DB_NAME = "MIAG.DB";

    // database version
    private static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_EXAM = "create table " + EXAM + "( EXAM_ID INTEGER  PRIMARY KEY, EXAM_NAME TEXT NOT NULL, EXAM_DATE_START TEXT, EXAM_DATE_END TEXT);";
    private static final String CREATE_TABLE_COMPETITIVE = "create table " + COMPETITIVE + "( COMP_ID INTEGER  PRIMARY KEY, COMP_NAME TEXT NOT NULL, COMP_DATE TEXT);";
    private static final String CREATE_TABLE_SUBJECT = "create table " + SUBJECT + "( SJ_ID INTEGER  PRIMARY KEY, SJ_NAME TEXT NOT NULL, SJ_DATE TEXT);";
    private static final String CREATE_TABLE_PAPER1 = "create table " + PAPER1 + "( PAPER1_ID INTEGER PRIMARY KEY, QCM_ID INTEGER, TEST_ID INTEGER, TEST_NAME TEXT NOT NULL, TEST_CHRONO INTEGER, TEST_DATE TEXT, SJ_ID INTEGER);";
    private static final String CREATE_TABLE_PAPER2 = "create table " + PAPER2 + "( PAPER2_ID INTEGER PRIMARY KEY, TEST_ID INTEGER, TEST_NAME TEXT NOT NULL, TEST_CHRONO INTEGER, TEST_DATE TEXT, TEST_CONTENT TEXT, SJ_ID INTEGER);";
    private static final String CREATE_TABLE_PAPER3 = "create table " + PAPER3 + "( PAPER3_ID INTEGER PRIMARY KEY, TEST_ID INTEGER, TEST_NAME TEXT NOT NULL, TEST_CHRONO INTEGER, TEST_DATE TEXT, TEST_CONTENT TEXT, SJ_ID INTEGER);";
    private static final String CREATE_TABLE_CHAPTER = "create table " + CHAPTER + "( CHAP_ID INTEGER PRIMARY KEY, SJ_ID INTEGER, COMP_ID INTEGER, CHAP_NAME TEXT NOT NULL, CHAP_DATE TEXT);";
    private static final String CREATE_TABLE_REQUIEREMENT = "create table " + REQUIEREMENT + "( REQ_ID INTEGER PRIMARY KEY, COMP_ID INTEGER, REQ_NAME TEXT NOT NULL, REQ_FILE TEXT, REQ_CONTENT TEXT, REQ_DATE TEXT);";
    private static final String CREATE_TABLE_QUESTION = "create table " + QUESTION + "( QUEST_ID INTEGER PRIMARY KEY, QUEST_LABEL  TEXT NOT NULL, QUEST_TYPE TEXT, QUEST_DATE TEXT, PAPER1_ID INTEGER, CHAP_ID INTEGER);";
    private static final String CREATE_TABLE_ANWSER = "create table " + ANWSER + "( ANWS_ID INTEGER PRIMARY KEY, ANWS_CONTENT  TEXT NOT NULL, ANWS_DATE TEXT, QUEST_ID INTEGER);";
    private static final String CREATE_TABLE_SUBJECT_CORRECTION = "create table " + SUBJECT_CORRECTION + "( SJC_ID INTEGER PRIMARY KEY, SJC_CONTENT  TEXT NOT NULL, SJC_FILE INTEGER, PAPER1_ID INTEGER, CHAP_ID INTEGER);";
    private static final String CREATE_TABLE_STAFFMEMBER = "create table " + STAFFMEMBER + "( SM_ID INTEGER PRIMARY KEY, SM_NAME  TEXT NOT NULL, SM_FUNCTION TEXT, SM_NUMBER TEXT, SM_DATE TEXT);";
    private static final String CREATE_TABLE_FILE = "create table " + FILE + "( FILE_ID INTEGER PRIMARY KEY, FILE_NAME  TEXT, FILE_TYPE TEXT, FILE_FORMAT TEXT, FILE_DATE TEXT);";


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXAM);
        db.execSQL(CREATE_TABLE_COMPETITIVE);
        db.execSQL(CREATE_TABLE_SUBJECT);
        db.execSQL(CREATE_TABLE_PAPER1);
        db.execSQL(CREATE_TABLE_PAPER2);
        db.execSQL(CREATE_TABLE_PAPER3);
        db.execSQL(CREATE_TABLE_CHAPTER);
        db.execSQL(CREATE_TABLE_REQUIEREMENT);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ANWSER);
        db.execSQL(CREATE_TABLE_SUBJECT_CORRECTION);
        db.execSQL(CREATE_TABLE_STAFFMEMBER);
        db.execSQL(CREATE_TABLE_FILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXAM);
        db.execSQL("DROP TABLE IF EXISTS " + COMPETITIVE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + PAPER1);
        db.execSQL("DROP TABLE IF EXISTS " + PAPER2);
        db.execSQL("DROP TABLE IF EXISTS " + PAPER3);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + REQUIEREMENT);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + ANWSER);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_CORRECTION);
        db.execSQL("DROP TABLE IF EXISTS " + STAFFMEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + FILE);
        onCreate(db);
    }
}
