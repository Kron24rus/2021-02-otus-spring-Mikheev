package com.mikheev.homeworkfive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class HomeworkFiveApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(HomeworkFiveApplication.class, args);
    }

}
