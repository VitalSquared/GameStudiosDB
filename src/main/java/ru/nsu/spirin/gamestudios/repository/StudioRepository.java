package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.StudioMapper;
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.repository.query.StudioQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class StudioRepository extends JdbcDaoSupport {

    @Autowired
    public StudioRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Studio findByID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(StudioQueries.QUERY_FIND_BY_ID, new StudioMapper(), studioID);
    }

    public List<Studio> findAllByID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        String studioWhere = studioID == null || studioID == 0 ? " TRUE" : " st.studio_id = ?";
        Object[] params = studioID == null || studioID == 0 ? new Object[0] : new Object[] {studioID};
        return this.getJdbcTemplate().query(
                String.format(StudioQueries.QUERY_FIND_ALL_BY_ID, studioWhere),
                new StudioMapper(),
                params
        );
    }

    public List<Studio> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(StudioQueries.QUERY_FIND_ALL, new StudioMapper());
    }

    public void save(Studio studio) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(StudioQueries.QUERY_SAVE, studio.getName(), studio.getAddress());
    }

    public void update(Long id, Studio studio) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(StudioQueries.QUERY_UPDATE, studio.getName(), studio.getAddress(), id);
    }

    public void delete(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(StudioQueries.QUERY_DELETE, studioID);
    }
}
