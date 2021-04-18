package com.mikheev.homeworkfive.domain;

public enum  DomainNames {
    AUTHOR,
    BOOK,
    GENRE;

    static public DomainNames forNameIgnoreCase(String value) {
        for (DomainNames name : DomainNames.values()) {
            if (name.name().equalsIgnoreCase(value)) {
                return name;
            }
        }
        return null;
    }
}
