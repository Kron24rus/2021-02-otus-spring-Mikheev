package com.mikheev.homeworkfive.dao;

import com.mikheev.homeworkfive.domain.Book;

import java.util.List;

public interface BookDao {

    long insert(Book book);

    void update(Book book);

    void delete(Book book);

    Book getById(long id);

    List<Book> getAll();
}
