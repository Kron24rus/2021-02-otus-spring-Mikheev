package com.mikheev.lessonone.service.impl;

import com.mikheev.lessonone.service.QuestionnaireService;
import com.mikheev.lessonone.storage.QuestionnaireStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {"csv-questions-path=questions.csv"})
class QuestionnaireServiceImplTest {

    @MockBean
    private QuestionnaireStorage questionnaireStorage;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Test
    void loadQuestionnaire() {
        questionnaireService.loadQuestionnaire();
        Mockito.verify(questionnaireStorage, times(1)).saveQuestionnaire(anyList());
    }

    @Test
    void printQuestionnaire() {
        questionnaireService.printQuestionnaire();
        Mockito.verify(questionnaireStorage, times(1)).loadQuestionnaire();
    }
}