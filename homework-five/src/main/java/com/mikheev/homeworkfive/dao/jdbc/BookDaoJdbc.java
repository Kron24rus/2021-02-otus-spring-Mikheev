package com.mikheev.homeworkfive.dao.jdbc;

import com.mikheev.homeworkfive.dao.BookDao;
import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(
                Map.of("title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
        jdbcOperations.update(
                "INSERT INTO books (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                params,
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        jdbcOperations.update(
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public void delete(Book book) {
        jdbcOperations.update(
                "DELETE FROM books WHERE id = :id",
                Map.of("id", book.getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbcOperations.queryForObject(
                "SELECT * FROM books as b LEFT JOIN genres ON b.genre_id = genres.id LEFT JOIN authors ON b.author_id = authors.id WHERE b.id = :id",
                Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "SELECT * FROM books LEFT JOIN genres ON books.genre_id = genres.id LEFT JOIN authors ON books.author_id = authors.id",
                new BookMapper());
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long authorId = resultSet.getLong("authors.id");
            long genreId = resultSet.getLong("genres.id");
            String bookTitle = resultSet.getString("title");
            String authorName = resultSet.getString("authors.name");
            String genreName = resultSet.getString("genres.name");
            Author bookAuthor = new Author(authorId, authorName);
            Genre bookGenre = new Genre(genreId, genreName);
            return new Book(id, bookTitle, bookAuthor, bookGenre);
        }
    }
}
