package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.mapper.GameMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class GameDAO extends JdbcDaoSupport {

    @Autowired
    public GameDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Game> getAllGames() {
        String sql = "SELECT * FROM game;";
        return this.getJdbcTemplate().query(sql, new GameMapper());
    }

    public Game getGameByID(Long gameID) {
        String sql = "SELECT * FROM game WHERE game_id = ?";
        return this.getJdbcTemplate().queryForObject(sql, new GameMapper(), gameID);
    }

    public void newGame(Game game) {
        String sql = """
                                    INSERT INTO game (game_id, start_date, dev_name, release_name, expenses, studio_id) VALUES
                                         (default, ?, ?, ?, ?, ?)
                                """;
        this.getJdbcTemplate().update(sql, game.getStartDate(), game.getDevName(), game.getReleaseName(), game.getExpenses(), game.getStudioID());
    }

    public void updateGame(Long id, Game game) {
        String sql = """
                                    UPDATE game
                                    SET start_date = ?, dev_name = ?, release_name = ?, expenses = ?
                                    WHERE game_id = ?
                                """;
        this.getJdbcTemplate().update(sql, game.getStartDate(), game.getDevName(), game.getReleaseName(), id);
    }
}
