package com.mikheev.lessonone.service.impl;

import com.mikheev.lessonone.config.QuestionnaireConfiguration;
import com.mikheev.lessonone.model.Question;
import com.mikheev.lessonone.model.Answer;
import com.mikheev.lessonone.service.IOService;
import com.mikheev.lessonone.service.QuestionnaireService;
import com.mikheev.lessonone.storage.QuestionnaireStorage;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final static String QUESTION_DELIMITER = "####################################\n";
    private final QuestionnaireStorage questionnaireStorage;
    private final QuestionnaireConfiguration questionnaireConfiguration;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final List<String> pathsToCsvQuestions;
    private final Map<String, String> pathsToCsvQuestionsMap;
    private String userName;

    public QuestionnaireServiceImpl(QuestionnaireStorage questionnaireStorage,
                                    IOService ioService,
                                    MessageSource messageSource,
                                    QuestionnaireConfiguration questionnaireConfiguration) {
        this.questionnaireStorage = questionnaireStorage;
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.questionnaireConfiguration = questionnaireConfiguration;
        this.pathsToCsvQuestionsMap = questionnaireConfiguration.getCsvFiles()
                .stream()
                .collect(Collectors.toMap(
                        QuestionnaireConfiguration.CsvFiles::getLocale,
                        QuestionnaireConfiguration.CsvFiles::getPath));
        this.pathsToCsvQuestions = questionnaireConfiguration.getCsvPaths();
    }

    @Override
    public void runQuestionnaire() {
        try {
            loadQuestionnaire();
            askUserName();
            startQuestionnaire();
//            printQuestionnaire();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void askUserName() throws IOException {
        ioService.writeToConsole(getLocalizedMessage("username") + ": ");
        String userName = ioService.readFromConsole();
        if (userName != null && userName.length() > 0) {
            this.userName = userName;
        } else {
            this.userName = "defaultName";
        }
    }

    private void loadQuestionnaire() {
        try {
            for (String questionsFilePath : pathsToCsvQuestions) {
                Resource resource = new ClassPathResource(questionsFilePath);
                InputStream csvFile = resource.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(csvFile);

                List<Question> questionnaire = new CsvToBeanBuilder(streamReader)
                        .withType(Question.class)
                        .build()
                        .parse();
                questionnaire.forEach(this::setQuestionAnswers);
                questionnaireStorage.saveQuestionnaire(questionnaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startQuestionnaire() throws IOException {
        List<Question> questionnaire = questionnaireStorage.loadQuestionnaire();
        for (Question question : questionnaire) {
            String questionString = getQuestionString(question);
            ioService.writeToConsole(questionString);
            String userResponse = ioService.readFromConsole();
            if (userResponse != null && userResponse.equals(question.getCorrect())) {
                ioService.writeToConsole(getLocalizedMessage("correct.answer") + "\n");
            } else {
                ioService.writeToConsole(getLocalizedMessage("wrong.answer") + "\n");
            }
        }
        LocaleContextHolder.getLocale().toString();
    }

    private void printQuestionnaire() {
        List<Question> questionnaire = questionnaireStorage.loadQuestionnaire();
        questionnaire.forEach(question -> ioService.writeToConsole(question.toString()));
    }

    private void setQuestionAnswers(Question question) {
        List<Answer> questionAnswers = new ArrayList<>();
        List<String> answersList = Arrays.asList(question.getAnswersString().split("\\|"));
        answersList.forEach(answerString -> {
            Answer answer = new Answer(answerString, question.getCorrect().equals(answerString));
            questionAnswers.add(answer);
        });
        question.setAnswers(questionAnswers);
    }

    private String getQuestionString(Question question) {
        StringBuilder stringBuilder = new StringBuilder(QUESTION_DELIMITER);
        stringBuilder.append(question.getQuestion()).append("\n");
        for (Answer answer : question.getAnswers()) {
            stringBuilder.append(answer.getAnswer()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String getLocalizedMessage(String messageCode) {
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }
}
