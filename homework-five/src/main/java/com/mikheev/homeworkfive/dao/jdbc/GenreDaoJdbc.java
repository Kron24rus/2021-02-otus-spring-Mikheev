package com.mikheev.homeworkfive.dao.jdbc;

import com.mikheev.homeworkfive.dao.GenreDao;
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
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", genre.getName()));
        jdbcOperations.update(
                "INSERT INTO genres (name) values (:name)",
                params,
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Genre genre) {
        jdbcOperations.update(
                "UPDATE genres SET name = :name WHERE id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void delete(Genre genre) {
        jdbcOperations.update(
                "DELETE FROM genres WHERE id = :id",
                Map.of("id", genre.getId()));
    }

    @Override
    public Genre getById(long id) {
        return jdbcOperations.queryForObject(
                "SELECT * FROM genres WHERE id = :id",
                Map.of("id", id),
                new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query(
                "SELECT * FROM genres",
                new GenreMapper());
    }

    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
