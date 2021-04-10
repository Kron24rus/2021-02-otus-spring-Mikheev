package com.mikheev.lessonone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "questionnaire")
public class QuestionnaireConfiguration {

    private List<String> csvPaths;

    private List<CsvFiles> csvFiles;

    public List<String> getCsvPaths() {
        return csvPaths;
    }

    public void setCsvPaths(List<String> csvPaths) {
        this.csvPaths = csvPaths;
    }

    public List<CsvFiles> getCsvFiles() {
        return csvFiles;
    }

    public void setCsvFiles(List<CsvFiles> csvFiles) {
        this.csvFiles = csvFiles;
    }

    public static class CsvFiles {

        private String locale;
        private String path;

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
