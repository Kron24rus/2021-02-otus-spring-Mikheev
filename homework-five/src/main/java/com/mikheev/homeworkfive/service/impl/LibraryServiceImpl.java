package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.dao.AuthorDao;
import com.mikheev.homeworkfive.dao.BookDao;
import com.mikheev.homeworkfive.dao.GenreDao;
import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private static final String TABLE_DELIMITER = "============================";
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    public LibraryServiceImpl(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @Override
    public String displayAllBooks() {
        List<Book> books = bookDao.getAll();
        return prettyPrintEntity(books, "Books in data base");
    }

    @Override
    public String displayFullAuthorInfo() {
        List<Author> authors = authorDao.getAll();
        return prettyPrintEntity(authors, "Authors in database");
    }

    @Override
    public String displayAllGenres() {
        List<Genre> genres = genreDao.getAll();
        return prettyPrintEntity(genres, "Genres in database");
    }

    @Override
    public String displayBookWithId(long id) {
        Book book = bookDao.getById(id);
        return prettyPrintEntity(Collections.singletonList(book), "Book with id: " + id);
    }

    @Override
    public String displayAuthorWithId(long id) {
        Author author = authorDao.getById(id);
        if (author != null) {
            return prettyPrintEntity(Collections.singletonList(author), "Author with id: " + id);
        } else {
            return "No Author with id: " + id + "\n";
        }
    }

    @Override
    public String displayGenreWithId(long id) {
        Genre genre = genreDao.getById(id);
        return prettyPrintEntity(Collections.singletonList(genre), "Genre with id: " + id);
    }

    @Override
    public String addBook(String title, long authorId, long genreId) {
        Author author = new Author(authorId, "");
        Genre genre = new Genre(genreId, "");
        Book book = new Book(title, author, genre);
        long bookId = bookDao.insert(book);
        return "Book: " + title + " inserted with id: " + bookId;
    }

    @Override
    public String deleteBook(long id) {
        Book book = new Book(id, "");
        bookDao.delete(book);
        return "Book with id: " + id + " removed from database";
    }

    @Override
    public String updateBook(long id, String title, Long author_id, Long genre_id) {
        Book book = bookDao.getById(id);
        if (title != null) {
            book.setTitle(title);
        }
        if (author_id != null) {
            book.setAuthor(new Author(author_id, ""));
        }
        if (genre_id != null) {
            book.setGenre(new Genre(genre_id, ""));
        }
        bookDao.update(book);
        return "Book with id: " + id + " updated";
    }

    private String prettyPrintEntity(List entities, String tableHeader) {
        StringBuilder tableString = new StringBuilder(TABLE_DELIMITER).append("\n");
        tableString.append(tableHeader).append("\n").append(TABLE_DELIMITER).append("\n");
        for (Object object : entities) {
            tableString.append(object.toString()).append("\n");
        }
        return tableString.toString();
    }
}
