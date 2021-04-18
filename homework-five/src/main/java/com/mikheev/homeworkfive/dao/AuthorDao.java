package com.mikheev.homeworkfive.dao;

import com.mikheev.homeworkfive.domain.Author;

import java.util.List;

public interface AuthorDao {

    long insert(Author author);

    void update(Author author);

    void delete(Author author);

    Author getById(long id);

    List<Author> getAll();
}
