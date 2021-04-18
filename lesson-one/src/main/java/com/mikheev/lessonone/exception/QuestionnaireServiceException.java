package com.mikheev.lessonone.exception;

public class QuestionnaireServiceException extends RuntimeException {

    public QuestionnaireServiceException(String message) {
        super(message);
    }

    public QuestionnaireServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
