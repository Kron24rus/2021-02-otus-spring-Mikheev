package com.mikheev.homeworkfive.dao.jdbc;

import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final String EXPECTED_BOOK_TITLE = "expectedBookTitle";
    private static final long EXPECTED_BOOK_ID = 3;

    private Book COMEDY_BOOK;
    private Book DETECTIVE_BOOK;
    private Book CRIMINAL_BOOK;
    private Author AUTHOR;
    private Genre DETECTIVE;
    private Genre COMEDY;
    private Genre CRIMINAL;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @BeforeEach
    private void buildDbEntities() {
        COMEDY_BOOK = new Book(0, "comedyBook");
        DETECTIVE_BOOK = new Book(1, "detectiveBook");
        CRIMINAL_BOOK = new Book(2,"criminalBook");
        AUTHOR = new Author(0, "AuthorOfDetectiveAndComedy");
        DETECTIVE = new Genre(0, "detective");
        COMEDY = new Genre(1, "comedy");
        CRIMINAL = new Genre(2, "criminal");

        COMEDY_BOOK.setGenre(COMEDY);
        COMEDY_BOOK.setAuthor(AUTHOR);
        DETECTIVE_BOOK.setGenre(DETECTIVE);
        DETECTIVE_BOOK.setAuthor(AUTHOR);
        CRIMINAL_BOOK.setGenre(CRIMINAL);
        CRIMINAL_BOOK.setAuthor(AUTHOR);
    }

    @Test
    void insert_insertNewBook() {
        Book expectedBook = new Book(EXPECTED_BOOK_TITLE, AUTHOR, COMEDY);
        long id = bookDaoJdbc.insert(expectedBook);
        Book returnedBook = bookDaoJdbc.getById(id);
        expectedBook.setId(EXPECTED_BOOK_ID);
        assertThat(returnedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void update_updatesBookById() {
        CRIMINAL_BOOK.setGenre(COMEDY);
        CRIMINAL_BOOK.setTitle(DETECTIVE_BOOK.getTitle());
        bookDaoJdbc.update(CRIMINAL_BOOK);
        Book returnedBook = bookDaoJdbc.getById(CRIMINAL_BOOK.getId());
        assertThat(returnedBook).usingRecursiveComparison().isEqualTo(CRIMINAL_BOOK);
    }

    @Test
    void delete_removesBookFromDatabase() {
        assertThatCode(() -> bookDaoJdbc.getById(DETECTIVE_BOOK.getId())).doesNotThrowAnyException();
        bookDaoJdbc.delete(DETECTIVE_BOOK);
        assertThatThrownBy(() -> bookDaoJdbc.getById(DETECTIVE_BOOK.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getById_returnsExpectedBook() {
        Book returnedBook = bookDaoJdbc.getById(COMEDY_BOOK.getId());
        assertThat(returnedBook).usingRecursiveComparison().isEqualTo(COMEDY_BOOK);
    }

    @Test
    void getAll_returnsAllBooksInTestDB() {
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(COMEDY_BOOK, CRIMINAL_BOOK, DETECTIVE_BOOK);
    }
}