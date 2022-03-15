package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.PlatformMapper;
import ru.nsu.spirin.gamestudios.model.entity.Platform;
import ru.nsu.spirin.gamestudios.repository.query.PlatformQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class PlatformRepository extends JdbcDaoSupport {

    @Autowired
    public PlatformRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Platform findByID(Long platformID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(PlatformQueries.QUERY_FIND_BY_ID, new PlatformMapper(), platformID);
    }

    public List<Platform> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(PlatformQueries.QUERY_FIND_ALL, new PlatformMapper());
    }

    public void save(Platform platform) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(PlatformQueries.QUERY_SAVE, platform.getName(), platform.getPercent());
    }

    public void update(Long platformID, Platform platform) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(PlatformQueries.QUERY_UPDATE, platform.getName(), platform.getPercent(), platformID);
    }

    public void delete(Long platformID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(PlatformQueries.QUERY_DELETE, platformID);
    }
}
