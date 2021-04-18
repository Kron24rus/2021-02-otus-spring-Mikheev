package com.mikheev.homeworkfive.service;

public interface LibraryService {

    String displayAllBooks();

    String displayFullAuthorInfo();

    String displayAllGenres();

    String displayBookWithId(long id);

    String displayAuthorWithId(long id);

    String displayGenreWithId(long id);

    String addBook(String title, long authorId, long genreId);

    String deleteBook(long id);

    String updateBook(long id, String title, Long author_id, Long genre_id);
}
