package com.mikheev.homeworkfive.service;

public interface BookService {

    String displayAllBooks();

    String displayBookWithId(long id);

    String addBook(String title, long authorId, long genreId);

    String deleteBook(long id);

    String updateBook(long id, String title, Long author_id, Long genre_id);
}
