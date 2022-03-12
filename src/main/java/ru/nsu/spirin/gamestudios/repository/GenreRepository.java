package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.GenreMapper;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.repository.query.GenreQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class GenreRepository extends JdbcDaoSupport {
    @Autowired
    public GenreRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Genre findByID(Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(GenreQueries.QUERY_FIND_BY_ID, new GenreMapper(), genreID);
    }

    public List<Genre> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(GenreQueries.QUERY_FIND_ALL, new GenreMapper());
    }

    public void save(Genre genre) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GenreQueries.QUERY_SAVE, genre.getName());
    }

    public void update(Long genreID, Genre genre) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GenreQueries.QUERY_UPDATE, genre.getName(), genreID);
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
