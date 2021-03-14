package com.mikheev.lessonone.storage.Impl;

import com.mikheev.lessonone.model.Question;
import com.mikheev.lessonone.storage.QuestionnaireStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionnaireStorageImpl implements QuestionnaireStorage {

    private List<Question> questionnaire;

    @Override
    public void saveQuestionnaire(List<Question> questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Override
    public List<Question> loadQuestionnaire() {
        return questionnaire;
    }
}
