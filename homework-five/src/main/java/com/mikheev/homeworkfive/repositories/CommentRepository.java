package com.mikheev.homeworkfive.repositories;

import com.mikheev.homeworkfive.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    void deleteById(long id);
}
