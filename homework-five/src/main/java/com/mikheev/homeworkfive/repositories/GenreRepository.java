package com.mikheev.homeworkfive.repositories;

import com.mikheev.homeworkfive.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
