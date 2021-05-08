package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.service.BookService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LibraryApplicationBookCommands {

    private BookService bookService;

    public LibraryApplicationBookCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Display book with specified id", key = {"getbook"})
    public String displayBookWithId(long id) {
        return bookService.displayBookWithId(id);
    }

    @ShellMethod(value = "Display all books", key = {"getallbooks"})
    public String displayAll() {
        return bookService.displayAllBooks();
    }

    @ShellMethod(value = "Add new book", key = {"addbook"})
    public String addNewBook(String title, long author_id, long genre_id) {
        return bookService.addBook(title, author_id, genre_id);
    }

    @ShellMethod(value = "Delete book", key = {"deletebook"})
    public String deleteBook(long id) {
        return bookService.deleteBook(id);
    }

    @ShellMethod(value = "Update book: pass null if you don't want to update this field", key = {"updatebook"})
    public String updateBook(@ShellOption(value = "--id") long id,
                             @ShellOption(value = "--title", defaultValue = ShellOption.NULL) String title,
                             @ShellOption(value = "--authorId", defaultValue = ShellOption.NULL) Long authorId,
                             @ShellOption(value = "--genreId", defaultValue = ShellOption.NULL) Long genreId) {
        return bookService.updateBook(id, title, authorId, genreId);
    }
}
