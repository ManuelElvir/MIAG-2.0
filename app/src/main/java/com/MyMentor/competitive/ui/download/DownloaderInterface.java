package com.MyMentor.competitive.ui.download;

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

public interface DownloaderInterface {

    void onReceiveCompetitive(COMPETITIVE_RES competitiveList);

    void onReceiveSubject(SUBJECT_RES subjects);

    void onReceivePastQuestion(PAST_QUESTIONS_RES past_questions);

    void onReceiveChapter(CHAPTER_RES chapters);

    void onReceiveNote(NOTE_RES notes);

    void onReceiveTutorial(TUTORIAL_RES tutorials);

    void onReceiveQCM(QCM_RES qcms);

    void onReceiveContent(CONTENT_RES contents);

    void onReceiveQuestion(QUESTION_RES questions);

    void onReceiveQuestionAnswer(QUESTION_ANWSER_RES question_anwsers);

    void onReceiveAnswer(ANWSER_RES anwsers);

    void onReceiveTutorialQcm(TUTORIAL_QCM_RES tutorial_qcms);

    void onReceiveRequierement(REQUIEREMENT_RES requierements);

    void onReceiveStaffMember(STAFFMEMBER_RES staffmembers);

    void onReceiveImages(FILE_RES files);

    void onErrorLoadind(String cause);

    void onFinishDownload();
}
