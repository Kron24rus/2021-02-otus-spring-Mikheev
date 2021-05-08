package com.mikheev.homeworkfive.utils;

import java.util.List;

public class EntityFormatterUtils {

    private static final String TABLE_DELIMITER = "============================";

    public static String prettyPrintEntity(List entities, String tableHeader) {
        StringBuilder tableString = new StringBuilder(TABLE_DELIMITER).append("\n");
        tableString.append(tableHeader).append("\n").append(TABLE_DELIMITER).append("\n");
        for (Object object : entities) {
            tableString.append(object.toString()).append("\n");
        }
        return tableString.toString();
    }
}
