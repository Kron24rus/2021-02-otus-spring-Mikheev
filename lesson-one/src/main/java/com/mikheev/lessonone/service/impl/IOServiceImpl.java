package com.mikheev.lessonone.service.impl;

import com.mikheev.lessonone.service.IOService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class IOServiceImpl implements IOService {

    @Override
    public String readFromConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    @Override
    public void writeToConsole(String message) {
        System.out.print(message);
    }
}
