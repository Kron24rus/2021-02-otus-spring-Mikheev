package com.mikheev.homeworkfive.repositories;

import com.mikheev.homeworkfive.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(long id);
}
