package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestAppResult;
import ru.nsu.spirin.gamestudios.model.mapper.TestAppResultMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestAppResultDAO extends JdbcDaoSupport {

    @Autowired
    public TestAppResultDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestAppResult> getAllResults() {
        String sql = "SELECT * FROM test_app_result;";
        return this.getJdbcTemplate().query(sql, new TestAppResultMapper());
    }

    public TestAppResult getResultByID(Long resultID) {
        String sql = "SELECT * FROM test_app_result WHERE result_id = ?";
        return this.getJdbcTemplate().queryForObject(sql, new TestAppResultMapper(), resultID);
    }
}
