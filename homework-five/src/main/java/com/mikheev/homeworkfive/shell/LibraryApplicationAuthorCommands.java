package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LibraryApplicationAuthorCommands {

    private LibraryService libraryService;

    public LibraryApplicationAuthorCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Display author with specified id", key = {"getauthor"})
    public String displayAuthorWithId(long id) {
        return libraryService.displayAuthorWithId(id);
    }

    @ShellMethod(value = "Display all authors", key = {"getallauthors"})
    public String displayAllAuthors() {
        return libraryService.displayFullAuthorInfo();
    }
}
