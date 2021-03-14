package com.mikheev.lessonone.storage;

import com.mikheev.lessonone.model.Question;

import java.util.List;

public interface QuestionnaireStorage {

    void saveQuestionnaire(List<Question> questionnaire);

    List<Question> loadQuestionnaire();

}
