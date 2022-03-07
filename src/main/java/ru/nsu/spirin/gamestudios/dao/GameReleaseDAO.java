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
}