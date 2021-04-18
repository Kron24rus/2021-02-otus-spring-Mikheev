package com.mikheev.lessonone.service;

import java.io.IOException;

public interface IOService {

    String readFromConsole() throws IOException;

    void writeToConsole(String message);
}
