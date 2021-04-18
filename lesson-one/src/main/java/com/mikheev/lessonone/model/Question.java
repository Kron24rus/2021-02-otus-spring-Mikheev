package com.mikheev.lessonone.model;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Question {

    @CsvBindByName(column = "id")
    private long id;

    @CsvBindByName(column = "question")
    private String question;

    @CsvBindByName(column = "answers")
    private String answersString;

    @CsvBindByName(column = "correct")
    private String correct;

    private List<Answer> answers;

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

    public String getAnswersString() {
        return answersString;
    }

    public void setAnswersString(String answersString) {
        this.answersString = answersString;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correct='" + correct + '\'' +
                ", answers=" + answers +
                '}';
    }
}
