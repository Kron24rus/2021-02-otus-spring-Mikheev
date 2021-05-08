package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import com.mikheev.homeworkfive.repositories.AuthorRepository;
import com.mikheev.homeworkfive.repositories.BookRepository;
import com.mikheev.homeworkfive.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    private static final long ENTITY_ID = 153;
    private static final String ENTITY_NAME = "entityName";
    private static final String NEW_ENTITY_NAME = "newEntityName";
    private static final long AUTHOR_ID = 145;
    private static final long NEW_AUTHOR_ID = 255;
    private static final long GENRE_ID = 22;
    private static final long NEW_GENRE_ID = 33;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void displayAllBooks() {
        String responseMessage = bookService.displayAllBooks();
        Mockito.verify(bookRepository, times(1)).findAll();
        assertThat(responseMessage).contains("Books in data base");
    }

    @Test
    void displayBookWithId() {
        Book book = new Book();
        book.setTitle(ENTITY_NAME);
        book.setId(ENTITY_ID);
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(ENTITY_ID)).thenReturn(optionalBook);
        String responseMessage = bookService.displayBookWithId(ENTITY_ID);
        assertThat(responseMessage).contains("Book with id: " + ENTITY_ID);
    }


    @Test
    void addBook_messageWithCorrectIdReturned() {
        Author author = new Author();
        Genre genre = new Genre();
        Optional<Author> optionalAuthor = Optional.of(author);
        Optional<Genre> optionalGenre = Optional.of(genre);
        Book newBook = new Book(ENTITY_NAME, author, genre);
        Book savedBook = new Book(ENTITY_NAME, author, genre);
        savedBook.setId(ENTITY_ID);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(optionalAuthor);
        when(genreRepository.findById(GENRE_ID)).thenReturn(optionalGenre);
        when(bookRepository.save(newBook)).thenReturn(savedBook);
        String insertMessage = bookService.addBook(ENTITY_NAME, AUTHOR_ID, GENRE_ID);
        Mockito.verify(bookRepository, times(1)).save(newBook);
        assertThat(insertMessage).isEqualTo("Adding new book: Inserted with id - " + ENTITY_ID);
    }

    @Test
    void addBook_notSavedWrongAuthorId() {
        Genre genre = new Genre();
        Optional<Author> optionalAuthor = Optional.empty();
        Optional<Genre> optionalGenre = Optional.of(genre);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(optionalAuthor);
        when(genreRepository.findById(GENRE_ID)).thenReturn(optionalGenre);
        String insertMessage = bookService.addBook(ENTITY_NAME, AUTHOR_ID, GENRE_ID);
        assertThat(insertMessage).isEqualTo("Adding new book: Author with id: " + AUTHOR_ID + " not found. ");
    }

    @Test
    void deleteBook_messageWithCorrectIdReturned() {
        String deleteMessage = bookService.deleteBook(ENTITY_ID);
        Mockito.verify(bookRepository, times(1)).deleteById(ENTITY_ID);
        assertThat(deleteMessage).isEqualTo("Book with id: " + ENTITY_ID + " removed from database");
    }

    @Test
    void updateBook_verifyThatBookEntityUpdated() {
        Book book = new Book();
        Genre genre = new Genre();
        genre.setId(GENRE_ID);
        Author author = new Author();
        author.setId(AUTHOR_ID);
        Author newAuthor = new Author();
        newAuthor.setId(NEW_AUTHOR_ID);
        Genre newGenre = new Genre();
        newGenre.setId(NEW_GENRE_ID);
        book.setId(ENTITY_ID);
        book.setTitle(ENTITY_NAME);
        book.setGenre(genre);
        book.setAuthor(author);
        Optional<Book> optionalBook = Optional.of(book);
        Optional<Author> optionalAuthor = Optional.of(newAuthor);
        Optional<Genre> optionalGenre = Optional.of(newGenre);
        when(bookRepository.findById(ENTITY_ID)).thenReturn(optionalBook);
        when(authorRepository.findById(NEW_AUTHOR_ID)).thenReturn(optionalAuthor);
        when(genreRepository.findById(NEW_GENRE_ID)).thenReturn(optionalGenre);
        String updateMessage = bookService.updateBook(ENTITY_ID, NEW_ENTITY_NAME, NEW_AUTHOR_ID, NEW_GENRE_ID);
        Mockito.verify(bookRepository, times(1)).save(book);
        assertThat(book.getTitle()).isEqualTo(NEW_ENTITY_NAME);
        assertThat(updateMessage).contains("Book updated");
    }
}