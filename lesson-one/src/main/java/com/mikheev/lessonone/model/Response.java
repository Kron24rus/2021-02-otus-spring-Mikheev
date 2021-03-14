package com.mikheev.lessonone.model;

public class Response {

    private String response;

    private boolean isCorrect;

    public Response(String response, boolean isCorrect) {
        this.response = response;
        this.isCorrect = isCorrect;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
