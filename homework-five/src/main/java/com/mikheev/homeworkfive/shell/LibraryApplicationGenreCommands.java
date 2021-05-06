package com.mikheev.homeworkfive.shell;

import com.mikheev.homeworkfive.service.LibraryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LibraryApplicationGenreCommands {

    private LibraryService libraryService;

    public LibraryApplicationGenreCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Display genre with specified id", key = {"getgenre"})
    public String displayGenreWithId(long id) {
        return libraryService.displayGenreWithId(id);
    }

    @ShellMethod(value = "Display all genres", key = {"getallgenres"})
    public String displayAll() {
        return libraryService.displayAllGenres();
    }
}
