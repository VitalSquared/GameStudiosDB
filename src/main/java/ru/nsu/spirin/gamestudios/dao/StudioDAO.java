package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.StudioMapper;
import ru.nsu.spirin.gamestudios.model.entity.Studio;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class StudioDAO extends JdbcDaoSupport {

    @Autowired
    public StudioDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Studio getStudioByID(Long studioID) {
        String sql = """
                        SELECT *
                        FROM studio st
                        WHERE st.studio_id = ?
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new StudioMapper(), studioID);
    }

    public List<Studio> getStudiosListByID(Long studioID) {
        String studioWhere = studioID == null || studioID == 0 ? " TRUE" : " st.studio_id = ?";
        Object[] params = studioID == null || studioID == 0 ? new Object[0] : new Object[] {studioID};
        String sql = """
                        SELECT * 
                        FROM studio st
                        WHERE 
                    """ + studioWhere + " "
                    + "ORDER BY st.studio_id;";
        return this.getJdbcTemplate().query(sql, new StudioMapper(), params);
    }

    public List<Studio> getAllStudios() {
        String sql = """
                        SELECT * 
                        FROM studio st
                        ORDER BY st.studio_id;
                    """;
        return this.getJdbcTemplate().query(sql, new StudioMapper());
    }

    public void newStudio(Studio studio) throws SQLException {
        if (studio.getName() == null || studio.getAddress() == null) {
            return;
        }

        String sql = """
                        INSERT INTO studio (studio_id, name, address) VALUES
                             (default, ?, ?);
                    """;
        this.getJdbcTemplate().update(sql, studio.getName(), studio.getAddress());
    }

    public void updateStudio(Long id, Studio studio) throws SQLException {
        if (studio.getAddress() == null || studio.getName() == null) {
            return;
        }

        String sql = """
                        UPDATE studio
                        SET name = ?, address = ?
                        WHERE studio_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, studio.getName(), studio.getAddress(), id);
    }

    public void removeStudio(Long studioID) throws SQLException {
        String sql = """
                        DELETE FROM studio
                        WHERE studio_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, studioID);
    }
}
