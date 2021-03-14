package com.mikheev.lessonone.service.impl;

import com.mikheev.lessonone.model.Question;
import com.mikheev.lessonone.model.Response;
import com.mikheev.lessonone.service.QuestionnaireService;
import com.mikheev.lessonone.storage.QuestionnaireStorage;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionnaireStorage questionnaireStorage;

    @Value("${csv-questions-path}")
    private String pathToCsvFile;

    public QuestionnaireServiceImpl(QuestionnaireStorage questionnaireStorage) {
        this.questionnaireStorage = questionnaireStorage;
    }

    @Override
    public void loadQuestionnaire() {
        try {
            Resource resource = new ClassPathResource(pathToCsvFile);
            InputStream csvFile = resource.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(csvFile);

            List<Question> questionnaire = new CsvToBeanBuilder(streamReader)
                    .withType(Question.class)
                    .build()
                    .parse();
            questionnaire.forEach(this::setQuestionResponses);
            questionnaireStorage.saveQuestionnaire(questionnaire);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printQuestionnaire() {
        List<Question> questionnaire = questionnaireStorage.loadQuestionnaire();
        questionnaire.forEach(System.out::println);
    }

    private void setQuestionResponses(Question question) {
        List<Response> questionResponses = new ArrayList<>();
        List<String> responseList = Arrays.asList(question.getResponsesString().split("\\|"));
        responseList.forEach(responseString -> {
            Response response = new Response(responseString, question.getCorrect().equals(responseString));
            questionResponses.add(response);
        });
        question.setResponses(questionResponses);
    }
}
