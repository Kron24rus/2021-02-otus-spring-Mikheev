package com.mikheev.homeworkfive.repositories.impl;

import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Comment;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Tests for comment repository")
@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    private static final long EXPECTED_BOOK_ID = 0;
    private static final long NOT_EXISTING_BOOK_ID = 145;
    private static final String EXPECTED_COMMENT_TEXT = "test positive comment";
    private static final String UPDATED_COMMENT_TEXT = "updated comment";
    private static final long EXPECTED_COMMENT_ID = 1;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final long EXPECTED_NEW_COMMENT_ID = 5;

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findCommentsByBookId_success() {
        val comments = commentRepository.findCommentsByBookId(EXPECTED_BOOK_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(comment -> comment.getText().equals(EXPECTED_COMMENT_TEXT))
                .allMatch(comment -> comment.getBook().getId() == EXPECTED_BOOK_ID);
    }

    @Test
    void findCommentsByBookId_wrongBookId() {
        val comments = commentRepository.findCommentsByBookId(NOT_EXISTING_BOOK_ID);
        assertThat(comments).isEmpty();
    }

    @Test
    void save_newCommentSaved() {
        Book book = entityManager.find(Book.class, EXPECTED_BOOK_ID);
        Comment newComment = new Comment();
        newComment.setText(EXPECTED_COMMENT_TEXT);
        newComment.setBook(book);
        Comment savedComment = commentRepository.save(newComment);
        Comment expectedComment = entityManager.find(Comment.class, EXPECTED_NEW_COMMENT_ID);
        Assertions.assertThat(savedComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void save_updateCommentSuccess() {
        Comment comment = entityManager.find(Comment.class, EXPECTED_COMMENT_ID);
        assertThat(comment.getText()).isEqualTo(EXPECTED_COMMENT_TEXT);

        comment.setText(UPDATED_COMMENT_TEXT);

        commentRepository.save(comment);

        Comment updatedComment = entityManager.find(Comment.class, EXPECTED_COMMENT_ID);
        assertThat(updatedComment.getText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    void deleteById_success() {
        Comment comment = entityManager.find(Comment.class, EXPECTED_COMMENT_ID);
        Assertions.assertThat(comment).isNotNull();
        entityManager.detach(comment);

        commentRepository.deleteById(EXPECTED_COMMENT_ID);

        Comment expectedNullComment = entityManager.find(Comment.class, EXPECTED_COMMENT_ID);
        Assertions.assertThat(expectedNullComment).isNull();
    }
}