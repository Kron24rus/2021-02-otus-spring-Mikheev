package com.mikheev.homeworkfive.repositories.impl;

import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for book repository")
@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {

    private static final long BOOK_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String EXPECTED_BOOK_TITLE = "expectedBookTitle";
    private static final long EXPECTED_BOOK_ID = 3;
    private static final String GENRE_DETECTIVE = "detective";
    private static final String GENRE_COMEDY = "comedy";
    private static final long GENRE_COMEDY_ID = 1;
    private static final String BOOK_TITLE_DETECTIVE = "detectiveBook";

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findById_validBook() {
        val optionalFoundBook = bookRepository.findById(BOOK_ID);
        val expectedBook = entityManager.find(Book.class, BOOK_ID);
        assertThat(optionalFoundBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findById_notFound() {
        val optionalNotFoundBook = bookRepository.findById(EXPECTED_BOOK_ID);
        assertThat(optionalNotFoundBook.isEmpty()).isTrue();
    }

    @Test
    void findAllBooks_containsAllBooksWithAuthorAndGenre() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> !book.getTitle().isEmpty())
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenre() != null);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @Test
    void saveBook_newBookSaved() {
        Author author = entityManager.find(Author.class, 0L);
        Genre genre = entityManager.find(Genre.class, 0L);
        Book newBook = new Book();
        newBook.setTitle(EXPECTED_BOOK_TITLE);
        newBook.setAuthor(author);
        newBook.setGenre(genre);
        Book savedBook = bookRepository.save(newBook);
        Book expectedBook = entityManager.find(Book.class, EXPECTED_BOOK_ID);
        assertThat(savedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void saveBook_bookUpdated() {
        Book book = entityManager.find(Book.class, BOOK_ID);
        assertThat(book.getGenre().getName()).isEqualTo(GENRE_DETECTIVE);
        assertThat(book.getTitle()).isEqualTo(BOOK_TITLE_DETECTIVE);

        Genre genre = entityManager.find(Genre.class, GENRE_COMEDY_ID);
        book.setTitle(EXPECTED_BOOK_TITLE);
        book.setGenre(genre);

        bookRepository.save(book);
        Book savedBook = entityManager.find(Book.class, BOOK_ID);

        assertThat(savedBook.getTitle()).isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(savedBook.getGenre()).usingRecursiveComparison().isEqualTo(genre);
        assertThat(savedBook.getId()).isEqualTo(BOOK_ID);
    }

    @Test
    void deleteBook_success() {
        Book book = entityManager.find(Book.class, BOOK_ID);
        assertThat(book).isNotNull();
        entityManager.detach(book);

        bookRepository.deleteById(BOOK_ID);

        Book expectedNullBook = entityManager.find(Book.class, BOOK_ID);
        assertThat(expectedNullBook).isNull();
    }
}