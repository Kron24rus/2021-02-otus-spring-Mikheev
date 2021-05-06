package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LibraryApplicationBookCommands {

    private LibraryService libraryService;

    public LibraryApplicationBookCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Display book with specified id", key = {"getbook"})
    public String displayBookWithId(long id) {
        return libraryService.displayBookWithId(id);
    }

    @ShellMethod(value = "Display all books", key = {"getallbooks"})
    public String displayAll() {
        return libraryService.displayAllBooks();
    }

    @ShellMethod(value = "Add new book", key = {"addbook"})
    public String addNewBook(String title, long author_id, long genre_id) {
        return libraryService.addBook(title, author_id, genre_id);
    }

    @ShellMethod(value = "Delete book", key = {"deletebook"})
    public String deleteBook(long id) {
        return libraryService.deleteBook(id);
    }

    @ShellMethod(value = "Update book: pass null if you don't want to update this field", key = {"updatebook"})
    public String updateBook(@ShellOption(value = "--id") long id,
                             @ShellOption(value = "--title", defaultValue = ShellOption.NULL) String title,
                             @ShellOption(value = "--authorId", defaultValue = ShellOption.NULL) Long authorId,
                             @ShellOption(value = "--genreId", defaultValue = ShellOption.NULL) Long genreId) {
        return libraryService.updateBook(id, title, authorId, genreId);
    }

    @ShellMethod(value = "Display book comments", key = {"getcomments"})
    public String displayBookComments(long id) {
        return libraryService.displayBookComments(id);
    }

    @ShellMethod(value = "Add new comment to book", key = {"addcomment"})
    public String addNewComment(long bookId, String text) {
        return libraryService.addComment(bookId, text);
    }

    @ShellMethod(value = "Update comment text", key = {"updatecomment"})
    public String updateComment(long commentId, String text) {
        return libraryService.updateComment(commentId, text);
    }

    @ShellMethod(value = "Delete comment", key = {"deletecomment"})
    public String deleteComment(long commentId) {
        return libraryService.deleteComment(commentId);
    }

    @ShellMethod(value = "Copy comment", key = {"copycomment"})
    public String copyComment(long commentId, long bookId) {
        return libraryService.copyComment(commentId, bookId);
    }
}
