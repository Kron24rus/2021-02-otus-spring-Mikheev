package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.domain.Genre;
import com.mikheev.homeworkfive.repositories.GenreRepository;
import com.mikheev.homeworkfive.service.GenreService;
import com.mikheev.homeworkfive.utils.EntityFormatterUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public String displayAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return EntityFormatterUtils.prettyPrintEntity(genres, "Genres in database");
    }

    @Transactional(readOnly = true)
    @Override
    public String displayGenreWithId(long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre
                .map(genre -> EntityFormatterUtils.prettyPrintEntity(Collections.singletonList(genre), "Genre with id: " + id))
                .orElseGet(() -> "No Genre with id: " + id + "\n");
    }
}
