package com.mikheev.lessonone.model;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Question {

    @CsvBindByName(column = "id")
    private long id;

    @CsvBindByName(column = "question")
    private String question;

    @CsvBindByName(column = "responses")
    private String responsesString;

    @CsvBindByName(column = "correct")
    private String correct;

    private List<Response> responses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponsesString() {
        return responsesString;
    }

    public void setResponsesString(String responsesString) {
        this.responsesString = responsesString;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correct='" + correct + '\'' +
                ", responses=" + responses +
                '}';
    }
}
