package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestStatus;
import ru.nsu.spirin.gamestudios.model.mapper.TestStatusMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestStatusDAO extends JdbcDaoSupport {

    @Autowired
    public TestStatusDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestStatus> getAllStatuses() {
        String sql = "SELECT * FROM test_status;";
        return this.getJdbcTemplate().query(sql, new TestStatusMapper());
    }

    public TestStatus getStatusByID(Long statusID) {
        String sql = "SELECT * FROM test_status WHERE status_id = ?";
        return this.getJdbcTemplate().queryForObject(sql, new TestStatusMapper(), statusID);
    }
}
