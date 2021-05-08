package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import com.mikheev.homeworkfive.repositories.AuthorRepository;
import com.mikheev.homeworkfive.repositories.BookRepository;
import com.mikheev.homeworkfive.repositories.GenreRepository;
import com.mikheev.homeworkfive.service.BookService;
import com.mikheev.homeworkfive.utils.EntityFormatterUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public String displayAllBooks() {
        List<Book> books = bookRepository.findAll();
        return EntityFormatterUtils.prettyPrintEntity(books, "Books in data base");
    }

    @Transactional(readOnly = true)
    @Override
    public String displayBookWithId(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> EntityFormatterUtils.prettyPrintEntity(Collections.singletonList(book), "Book with id: " + id))
                .orElseGet(() -> "No Book with id: " + id + "\n");
    }

    @Transactional
    @Override
    public String addBook(String title, long authorId, long genreId) {
        StringBuilder resultMessage = new StringBuilder("Adding new book: ");

        Author author = getAuthorOrAppendNotFound(authorId, resultMessage);
        Genre genre = getGenreOrAppendNotFound(genreId, resultMessage);

        if (!Objects.isNull(author) && !Objects.isNull(genre)) {
            Book book = new Book(title, author, genre);
            Book savedBook = bookRepository.save(book);
            resultMessage.append("Inserted with id - ").append(savedBook.getId());
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String updateBook(long id, String title, Long author_id, Long genre_id) {
        StringBuilder resultMessage = new StringBuilder("Updating book with id: ").append(id).append(": ");
        boolean success = true;
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (title != null) {
                book.setTitle(title);
            }
            if (author_id != null) {
                Author author = getAuthorOrAppendNotFound(author_id, resultMessage);
                if (author != null) {
                    book.setAuthor(author);
                } else {
                    success = false;
                }
            }
            if (genre_id != null) {
                Genre genre = getGenreOrAppendNotFound(genre_id, resultMessage);
                if (genre != null) {
                    book.setGenre(genre);
                } else {
                    success = false;
                }
            }
            if (success) {
                bookRepository.save(book);
                resultMessage.append("Book updated");
            }
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String deleteBook(long id) {
        bookRepository.deleteById(id);
        return "Book with id: " + id + " removed from database";
    }

    private Author getAuthorOrAppendNotFound(long authorId, StringBuilder resultMessage) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            resultMessage.append("Author with id: ").append(authorId).append(" not found. ");
            return null;
        }
    }

    private Genre getGenreOrAppendNotFound(long genreId, StringBuilder resultMessage) {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isPresent()) {
            return optionalGenre.get();
        } else {
            resultMessage.append("Genre with id: ").append(genreId).append(" not found. ");
            return null;
        }
    }
}
