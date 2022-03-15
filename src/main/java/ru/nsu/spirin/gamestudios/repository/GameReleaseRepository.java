package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;
import ru.nsu.spirin.gamestudios.model.mapper.GameReleaseMapper;
import ru.nsu.spirin.gamestudios.repository.query.GameReleaseQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class GameReleaseRepository extends JdbcDaoSupport {

    @Autowired
    public GameReleaseRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<GameRelease> findAllByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(GameReleaseQueries.QUERY_FIND_ALL_BY_GAME_ID, new GameReleaseMapper(), gameID);
    }

    public GameRelease findByGameIDAndPlatformID(Long gameID, Long platformID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(GameReleaseQueries.QUERY_FIND_BY_GAME_ID_AND_PLATFORM_ID, new GameReleaseMapper(), gameID, platformID);
    }

    public void save(Long gameID, GameRelease gameRelease) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_SAVE,
                gameID,
                gameRelease.getContractID(),
                gameRelease.getPlatformID(),
                gameRelease.getReleaseDate(),
                gameRelease.getCost(),
                gameRelease.getSoldCount());
    }

    public void update(Long gameID, Long platformID, GameRelease release) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_UPDATE,
                release.getReleaseDate(),
                release.getCost(),
                release.getSoldCount(),
                gameID,
                platformID
        );
    }

    public void delete(Long gameID, Long platformID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_DELETE, gameID, platformID);
    }

    public void deleteAllByContractID(Long contractID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_DELETE_ALL_BY_CONTRACT_ID, contractID);
    }

    public void deleteAllByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_DELETE_ALL_BY_GAME_ID, gameID);
    }

    public void deleteAllByPlatformID(Long platformID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(GameReleaseQueries.QUERY_DELETE_ALL_BY_PLATFORM_ID, platformID);
    }
}
