package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.dao.AuthorDao;
import com.mikheev.homeworkfive.dao.BookDao;
import com.mikheev.homeworkfive.dao.GenreDao;
import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class LibraryServiceImplTest {

    private static final long ENTITY_ID = 153;
    private static final String ENTITY_NAME = "entityName";
    private static final String NEW_ENTITY_NAME = "newEntityName";
    private static final long AUTHOR_ID = 145;
    private static final long NEW_AUTHOR_ID = 255;
    private static final long GENRE_ID = 22;
    private static final long NEW_GENRE_ID = 33;

    @MockBean
    private AuthorDao authorDaoMock;

    @MockBean
    private BookDao bookDaoMock;

    @MockBean
    private GenreDao genreDaoMock;

    @Autowired
    private LibraryServiceImpl libraryService;

    @Test
    void displayAllBooks() {
        String responseMessage = libraryService.displayAllBooks();
        Mockito.verify(bookDaoMock, times(1)).getAll();
        assertThat(responseMessage).contains("Books in data base");
    }

    @Test
    void displayBookWithId() {
        Book book = new Book(ENTITY_ID, ENTITY_NAME);
        when(bookDaoMock.getById(ENTITY_ID)).thenReturn(book);
        String responseMessage = libraryService.displayBookWithId(ENTITY_ID);
        assertThat(responseMessage).contains("Book with id: " + ENTITY_ID);
    }


    @Test
    void addBook_messageWithCorrectIdReturned() {
        Author author = new Author(AUTHOR_ID, "");
        Genre genre = new Genre(GENRE_ID, "");
        Book book = new Book(ENTITY_NAME, author, genre);
        when(bookDaoMock.insert(book)).thenReturn(ENTITY_ID);
        String insertMessage = libraryService.addBook(ENTITY_NAME, AUTHOR_ID, GENRE_ID);
        Mockito.verify(bookDaoMock, times(1)).insert(book);
        assertThat(insertMessage).isEqualTo("Book: " + ENTITY_NAME + " inserted with id: " + ENTITY_ID);

    }

    @Test
    void deleteBook_messageWithCorrectIdReturned() {
        String deleteMessage = libraryService.deleteBook(ENTITY_ID);
        Mockito.verify(bookDaoMock, times(1)).delete(any(Book.class));
        assertThat(deleteMessage).isEqualTo("Book with id: " + ENTITY_ID + " removed from database");
    }

    @Test
    void updateBook_verifyThatBookEntityUpdated() {
        Book book = new Book();
        book.setId(ENTITY_ID);
        book.setTitle(ENTITY_NAME);
        book.setGenre(new Genre(GENRE_ID, ""));
        book.setAuthor(new Author(AUTHOR_ID, ""));
        when(bookDaoMock.getById(ENTITY_ID)).thenReturn(book);
        String updateMessage = libraryService.updateBook(ENTITY_ID, NEW_ENTITY_NAME, NEW_AUTHOR_ID, NEW_GENRE_ID);
        Mockito.verify(bookDaoMock, times(1)).update(book);
        assertThat(book.getTitle()).isEqualTo(NEW_ENTITY_NAME);
        assertThat(updateMessage).isEqualTo("Book with id: " + ENTITY_ID + " updated");
    }
}