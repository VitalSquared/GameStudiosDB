package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.mapper.GameMapper;
import ru.nsu.spirin.gamestudios.repository.query.GameQueries;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GameRepository extends JdbcDaoSupport {

    @Autowired
    public GameRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Game> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(GameQueries.QUERY_FIND_ALL, new GameMapper());
    }

    public List<Game> findAllByContractID(Long contractID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(GameQueries.QUERY_FIND_ALL_BY_CONTRACT_ID, new GameMapper(), contractID);
    }

    public List<Game> findAllByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(GameQueries.QUERY_FIND_ALL_BY_STUDIO_ID, new GameMapper(), studioID);
    }

    public Game findByID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(GameQueries.QUERY_FIND_BY_ID, new GameMapper(), gameID);
    }

    public void save(Game game) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_SAVE,
                game.getStartDate(),
                game.getDevName(),
                game.getReleaseName(),
                game.getExpenses(),
                game.getStudioID());
    }

    public void update(Long gameID, Game game) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_UPDATE,
                game.getStartDate(),
                game.getDevName(),
                game.getReleaseName(),
                game.getExpenses(),
                gameID
        );
    }

    public void delete(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE, gameID);
    }

    public void saveGameGenre(Long gameID, Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_SAVE_GAME_GENRE,  gameID, genreID);
    }

    public void deleteGameGenre(Long gameID, Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_GAME_GENRE, gameID, genreID);
    }

    public void deleteAllGameGenre(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_ALL_GAME_GENRE, gameID);
    }

    public void deleteAllGameGenreByGenreID(Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_ALL_GAME_GENRE_BY_GENRE_ID, genreID);
    }

    public void saveGameEmployee(Long gameID, Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_SAVE_GAME_EMPLOYEE,  gameID, employeeID);
    }

    public void deleteGameEmployee(Long gameID, Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_GAME_EMPLOYEE, gameID, employeeID);
    }

    public void deleteAllGameEmployeeByEmployeeID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_ALL_GAME_EMPLOYEE_BY_EMPLOYEE_ID, employeeID);
    }

    public void deleteAllGameEmployeeByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameQueries.QUERY_DELETE_ALL_GAME_EMPLOYEE_BY_GAME_ID, gameID);
    }
}
