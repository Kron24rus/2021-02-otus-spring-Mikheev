package com.mikheev.homeworkfive.service.impl;

import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Comment;
import com.mikheev.homeworkfive.repositories.BookRepository;
import com.mikheev.homeworkfive.repositories.CommentRepository;
import com.mikheev.homeworkfive.service.CommentService;
import com.mikheev.homeworkfive.utils.EntityFormatterUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public String displayBookComments(long id) {
        List<Comment> comments = commentRepository.findCommentsByBookId(id);
        StringBuilder resultMessage = new StringBuilder();
        if (comments != null && !comments.isEmpty()) {
            resultMessage.append(EntityFormatterUtils.prettyPrintEntity(comments, "Comments for book"));
        } else {
            resultMessage.append("Comments for Book with id: ").append(id).append(" not found.");
        }
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String addComment(long bookId, String text) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Comment comment = new Comment();
            comment.setText(text);
            comment.setBook(book);
            Comment savedComment = commentRepository.save(comment);
            return "Added new comment with id: " + savedComment.getId();
        } else {
            return "Book with id: " + bookId + " not found.";
        }
    }

    @Transactional
    @Override
    public String updateComment(long commentId, String text) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(text);
            commentRepository.save(comment);
            return "Comment updated";
        } else {
            return "Comment with id " + commentId + " not found";
        }
    }

    @Transactional
    @Override
    public String deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
        return "Comment with id: " + commentId + " removed from database";
    }

    @Transactional
    @Override
    public String copyComment(long commentId, long bookId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent() && optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            Book book = optionalBook.get();
            Comment newComment = new Comment();
            newComment.setText(comment.getText());
            newComment.setBook(book);
            commentRepository.save(newComment);
            return "Comment with id: " + commentId + " copied to book with id: " + bookId;
        } else {
            return "Comment and/or book with specified id not found";
        }
    }
}
