package com.mikheev.homeworkfive.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Data
public class Author {

    private long id;
    private final String name;
    private Set<Book> books;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
        this.books = new HashSet<>();
    }
}
