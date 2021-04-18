package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.domain.DomainNames;
import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;

@ShellComponent
public class LibraryApplicationCommands {

    private LibraryService libraryService;

    public LibraryApplicationCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Show data stored in database", key = {"get", "show"})
    public String getEntityById(String entityName, long id) {
        DomainNames domainName = DomainNames.forNameIgnoreCase(entityName);
        String responseMessage;
        if (domainName != null) {
            if (domainName == DomainNames.AUTHOR) {
                responseMessage = libraryService.displayAuthorWithId(id);
            } else if (domainName == DomainNames.BOOK) {
                responseMessage = libraryService.displayBookWithId(id);
            } else {
                responseMessage = libraryService.displayGenreWithId(id);
            }
            return responseMessage;
        } else {
            return "Wrong item name: " + entityName + "\n" + "Use one of: " + Arrays.toString(DomainNames.values());
        }
    }

    @ShellMethod(value = "Show data stored in database", key = {"show all", "get all"})
    public String getEntities(String entityName) {
        DomainNames domainName = DomainNames.forNameIgnoreCase(entityName);
        String responseMessage;
        if (domainName != null) {
            if (domainName == DomainNames.AUTHOR) {
                responseMessage = libraryService.displayFullAuthorInfo();
            } else if (domainName == DomainNames.BOOK) {
                responseMessage = libraryService.displayAllBooks();
            } else {
                responseMessage = libraryService.displayAllGenres();
            }
            return responseMessage;
        } else {
            return "Wrong item name: " + entityName + "\n" + "Use one of: " + Arrays.toString(DomainNames.values());
        }
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
}
