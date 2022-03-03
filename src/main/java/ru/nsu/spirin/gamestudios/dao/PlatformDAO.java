package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.CategoryMapper;
import ru.nsu.spirin.gamestudios.mapper.PlatformMapper;
import ru.nsu.spirin.gamestudios.model.Category;
import ru.nsu.spirin.gamestudios.model.Platform;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class PlatformDAO extends JdbcDaoSupport {

    @Autowired
    public PlatformDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Platform getPlatformByID(Long platformID) {
        String sql = """
                        SELECT *
                        FROM platform p
                        WHERE p.platform_id = ?
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new PlatformMapper(), platformID);
    }

    public List<Platform> getAllPlatforms() {
        String sql = "SELECT * FROM platform ORDER BY platform_id";
        return this.getJdbcTemplate().query(sql, new PlatformMapper());
    }

    public void newPlatform(Platform platform) throws SQLException {
        if (platform.getName() == null || platform.getPercent() == null) {
            return;
        }
        String sql = """
                        INSERT INTO platform (platform_id, name, percent) VALUES
                             (default, ?, ?);
                    """;
        this.getJdbcTemplate().update(sql, platform.getName(), platform.getPercent());
    }

    public void updatePlatform(Long id, Platform platform) throws SQLException {
        if (platform.getName() == null || platform.getPercent() == null) {
            return;
        }
        String sql = """
                        UPDATE platform
                        SET name = ?, percent = ?
                        WHERE platform_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, platform.getName(), platform.getPercent(), id);
    }
}
