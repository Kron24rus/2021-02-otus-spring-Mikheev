package com.mikheev.homeworkfive.dao.jdbc;

import com.mikheev.homeworkfive.dao.AuthorDao;
import com.mikheev.homeworkfive.domain.Author;
import com.mikheev.homeworkfive.domain.Book;
import com.mikheev.homeworkfive.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", author.getName()));
        jdbcOperations.update(
                "INSERT INTO authors (name) values (:name)",
                params,
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Author author) {
        jdbcOperations.update(
                "UPDATE authors SET name = :name WHERE id = :id",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void delete(Author author) {
        jdbcOperations.update(
                "DELETE FROM authors WHERE id = :id",
                Map.of("id", author.getId()));
    }

    @Override
    public Author getById(long id) {
        List<Author> authors = jdbcOperations.query(
                "SELECT * FROM authors as a LEFT JOIN books ON a.id = books.author_id LEFT JOIN genres ON books.genre_id = genres.id WHERE a.id = :id",
                Map.of("id", id),
                new AuthorDataExtractor());
        if (authors != null && !authors.isEmpty()) {
            return authors.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query(
                "SELECT * FROM authors LEFT JOIN books ON authors.id = books.author_id LEFT JOIN genres ON books.genre_id = genres.id",
                new AuthorDataExtractor());
    }

    public static class AuthorDataExtractor implements ResultSetExtractor<List<Author>> {
        @Override
        public List<Author> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

            List<Author> authors = new ArrayList<>();
            Map<Long, Author> authorsMap = new HashMap<>();
            Map<Long, Book> bookMap = new HashMap<>();
            Map<Long, Genre> genreMap = new HashMap<>();

            while (resultSet.next()) {
                long authorId = resultSet.getLong("id");
                Author author = authorsMap.get(authorId);
                if (author == null) {
                    String authorName = resultSet.getString("name");
                    author = new Author(authorId, authorName);
                    authorsMap.put(authorId, author);
                    authors.add(author);
                }
                long bookId = resultSet.getLong("books.id");
                Book book = bookMap.get(bookId);
                if (book == null) {
                    String bookTitle = resultSet.getString("books.title");
                    book = new Book(bookId, bookTitle);
                    bookMap.put(bookId, book);
                    author.getBooks().add(book);
                }
                long genreId = resultSet.getLong("genres.id");
                Genre genre = genreMap.get(genreId);
                if (genre == null) {
                    String genreName = resultSet.getString("genres.name");
                    genre = new Genre(genreId, genreName);
                    genreMap.put(genreId, genre);
                    book.setGenre(genre);
                }
            }

            return authors;
        }
    }
}
