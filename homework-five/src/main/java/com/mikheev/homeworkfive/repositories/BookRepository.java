package com.mikheev.homeworkfive.repositories;

import com.mikheev.homeworkfive.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long id);

    Book save(Book book);

    void deleteById(long id);
}
