package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Comment;
import com.mikheev.homeworkfive.domain.Genre;
import com.mikheev.homeworkfive.repositories.AuthorRepository;
import com.mikheev.homeworkfive.repositories.BookRepository;
import com.mikheev.homeworkfive.repositories.CommentRepository;
import com.mikheev.homeworkfive.repositories.GenreRepository;
import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private static final String TABLE_DELIMITER = "============================";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                              GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public String displayAllBooks() {
        List<Book> books = bookRepository.findAll();
        return prettyPrintEntity(books, "Books in data base");
    }

    @Override
    public String displayFullAuthorInfo() {
        List<Author> authors = authorRepository.findAll();
        return prettyPrintEntity(authors, "Authors in database");
    }

    @Override
    public String displayAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return prettyPrintEntity(genres, "Genres in database");
    }

    @Override
    public String displayBookWithId(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> prettyPrintEntity(Collections.singletonList(book), "Book with id: " + id))
                .orElseGet(() -> "No Book with id: " + id + "\n");
    }

    @Override
    public String displayAuthorWithId(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.map(author -> prettyPrintEntity(Collections.singletonList(author), "Author with id: " + id))
                .orElseGet(() -> "No Author with id: " + id + "\n");
    }

    @Override
    public String displayGenreWithId(long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.map(genre -> prettyPrintEntity(Collections.singletonList(genre), "Genre with id: " + id))
                .orElseGet(() -> "No Genre with id: " + id + "\n");
    }

    @Transactional
    @Override
    public String addBook(String title, long authorId, long genreId) {
        StringBuilder resultMessage = new StringBuilder("Adding new book: ");
        boolean success = true;
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalAuthor.isEmpty()) {
            resultMessage.append("Author with id ").append(authorId).append(" not found. ");
            success = false;
        }
        if (optionalGenre.isEmpty()) {
            resultMessage.append("Genre with id ").append(genreId).append(" not found. ");
            success = false;
        }

        if (success) {
            Book book = new Book(title, optionalAuthor.get(), optionalGenre.get());
            Book savedBook = bookRepository.save(book);
            resultMessage.append("Inserted with id - ").append(savedBook.getId());
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String deleteBook(long id) {
        bookRepository.deleteById(id);
        return "Book with id: " + id + " removed from database";
    }

    @Transactional
    @Override
    public String updateBook(long id, String title, Long author_id, Long genre_id) {
        StringBuilder resultMessage = new StringBuilder("Updating book with id: ").append(id).append(": ");
        final boolean[] success = {true};
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (title != null) {
                book.setTitle(title);
            }
            if (author_id != null) {
                Optional<Author> optionalAuthor = authorRepository.findById(author_id);
                optionalAuthor.ifPresentOrElse(book::setAuthor,
                        () -> {
                            resultMessage.append("Author with id: ").append(author_id).append(" not found. ");
                            success[0] = false;
                        });
            }
            if (genre_id != null) {
                Optional<Genre> optionalGenre = genreRepository.findById(genre_id);
                optionalGenre.ifPresentOrElse(book::setGenre,
                        () -> {
                            resultMessage.append("Genre with id: ").append(genre_id).append(" not found. ");
                            success[0] = false;
                        });
            }
            if (success[0]) {
                bookRepository.save(book);
                resultMessage.append("Book updated");
            }
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String displayBookComments(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        StringBuilder resultMessage = new StringBuilder();
        if (optionalBook.isPresent()) {
            List<Comment> comments = optionalBook.get().getComments();
            resultMessage.append(prettyPrintEntity(comments, "Comments for book"));
        } else {
            resultMessage.append("Book with id: ").append(id).append(" not found.");
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String addComment(long bookId, String text) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Comment comment = new Comment();
            comment.setText(text);
            book.getComments().add(comment);
            bookRepository.save(book);
            return "Added new comment to book with id: " + bookId;
        } else {
            return "Book with id: " + bookId + " not found.";
        }
    }

    @Transactional
    @Override
    public String updateComment(long commentId, String text) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(text);
            commentRepository.save(comment);
            return "Comment updated";
        } else {
            return "Comment with id " + commentId + " not found";
        }
    }

    @Transactional
    @Override
    public String deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
        return "Comment with id: " + commentId + " removed from database";
    }

    @Transactional
    @Override
    public String copyComment(long commentId, long bookId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent() && optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            Book book = optionalBook.get();
            Comment newComment = new Comment();
            newComment.setText(comment.getText());
            book.getComments().add(newComment);
            bookRepository.save(book);
            return "Comment with id: " + commentId + " copied to book with id: " + bookId;
        } else {
            return "Comment and/or book with specified id not found";
        }
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
