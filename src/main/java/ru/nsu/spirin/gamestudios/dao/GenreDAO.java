package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.GenreMapper;
import ru.nsu.spirin.gamestudios.model.entity.Genre;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class GenreDAO extends JdbcDaoSupport {
    @Autowired
    public GenreDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Genre getGenreByID(Long genreID) {
        String sql = """
                        SELECT *
                        FROM genre g
                        WHERE g.genre_id = ?
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new GenreMapper(), genreID);
    }

    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genre ORDER BY genre_id";
        return this.getJdbcTemplate().query(sql, new GenreMapper());
    }

    public void newGenre(Genre genre) throws SQLException {
        if (genre.getName() == null) {
            return;
        }
        String sql = """
                        INSERT INTO genre (genre_id, name) VALUES
                             (default, ?);
                    """;
        this.getJdbcTemplate().update(sql, genre.getName());
    }

    public void updateGenre(Long id, Genre genre) throws SQLException {
        if (genre.getName() == null) {
            return;
        }
        String sql = """
                        UPDATE genre
                        SET name = ?
                        WHERE genre_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, genre.getName(), id);
    }

    public List<Genre> getGenresByGameID(Long gameID) {
        String sql = """
                        SELECT genre_id, name
                        FROM genre NATURAL JOIN game__genre
                        WHERE game_id = ?
                        ORDER BY genre_id
                    """;
        return this.getJdbcTemplate().query(sql, new GenreMapper(), gameID);
    }

    public List<Genre> getGenresByTestID(Long testID) {
        String sql = """
                        SELECT genre_id, name
                        FROM genre NATURAL JOIN test__genre
                        WHERE test_id = ?
                        ORDER BY genre_id
                    """;
        return this.getJdbcTemplate().query(sql, new GenreMapper(), testID);
    }
}
