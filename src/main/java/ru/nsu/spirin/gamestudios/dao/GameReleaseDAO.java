package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;
import ru.nsu.spirin.gamestudios.model.mapper.GameReleaseMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class GameReleaseDAO extends JdbcDaoSupport {

    @Autowired
    public GameReleaseDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<GameRelease> getReleasesByGameID(Long gameID) {
        String sql = """
                        SELECT * 
                        FROM game__contract__platform
                        WHERE game_id = ?;
                    """;
        return this.getJdbcTemplate().query(sql, new GameReleaseMapper(), gameID);
    }

    public void addRelease(Long gameID, GameRelease gameRelease) {
        String sql = """
                    INSERT INTO game__contract__platform (game_id, contract_id, platform_id, release_date, cost, sold_count) VALUES 
                    (?, ?, ?, ?, ?, ?);
                """;
        this.getJdbcTemplate().update(sql, gameID, gameRelease.getContractID(), gameRelease.getPlatformID(), gameRelease.getReleaseDate(), gameRelease.getCost(), gameRelease.getSoldCount());
    }

    public GameRelease getReleaseByGameAndPlatform(Long gameID, Long platformID) {
        String sql = """
                        SELECT * 
                        FROM game__contract__platform
                        WHERE game_id = ? AND platform_id = ?;
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new GameReleaseMapper(), gameID, platformID);
    }

    public void updateRelease(Long gameID, Long platformID, GameRelease release) {
        String sql = """
                    UPDATE game__contract__platform
                    SET release_date = ?, cost = ?, sold_count = ?
                    WHERE game_id = ? AND platform_id = ?;
                """;
        this.getJdbcTemplate().update(sql, release.getReleaseDate(), release.getCost(), release.getSoldCount(), gameID, platformID);
    }
}
