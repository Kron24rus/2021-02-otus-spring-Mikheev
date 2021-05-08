package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.service.AuthorService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LibraryApplicationAuthorCommands {

    private AuthorService authorService;

    public LibraryApplicationAuthorCommands(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Display author with specified id", key = {"getauthor"})
    public String displayAuthorWithId(long id) {
        return authorService.displayAuthorWithId(id);
    }

    @ShellMethod(value = "Display all authors", key = {"getallauthors"})
    public String displayAllAuthors() {
        return authorService.displayFullAuthorInfo();
    }
}
