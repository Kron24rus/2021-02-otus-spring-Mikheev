package com.mikheev.lessonone;

import com.mikheev.lessonone.service.QuestionnaireService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@SpringBootApplication
public class LessonOneApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LessonOneApplication.class, args);

        QuestionnaireService questionnaireService = context.getBean(QuestionnaireService.class);
        questionnaireService.runQuestionnaire();
    }

}
