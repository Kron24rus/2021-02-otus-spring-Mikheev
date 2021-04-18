package com.mikheev.homeworkfive.dao;

import com.mikheev.homeworkfive.domain.Genre;

import java.util.List;

public interface GenreDao {

    long insert(Genre genre);

    void update(Genre genre);

    void delete(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();
}
